package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.PagoErrorCode;
import ufps.edu.co.maps.specific.PagoMap;
import ufps.edu.co.rest.dto.AspiranteCheckoutDTO;
import ufps.edu.co.records.output.entity.PagoListadoOutput;
import ufps.edu.co.records.output.entity.PagoconceptoResumenOutput;
import ufps.edu.co.records.output.entity.PagoOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.EstadoDTO;
import ufps.edu.co.rest.dto.PagoDTO;
import ufps.edu.co.rest.dto.PagoResumenDTO;
import ufps.edu.co.rest.dto.PagoconceptoDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.EstadoService;
import ufps.edu.co.rest.services.PagoService;
import ufps.edu.co.rest.services.PagoconceptoService;
import ufps.edu.co.rest.services.UsuarioService;
import ufps.edu.co.wompi.WompiGateway;
import ufps.edu.co.wompi.config.WompiProperties;
import ufps.edu.co.wompi.model.WompiCheckoutRequest;
import ufps.edu.co.wompi.model.WompiCheckoutResponse;
import ufps.edu.co.wompi.model.WompiCustomerData;
import ufps.edu.co.wompi.model.WompiReceiptData;
import ufps.edu.co.wompi.model.WompiWebhookRequest;

@Service
@Transactional
public class PagoProcessor {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoconceptoService pagoconceptoService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private ValoresglobalesProcessor valoresglobalesProcessor;

    @Autowired
    private AspiranteService aspiranteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PagoMap pagoMap;

    @Autowired
    private WompiGateway wompiGateway;

    @Autowired
    private WompiProperties wompiProperties;

    @Transactional(readOnly = true)
        public List<PagoListadoOutput> findByAspirante(Integer idAspirante) {
        List<PagoResumenDTO> pagos = pagoService.findResumenByIdAspirante(idAspirante);
        BigDecimal valorInscripcion = calcularMontoInscripcionEnPesos();
        BigDecimal valorMatricula = calcularMontoMatriculaEnPesos(idAspirante);

        return pagos.stream()
            .map(dto -> PagoListadoOutput.builder()
                .id(dto.id())
                .idAspirante(dto.idAspirante())
                .idEstado(dto.idEstado())
                .idPagoconcepto(dto.idPagoconcepto())
                .aspirante(dto.aspirante())
                .estado(dto.estado())
                .pagoconcepto(PagoconceptoResumenOutput.builder()
                    .id(dto.pagoconceptoId())
                    .tipo(dto.pagoconceptoTipo())
                    .build())
                .valorPagoPesos(resolveValorPagoPorTipoConcepto(dto.pagoconceptoTipo(), valorInscripcion,
                    valorMatricula))
                .build())
            .toList();
    }

    public void ensureInitialPaymentsForAspirante(Integer idAspirante) {
        AspiranteCheckoutDTO aspirante = aspiranteService.findCheckoutById(idAspirante);
        if (aspirante == null) {
            throw new DomainException(PagoErrorCode.PAGO_NOT_FOUND, idAspirante);
        }

        EstadoDTO estadoPendiente = resolveEstadoPago("PENDIENTE");
        Map<String, PagoconceptoDTO> conceptos = pagoconceptoService.findAll().stream()
                .filter(concepto -> concepto.getTipo() != null)
                .collect(Collectors.toMap(concepto -> concepto.getTipo().toUpperCase(Locale.ROOT), concepto -> concepto,
                        (primero, segundo) -> primero));

        Set<Integer> conceptosExistentes = pagoService.findResumenByIdAspirante(idAspirante).stream()
            .map(PagoResumenDTO::idPagoconcepto)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        crearPagoPendienteSiFalta(idAspirante, estadoPendiente, conceptosExistentes, conceptos.get("INSCRIPCION"));
        crearPagoPendienteSiFalta(idAspirante, estadoPendiente, conceptosExistentes, conceptos.get("MATRICULA"));
    }

