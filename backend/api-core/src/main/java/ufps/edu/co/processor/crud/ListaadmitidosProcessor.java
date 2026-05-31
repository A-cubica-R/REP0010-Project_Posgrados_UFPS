package ufps.edu.co.processor.crud;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.DuplicateAdmisionException;
import ufps.edu.co.domain.exceptions.errorcodes.CohorteErrorCode;
import ufps.edu.co.domain.exceptions.errorcodes.ListaadmitidosErrorCode;
import ufps.edu.co.maps.specific.ListaadmitidosMap;
import ufps.edu.co.records.input.entity.ListaadmitidosInput.GENERATE_LISTA;
import ufps.edu.co.records.input.entity.ListaadmitidosInput.RECHAZAR_ASPIRANTE;
import ufps.edu.co.records.output.entity.ListaAdmitidosResumenOutput;
import ufps.edu.co.records.output.entity.ListaadmitidosOutput;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.rest.dto.AdmitidoDTO;
import ufps.edu.co.rest.services.AdministrativoService;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.CohorteService;
import ufps.edu.co.rest.services.ListaadmitidosService;
import ufps.edu.co.records.output.entity.*;
import ufps.edu.co.services.*;

@Service
public class ListaadmitidosProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ListaadmitidosProcessor.class);

    @Autowired
    private CohorteService cohorteService;

    @Autowired
    private AdministrativoService administrativoService;

    @Autowired
    private AspiranteService aspiranteService;

    @Autowired
    private ListaadmitidosService listaadmitidosService;

    @Autowired
    private ListaadmitidosMap map;

    @Autowired
    private SESService sesService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    private String correo = "jljb1704@gmail.com";

    public ListaAdmitidosResumenOutput generateAdmittedList(Integer idCohorte) {
        CohorteDTO cohorte = cohorteService.findById(idCohorte);
        if (cohorte == null) {
                        throw new DomainException(CohorteErrorCode.COHORTE_NOT_FOUND, idCohorte);
        }

        int cupos = cohorte.getCupos();
        List<AspiranteDTO> admitidos = aspiranteService.findAdmitidosByCohorte(idCohorte)
                .stream()
                .limit(cupos)
                .toList();

        long totalAdmitidos = admitidos.size();
        int cuposDisponibles = Math.max(0, cupos - (int) totalAdmitidos);
        boolean activa = cohorte.getEstado() != null
                && "ABIERTA".equalsIgnoreCase(cohorte.getEstado().getTipo());

        List<ListaAdmitidosResumenOutput.AspiranteResumen> aspirantes = admitidos.stream()
                .map(a -> {
                    String nombre = a.getPersona() != null
                            ? ((a.getPersona().getNombres() != null ? a.getPersona().getNombres() : "") + " "
                                    + (a.getPersona().getApellidos() != null ? a.getPersona().getApellidos() : "")).trim()
                            : "";
                    Integer numerodocumento = a.getPersona() != null && a.getPersona().getDocumentopersona() != null
                            ? a.getPersona().getDocumentopersona().getNumerodocumento()
                            : null;
                    return ListaAdmitidosResumenOutput.AspiranteResumen.builder()
                            .id(a.getId())
                            .nombre(nombre)
                            .numerodocumento(numerodocumento)
                            .correo(a.getPersona() != null ? a.getPersona().getCorreo() : null)
                            .puntaje(a.getPuntuacion())
                            .build();
                })
                .toList();

        return ListaAdmitidosResumenOutput.builder()
                .cohorteActual(ListaAdmitidosResumenOutput.CohorteResumen.builder()
                        .id(cohorte.getId())
                        .nombre(cohorte.getNombre())
                        .activa(activa)
                        .cuposDisponibles(cuposDisponibles)
                        .totalAdmitidos(totalAdmitidos)
                        .build())
                .aspirantes(aspirantes)
                .build();
    }

    public List<ListaadmitidosOutput> admitirAspirantes(GENERATE_LISTA input) {
        CohorteDTO cohorte = validateAndGetCohorte(input.idCohorte(), input.idAdministrativo());
        List<AspiranteDTO> admitidos = getTopCandidates(input.idCohorte(), null, cohorte.getCupos());
        LocalDate today = LocalDate.now();
        List<ListaadmitidosOutput> outputs = admitidos.stream()
                .map(a -> {
                    AdmitidoDTO dto = new AdmitidoDTO();
                    dto.setIdCohorte(input.idCohorte());
                    dto.setIdAspirante(a.getId());
                    dto.setFechageneracion(today);
                    if (listaadmitidosService.existsByIdCohorteAndIdAspirante(input.idCohorte(), a.getId())) {
                        throw new DuplicateAdmisionException(a.getId(), input.idCohorte());
                    }
                    this.notifyAspirant(this.correo, a.getPersona().getNombres(), "¡Felicidades! Has sido admitido en el proceso de admisión de posgrados de la UFPS. Por favor, revisa tu correo para más detalles sobre los siguientes pasos.");
                    AdmitidoDTO saved = listaadmitidosService.create(dto);
                    saved.setAspirante(a);
                    return map.toOutput(saved);
                })
                .toList();

        if (!outputs.isEmpty()) {
            try {
                String cohorteNombre = cohorte.getNombre() != null ? cohorte.getNombre() : "Cohorte";
                List<AspiranteOutput> aspirantesAdmitidos = outputs.stream()
                        .map(ListaadmitidosOutput::aspirante)
                        .filter(Objects::nonNull)
                        .toList();
                AdministrativoDTO admin = administrativoService.findById(input.idAdministrativo());
                String directorNombre = admin.getPersona().getNombres() + " " + admin.getPersona().getApellidos();
                String directorCorreo = admin.getPersona().getCorreo();
                byte[] pdf = pdfGeneratorService.generarListaAdmitidos(
                        cohorteNombre, LocalDateTime.now(), aspirantesAdmitidos, directorNombre);
                sesService.sendPdfToDirector(directorCorreo, directorNombre, cohorteNombre, pdf);
            } catch (Exception e) {
                logger.error("Error generando PDF o enviando correo al director tras admisión en cohorte {}", input.idCohorte(), e);
                throw new RuntimeException("Aspirantes admitidos correctamente, pero no se pudo enviar el correo al director.", e);
            }
        }

        return outputs;
    }

    private void notifyAspirant(String email, String name, String message) {
        sesService.enviarCorreo(
                email,
                "Notificación sobre tu Proceso de Admisión - Posgrados UFPS",
                "<p>Hola <strong>" + name + "</strong>,</p><p>" + message + "</p>");
    }

    public List<ListaadmitidosOutput> rechazarAspirante(RECHAZAR_ASPIRANTE input) {
        CohorteDTO cohorte = validateAndGetCohorte(input.idCohorte(), input.idAdministrativo());
        List<AspiranteDTO> nuevosAdmitidos = getTopCandidates(
                input.idCohorte(), input.idAspiranteRechazado(), cohorte.getCupos());
        LocalDate today = LocalDate.now();
        for (AspiranteDTO a : nuevosAdmitidos) {
            if (!listaadmitidosService.existsByIdCohorteAndIdAspirante(input.idCohorte(), a.getId())) {
                AdmitidoDTO dto = new AdmitidoDTO();
                dto.setIdCohorte(input.idCohorte());
                dto.setIdAspirante(a.getId());
                dto.setFechageneracion(today);
                this.notifyAspirant(this.correo, a.getPersona().getNombres(), "¡Lo sentimos! Has sido rechazado en el proceso de admisión de posgrados de la UFPS. Te animamos a revisar los requisitos y volver a aplicar en el futuro. ¡Gracias por tu interés!");
                listaadmitidosService.create(dto);
            }
        }
        listaadmitidosService.deleteByIdCohorteAndIdAspirante(input.idCohorte(), input.idAspiranteRechazado());
        return listaadmitidosService.findByIdCohorte(input.idCohorte()).stream()
                .map(map::toOutput)
                .toList();
    }

    private CohorteDTO validateAndGetCohorte(Integer idCohorte, Integer idAdministrativo) {
        CohorteDTO cohorte = cohorteService.findById(idCohorte);
        if (cohorte == null) {
                        throw new DomainException(CohorteErrorCode.COHORTE_NOT_FOUND, idCohorte);
        }
        AdministrativoDTO admin = administrativoService.findById(idAdministrativo);
        if (admin == null || admin.getCargo() == null) {
                        throw new DomainException(ListaadmitidosErrorCode.DIRECTOR_NO_VALIDO, idAdministrativo);
        }
        if (cohorte.getIdPrograma() == null || admin.getCargo().getIdPrograma() == null
                || !cohorte.getIdPrograma().equals(admin.getCargo().getIdPrograma())) {
                        throw new DomainException(ListaadmitidosErrorCode.COHORTE_NO_PERTENECE_AL_DIRECTOR, idCohorte);
        }
        return cohorte;
    }

    private List<AspiranteDTO> getTopCandidates(Integer idCohorte, Integer excludeIdAspirante, int cupos) {
        List<AspiranteDTO> todos = aspiranteService.findByCohorte(idCohorte);

        List<AspiranteDTO> sinPuntuacion = todos.stream()
                .filter(a -> a.getPuntuacion() == null)
                .toList();

        if (!sinPuntuacion.isEmpty()) {
            sinPuntuacion.forEach(a -> logger.warn(
                    "Aspirante id={} omitido: puntuacion null (cohorte {})", a.getId(), idCohorte));
        }

        return todos.stream()
                .filter(a -> a.getPuntuacion() != null)
                .filter(a -> excludeIdAspirante == null || !excludeIdAspirante.equals(a.getId()))
                .sorted(Comparator.comparing(AspiranteDTO::getPuntuacion, Comparator.reverseOrder()))
                .limit(cupos)
                .toList();
    }
}
