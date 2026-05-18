package ufps.edu.co.processor.crud;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DuplicateAdmisionException;
import ufps.edu.co.maps.specific.ListaadmitidosMap;
import ufps.edu.co.records.input.entity.ListaadmitidosInput.GENERATE_LISTA;
import ufps.edu.co.records.input.entity.ListaadmitidosInput.RECHAZAR_ASPIRANTE;
import ufps.edu.co.records.output.entity.ListaadmitidosOutput;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.rest.dto.ListaadmitidosDTO;
import ufps.edu.co.rest.services.AdministrativoService;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.CohorteService;
import ufps.edu.co.rest.services.ListaadmitidosService;
import ufps.edu.co.services.EmailService;

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
    private EmailService emailService;

    private String correo = "jljb1704@gmail.com";

    public List<ListaadmitidosOutput> generateAdmittedList(GENERATE_LISTA input) {
        CohorteDTO cohorte = validateAndGetCohorte(input.idCohorte(), input.idAdministrativo());
        List<AspiranteDTO> admitidos = getTopCandidates(input.idCohorte(), null, cohorte.getCupos());
        LocalDate today = LocalDate.now();
        return admitidos.stream()
                .map(a -> {
                    ListaadmitidosDTO dto = new ListaadmitidosDTO();
                    dto.setIdCohorte(input.idCohorte());
                    dto.setIdAspirante(a.getId());
                    dto.setFechageneracion(today);
                    dto.setAspirante(a);
                    dto.setCohorte(cohorte);
                    return map.toOutput(dto);
                })
                .toList();
    }

    public List<ListaadmitidosOutput> admitirAspirantes(GENERATE_LISTA input) {
        CohorteDTO cohorte = validateAndGetCohorte(input.idCohorte(), input.idAdministrativo());
        List<AspiranteDTO> admitidos = getTopCandidates(input.idCohorte(), null, cohorte.getCupos());
        LocalDate today = LocalDate.now();
        return admitidos.stream()
                .map(a -> {
                    ListaadmitidosDTO dto = new ListaadmitidosDTO();
                    dto.setIdCohorte(input.idCohorte());
                    dto.setIdAspirante(a.getId());
                    dto.setFechageneracion(today);
                    if (listaadmitidosService.existsByIdCohorteAndIdAspirante(input.idCohorte(), a.getId())) {
                        throw new DuplicateAdmisionException(a.getId(), input.idCohorte());
                    }
                    this.notifyAspirant(this.correo, a.getPersona().getNombres(), "¡Felicidades! Has sido admitido en el proceso de admisión de posgrados de la UFPS. Por favor, revisa tu correo para más detalles sobre los siguientes pasos.");
                    ListaadmitidosDTO saved = listaadmitidosService.create(dto);
                    saved.setAspirante(a);
                    return map.toOutput(saved);
                })
                .toList();
    }

    private void notifyAspirant(String email, String name, String message) {
        emailService.sendEmail(
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
                ListaadmitidosDTO dto = new ListaadmitidosDTO();
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
            throw new RuntimeException("Cohorte no encontrada con id: " + idCohorte);
        }
        AdministrativoDTO admin = administrativoService.findById(idAdministrativo);
        if (admin == null || admin.getCargo() == null) {
            throw new RuntimeException(
                    "Director no encontrado o sin cargo asignado, id: " + idAdministrativo);
        }
        if (cohorte.getIdPrograma() == null || admin.getCargo().getIdPrograma() == null
                || !cohorte.getIdPrograma().equals(admin.getCargo().getIdPrograma())) {
            throw new RuntimeException("La cohorte no pertenece al programa del director de programa");
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
