package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.PagoErrorCode;
import ufps.edu.co.maps.specific.PagoMap;
import ufps.edu.co.records.output.entity.PagoOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.EstadoDTO;
import ufps.edu.co.rest.dto.PagoDTO;
import ufps.edu.co.rest.dto.PagoconceptoDTO;
import ufps.edu.co.rest.dto.ValoresglobalesDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.EstadoService;
import ufps.edu.co.rest.services.PagoService;
import ufps.edu.co.rest.services.PagoconceptoService;
import ufps.edu.co.rest.services.ValoresglobalesService;
import ufps.edu.co.wompi.WompiGateway;
import ufps.edu.co.wompi.config.WompiProperties;
import ufps.edu.co.wompi.model.WompiCheckoutRequest;
import ufps.edu.co.wompi.model.WompiCheckoutResponse;
import ufps.edu.co.wompi.model.WompiWebhookRequest;

@Service
@Transactional
public class PagoProcessor {

    private static final Pattern GLOBAL_KEY_PATTERN = Pattern.compile("^(?<prefix>[A-Z_]+)_(?<year>\\d{4})_(?<version>\\d+)$");

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoconceptoService pagoconceptoService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private ValoresglobalesService valoresglobalesService;

    @Autowired
    private AspiranteService aspiranteService;

    @Autowired
    private PagoMap pagoMap;

    @Autowired
    private WompiGateway wompiGateway;

    @Autowired
    private WompiProperties wompiProperties;

    @Transactional(readOnly = true)
    public List<PagoOutput> findByAspirante(Integer idAspirante) {
        return pagoService.findByIdAspirante(idAspirante).stream().map(pagoMap::toOutput).toList();
    }

    public void ensureInitialPaymentsForAspirante(Integer idAspirante) {
        AspiranteDTO aspirante = aspiranteService.findById(idAspirante);
        if (aspirante == null) {
            throw new DomainException(PagoErrorCode.PAGO_NOT_FOUND, idAspirante);
        }

        EstadoDTO estadoPendiente = resolveEstadoPago("PENDIENTE");
        Map<String, PagoconceptoDTO> conceptos = pagoconceptoService.findAll().stream()
                .filter(concepto -> concepto.getTipo() != null)
                .collect(Collectors.toMap(concepto -> concepto.getTipo().toUpperCase(Locale.ROOT), concepto -> concepto,
                        (primero, segundo) -> primero));

        Set<Integer> conceptosExistentes = pagoService.findByIdAspirante(idAspirante).stream()
                .map(PagoDTO::getIdPagoconcepto)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        crearPagoPendienteSiFalta(idAspirante, estadoPendiente, conceptosExistentes, conceptos.get("INSCRIPCION"));
        crearPagoPendienteSiFalta(idAspirante, estadoPendiente, conceptosExistentes, conceptos.get("MATRICULA"));
    }

    public WompiCheckoutResponse iniciarCheckoutInscripcion(Integer idAspirante) {
        PagoDTO pago = encontrarPagoConcepto(idAspirante, "INSCRIPCION");
        validarPagoPerteneceAspirante(pago, idAspirante);

        EstadoDTO estadoRealizado = resolveEstadoPagoOpcional("REALIZADO");
        if (estadoRealizado != null && Objects.equals(pago.getIdEstado(), estadoRealizado.getId())) {
            throw new DomainException(PagoErrorCode.PAGO_YA_REALIZADO_CONFLICT, pago.getId());
        }

        BigDecimal monto = calcularMontoInscripcionEnPesos();
        long montoEnCents = monto.multiply(BigDecimal.valueOf(100L)).setScale(0, RoundingMode.HALF_UP).longValueExact();
        AspiranteDTO aspirante = aspiranteService.findById(idAspirante);
        String referencia = construirReferencia(pago, aspirante);
        String currency = resolverCurrency();
        String signatureIntegrity = generarSignatureIntegrity(referencia, montoEnCents, currency);
        String publicKey = resolverPublicKey();

        WompiCheckoutRequest request = WompiCheckoutRequest.builder()
                .paymentId(pago.getId())
                .aspiranteId(idAspirante)
                .pagoconceptoId(pago.getIdPagoconcepto())
                .concepto("INSCRIPCION")
                .reference(referencia)
                .amount(monto)
                .amountInCents(montoEnCents)
            .currency(currency)
            .publicKey(publicKey)
            .signatureIntegrity(signatureIntegrity)
            .redirectUrl(resolveReturnUrl())
            .widgetScriptUrl(resolveWidgetScriptUrl())
                .customerEmail(aspirante != null && aspirante.getPersona() != null ? aspirante.getPersona().getCorreo() : null)
                .customerName(construirNombreAspirante(aspirante))
            .returnUrl(resolveReturnUrl())
            .webhookUrl(resolveWebhookUrl())
                .metadata(Map.of(
                        "paymentId", String.valueOf(pago.getId()),
                        "aspiranteId", String.valueOf(idAspirante),
                        "concepto", "INSCRIPCION"))
                .build();

        return wompiGateway.createCheckout(request);
    }