    public WompiCheckoutResponse iniciarCheckoutInscripcion(Integer idAspirante, Integer authenticatedUserId,
            boolean validarTitular) {
        PagoResumenDTO pago = encontrarPagoConceptoResumen(idAspirante, "INSCRIPCION");
        validarPagoPerteneceAspirante(pago, idAspirante);

        AspiranteCheckoutDTO aspirante = aspiranteService.findCheckoutById(idAspirante);
        validarAspiranteAutenticado(aspirante, authenticatedUserId, validarTitular);

        EstadoDTO estadoRealizado = resolveEstadoPagoOpcional("REALIZADO");
        if (estadoRealizado != null && Objects.equals(pago.idEstado(), estadoRealizado.getId())) {
            throw new DomainException(PagoErrorCode.PAGO_YA_REALIZADO_CONFLICT, pago.id());
        }

        BigDecimal monto = calcularMontoInscripcionEnPesos();
        long montoEnCents = valoresglobalesProcessor.calcularValorInscripcionCentavos();
        String referencia = construirReferencia(pago, aspirante);
        String currency = resolverCurrency();
        String signatureIntegrity = generarSignatureIntegrity(referencia, montoEnCents, currency);
        String publicKey = resolverPublicKey();
        WompiCustomerData customerData = construirCustomerData(aspirante);
        WompiReceiptData receiptData = construirReceiptData(pago, aspirante, referencia, monto, montoEnCents, currency);

        WompiCheckoutRequest request = WompiCheckoutRequest.builder()
                .paymentId(pago.id())
                .aspiranteId(idAspirante)
                .pagoconceptoId(pago.idPagoconcepto())
                .concepto("INSCRIPCION")
                .reference(referencia)
                .amount(monto)
                .amountInCents(montoEnCents)
            .currency(currency)
            .publicKey(publicKey)
            .signatureIntegrity(signatureIntegrity)
            .redirectUrl(resolveReturnUrl())
            .widgetScriptUrl(resolveWidgetScriptUrl())
                //.customerEmail(aspirante != null && aspirante.getPersona() != null ? aspirante.getPersona().getCorreo() : null)
                .customerEmail(null)
                .customerName(construirNombreAspirante(aspirante))
                .customerData(customerData)
                .receiptData(receiptData)
            .returnUrl(resolveReturnUrl())
            .webhookUrl(resolveWebhookUrl())
                .metadata(Map.of(
                        "paymentId", String.valueOf(pago.id()),
                        "aspiranteId", String.valueOf(idAspirante),
                        "concepto", "INSCRIPCION"))
                .build();

        return wompiGateway.createCheckout(request);
    }

    public WompiReceiptData prepararReciboInscripcion(Integer idAspirante, Integer authenticatedUserId,
            boolean validarTitular) {
        PagoResumenDTO pago = encontrarPagoConceptoResumen(idAspirante, "INSCRIPCION");
        validarPagoPerteneceAspirante(pago, idAspirante);

        AspiranteCheckoutDTO aspirante = aspiranteService.findCheckoutById(idAspirante);
        validarAspiranteAutenticado(aspirante, authenticatedUserId, validarTitular);

        BigDecimal monto = calcularMontoInscripcionEnPesos();
        long montoEnCents = valoresglobalesProcessor.calcularValorInscripcionCentavos();
        String referencia = construirReferencia(pago, aspirante);
        return construirReceiptData(pago, aspirante, referencia, monto, montoEnCents, resolverCurrency());
    }

    public PagoOutput confirmarWebhook(WompiWebhookRequest request) {
        return procesarWebhook(request, false);
    }

    public PagoOutput confirmarWebhookAutomatico(WompiWebhookRequest request) {
        return procesarWebhook(request, true);
    }