    public PagoOutput confirmarWebhook(WompiWebhookRequest request) {
        if (request == null || request.paymentId() == null) {
            throw new DomainException(PagoErrorCode.PAGO_NOT_FOUND, null);
        }

        PagoDTO pago = pagoService.findById(request.paymentId());
        if (pago == null) {
            throw new DomainException(PagoErrorCode.PAGO_NOT_FOUND, request.paymentId());
        }

        if (request.reference() != null && request.reference().startsWith("PAGO-")) {
            String[] parts = request.reference().split("-");
            if (parts.length >= 4) {
                Integer idPagoReferencia = tryParseInt(parts[1]);
                if (idPagoReferencia != null && !Objects.equals(idPagoReferencia, pago.getId())) {
                    throw new DomainException(PagoErrorCode.PAGO_CONCEPTO_INVALIDO, request.reference());
                }
            }
        }

        if (request.status() == null || !esEstadoAprobado(request.status())) {
            throw new DomainException(PagoErrorCode.WOMPI_PAGO_NO_APROBADO_CONFLICT, request.status());
        }

        EstadoDTO estadoRealizado = resolveEstadoPago("REALIZADO");
        pago.setIdEstado(estadoRealizado.getId());
        PagoDTO updated = pagoService.update(pago.getId(), pago);

        // TODO: disparar notificación por correo al aspirante cuando el pago de inscripción quede realizado.
        return pagoMap.toOutput(updated);
    }

    private void crearPagoPendienteSiFalta(Integer idAspirante, EstadoDTO estadoPendiente,
            Set<Integer> conceptosExistentes, PagoconceptoDTO concepto) {
        if (concepto == null || conceptosExistentes.contains(concepto.getId())) {
            return;
        }
        pagoService.create(PagoDTO.builder()
                .idAspirante(idAspirante)
                .idEstado(estadoPendiente.getId())
                .idPagoconcepto(concepto.getId())
                .build());
    }

    private PagoDTO encontrarPagoConcepto(Integer idAspirante, String tipoConcepto) {
        PagoconceptoDTO concepto = pagoconceptoService.findAll().stream()
                .filter(item -> item.getTipo() != null && item.getTipo().equalsIgnoreCase(tipoConcepto))
                .findFirst()
                .orElseThrow(() -> new DomainException(PagoErrorCode.PAGO_CONCEPTO_NOT_FOUND, tipoConcepto));

        return pagoService.findByIdAspirante(idAspirante).stream()
                .filter(pago -> Objects.equals(pago.getIdPagoconcepto(), concepto.getId()))
                .findFirst()
                .orElseThrow(() -> new DomainException(PagoErrorCode.PAGO_NOT_FOUND, idAspirante));
    }

    private void validarPagoPerteneceAspirante(PagoDTO pago, Integer idAspirante) {
        if (pago == null) {
            throw new DomainException(PagoErrorCode.PAGO_NOT_FOUND, idAspirante);
        }
        if (!Objects.equals(pago.getIdAspirante(), idAspirante)) {
            throw new DomainException(PagoErrorCode.PAGO_NO_PERTENECE_AL_ASPIRANTE_FORBIDDEN, idAspirante);
        }
    }

    private EstadoDTO resolveEstadoPago(String tipo) {
        EstadoDTO estado = estadoService.findByTipoAndEntidad(tipo, "pago");
        if (estado == null) {
            estado = estadoService.findByTipoAndEntidad(tipo, "PAGO");
        }
        if (estado == null) {
            throw new DomainException(PagoErrorCode.PAGO_ESTADO_NOT_FOUND, tipo);
        }
        return estado;
    }

    private EstadoDTO resolveEstadoPagoOpcional(String tipo) {
        EstadoDTO estado = estadoService.findByTipoAndEntidad(tipo, "pago");
        if (estado != null) {
            return estado;
        }
        return estadoService.findByTipoAndEntidad(tipo, "PAGO");
    }

    private BigDecimal calcularMontoInscripcionEnPesos() {
        int anio = LocalDate.now().getYear();
        BigDecimal salariosMinimos = leerValorGlobalNumerico("SALARIO_MINIMO", anio, false);
        BigDecimal cantidadSalarios = leerValorGlobalNumerico("VALOR_INSCRIPCION", anio, true);
        return salariosMinimos.multiply(cantidadSalarios);
    }

    private String resolverCurrency() {
        String currency = wompiProperties.getCurrency();
        return currency == null || currency.isBlank() ? "COP" : currency;
    }

    private String resolveReturnUrl() {
        return normalizeConfigValue(wompiProperties.getReturnUrl());
    }

    private String resolveWebhookUrl() {
        return normalizeConfigValue(wompiProperties.getWebhookUrl());
    }

    private String resolveWidgetScriptUrl() {
        String widgetScriptUrl = wompiProperties.getWidgetScriptUrl();
        return widgetScriptUrl == null || widgetScriptUrl.isBlank() ? "https://checkout.wompi.co/widget.js" : widgetScriptUrl;
    }

    private String resolverPublicKey() {
        String publicKey = wompiProperties.getPublicKey();
        if (publicKey != null && !publicKey.isBlank()) {
            return publicKey;
        }
        return wompiProperties.isSimulated() ? "simulated-public-key" : null;
    }

    private String generarSignatureIntegrity(String reference, long amountInCents, String currency) {
        String secret = wompiProperties.getIntegritySecret();
        if (secret == null || secret.isBlank()) {
            secret = "simulated-secret";
        }

        String payload = reference + amountInCents + currency + secret;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte value : hash) {
                hex.append(String.format("%02x", value));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No se pudo calcular la firma de integridad de Wompi", e);
        }
    }

    private String normalizeConfigValue(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private BigDecimal leerValorGlobalNumerico(String prefijo, int anio, boolean permitirSufijoSmmlv) {
        ValoresglobalesDTO valor = valoresglobalesService.findAll().stream()
                .filter(item -> item.getClave() != null)
                .filter(item -> item.getClave().startsWith(prefijo + "_" + anio + "_"))
                .max(Comparator.comparingInt(item -> extraerVersion(item.getClave(), prefijo, anio)))
                .orElseThrow(() -> new DomainException(PagoErrorCode.VALOR_GLOBAL_NO_CONFIGURADO_NOT_FOUND,
                        prefijo + "_" + anio));

        String texto = valor.getValor() != null ? valor.getValor().trim() : null;
        if (texto == null || texto.isEmpty()) {
            throw new DomainException(PagoErrorCode.VALOR_GLOBAL_FORMATO_INVALIDO, valor.getClave());
        }

        if (permitirSufijoSmmlv) {
            texto = texto.toUpperCase(Locale.ROOT)
                    .replace("SMMLV", "")
                    .replace("SMLMV", "")
                    .trim();
        }

        try {
            return new BigDecimal(texto);
        } catch (NumberFormatException ex) {
            throw new DomainException(PagoErrorCode.VALOR_GLOBAL_FORMATO_INVALIDO, valor.getClave());
        }
    }

    private int extraerVersion(String clave, String prefijo, int anio) {
        Matcher matcher = GLOBAL_KEY_PATTERN.matcher(clave);
        if (!matcher.matches()) {
            return 0;
        }
        if (!prefijo.equalsIgnoreCase(matcher.group("prefix"))) {
            return 0;
        }
        if (Integer.parseInt(matcher.group("year")) != anio) {
            return 0;
        }
        return Integer.parseInt(matcher.group("version"));
    }

    private String construirReferencia(PagoDTO pago, AspiranteDTO aspirante) {
        String nombre = aspirante != null && aspirante.getPersona() != null && aspirante.getPersona().getCorreo() != null
                ? aspirante.getPersona().getCorreo().replaceAll("[^a-zA-Z0-9]", "")
                : "aspirante";
        return "PAGO-" + pago.getId() + "-" + nombre.toUpperCase(Locale.ROOT) + "-" + LocalDate.now().getYear();
    }

    private String construirNombreAspirante(AspiranteDTO aspirante) {
        if (aspirante == null || aspirante.getPersona() == null) {
            return null;
        }
        String nombres = aspirante.getPersona().getNombres() != null ? aspirante.getPersona().getNombres() : "";
        String apellidos = aspirante.getPersona().getApellidos() != null ? aspirante.getPersona().getApellidos() : "";
        return (nombres + " " + apellidos).trim();
    }

    private boolean esEstadoAprobado(String status) {
        String normalized = status.trim().toUpperCase(Locale.ROOT);
        return normalized.equals("APPROVED") || normalized.equals("APPROVAL") || normalized.equals("APPROVAL_SUCCESS")
                || normalized.equals("SUCCESS") || normalized.equals("PAID") || normalized.equals("PAYED");
    }

    private Integer tryParseInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}