    private PagoOutput procesarWebhook(WompiWebhookRequest request, boolean actualizarEstado) {
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

        WompiCheckoutResponse wompiConfirmation = wompiGateway.confirmPayment(request);
        String status = wompiConfirmation != null && wompiConfirmation.status() != null ? wompiConfirmation.status()
                : request.status();

        if (status == null || !esEstadoAprobado(status)) {
            throw new DomainException(PagoErrorCode.WOMPI_PAGO_NO_APROBADO_CONFLICT, request.status());
        }

        if (!actualizarEstado) {
            return pagoMap.toOutput(pago);
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

    private PagoResumenDTO encontrarPagoConceptoResumen(Integer idAspirante, String tipoConcepto) {
        PagoconceptoDTO concepto = pagoconceptoService.findAll().stream()
                .filter(item -> item.getTipo() != null && item.getTipo().equalsIgnoreCase(tipoConcepto))
                .findFirst()
                .orElseThrow(() -> new DomainException(PagoErrorCode.PAGO_CONCEPTO_NOT_FOUND, tipoConcepto));

        return pagoService.findResumenByIdAspirante(idAspirante).stream()
                .filter(pago -> Objects.equals(pago.idPagoconcepto(), concepto.getId()))
                .findFirst()
                .orElseThrow(() -> new DomainException(PagoErrorCode.PAGO_NOT_FOUND, idAspirante));
    }

    private void validarPagoPerteneceAspirante(PagoResumenDTO pago, Integer idAspirante) {
        if (pago == null) {
            throw new DomainException(PagoErrorCode.PAGO_NOT_FOUND, idAspirante);
        }
        if (!Objects.equals(pago.idAspirante(), idAspirante)) {
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
        return valoresglobalesProcessor.calcularValorInscripcionPesos();
    }

    private BigDecimal calcularMontoMatriculaEnPesos(Integer idAspirante) {
        AspiranteDTO aspirante = aspiranteService.findById(idAspirante);
        if (aspirante == null || aspirante.getCohorte() == null || aspirante.getCohorte().getPrograma() == null) {
            return null;
        }
        return aspirante.getCohorte().getPrograma().getValormatricula();
    }

    private BigDecimal resolveValorPagoPorTipoConcepto(String tipoConcepto, BigDecimal valorInscripcion,
            BigDecimal valorMatricula) {
        if (tipoConcepto == null) {
            return null;
        }

        String tipoNormalizado = tipoConcepto.trim().toUpperCase(Locale.ROOT);
        if ("INSCRIPCION".equals(tipoNormalizado)) {
            return valorInscripcion;
        }
        if ("MATRICULA".equals(tipoNormalizado)) {
            return valorMatricula;
        }
        return null;
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

    private String construirReferencia(PagoResumenDTO pago, AspiranteCheckoutDTO aspirante) {
        String nombre = aspirante != null && aspirante.correo() != null
                ? aspirante.correo().replaceAll("[^a-zA-Z0-9]", "")
                : "aspirante";
        return "PAGO-" + pago.id() + "-" + nombre.toUpperCase(Locale.ROOT) + "-" + LocalDate.now().getYear();
    }

    private String construirNombreAspirante(AspiranteCheckoutDTO aspirante) {
        if (aspirante == null) {
            return null;
        }
        String nombres = aspirante.nombres() != null ? aspirante.nombres() : "";
        String apellidos = aspirante.apellidos() != null ? aspirante.apellidos() : "";
        return (nombres + " " + apellidos).trim();
    }

    private WompiCustomerData construirCustomerData(AspiranteCheckoutDTO aspirante) {
        if (aspirante == null) {
            return null;
        }

        String fullName = construirNombreAspirante(aspirante);
        String email = aspirante.correo();
        String phoneNumber = aspirante.celular() != null ? aspirante.celular() : aspirante.telefono();
        String legalId = aspirante.numerodocumento() != null ? String.valueOf(aspirante.numerodocumento()) : null;
        String legalIdType = aspirante.tipoDocumento();

        return WompiCustomerData.builder()
                .email(email)
                .fullName(fullName)
                .phoneNumber(phoneNumber)
                .phoneNumberPrefix("+57")
                .legalId(legalId)
                .legalIdType(legalIdType)
                .build();
    }

    private WompiReceiptData construirReceiptData(PagoResumenDTO pago, AspiranteCheckoutDTO aspirante, String referencia,
            BigDecimal monto, long montoEnCents, String currency) {
        Integer personaId = aspirante != null ? aspirante.idPersona() : null;
        Integer cohorteId = aspirante != null ? aspirante.idCohorte() : null;
        WompiCustomerData customerData = construirCustomerData(aspirante);

        return WompiReceiptData.builder()
                .paymentId(pago != null ? pago.id() : null)
                .aspiranteId(aspirante != null ? aspirante.id() : null)
                .personaId(personaId)
                .cohorteId(cohorteId)
                .pagoconceptoId(pago != null ? pago.idPagoconcepto() : null)
                .concepto("INSCRIPCION")
                .reference(referencia)
                .amount(monto)
                .amountInCents(montoEnCents)
                .currency(currency)
                .fullName(customerData != null ? customerData.fullName() : null)
                .email(customerData != null ? customerData.email() : null)
                .phoneNumber(customerData != null ? customerData.phoneNumber() : null)
                .legalId(customerData != null ? customerData.legalId() : null)
                .legalIdType(customerData != null ? customerData.legalIdType() : null)
                .build();
    }

    private void validarAspiranteAutenticado(AspiranteCheckoutDTO aspirante, Integer authenticatedUserId, boolean validarTitular) {
        if (!validarTitular) {
            return;
        }
        if (authenticatedUserId == null) {
            throw new DomainException(PagoErrorCode.ASPIRANTE_AUTENTICADO_NO_COINCIDE_FORBIDDEN, null);
        }

        Integer idPersonaUsuario = usuarioService.findIdPersonaById(authenticatedUserId);
        if (idPersonaUsuario != null && aspirante != null && aspirante.idPersona() != null
                && idPersonaUsuario.equals(aspirante.idPersona())) {
            return;
        }

        throw new DomainException(PagoErrorCode.ASPIRANTE_AUTENTICADO_NO_COINCIDE_FORBIDDEN, authenticatedUserId);
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