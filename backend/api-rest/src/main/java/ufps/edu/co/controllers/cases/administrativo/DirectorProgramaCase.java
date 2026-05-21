package ufps.edu.co.controllers.cases.administrativo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.DuplicateAdmisionException;
import ufps.edu.co.exception.PdfEmailException;
import ufps.edu.co.processor.crud.AdministrativoProcessor;
import ufps.edu.co.processor.crud.AspiranteProcessor;
import ufps.edu.co.processor.crud.CalificacioncriterioProcessor;
import ufps.edu.co.processor.crud.CriterioevaluacionProcessor;
import ufps.edu.co.processor.crud.DocumentoProcessor;
import ufps.edu.co.processor.crud.EntrevistaProcessor;
import ufps.edu.co.processor.crud.ListaadmitidosProcessor;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_FIND;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.CalificacioncriterioInput.CALIFICACIONCRITERIO_CREATE;
import ufps.edu.co.records.input.entity.CohorteInput.COHORTE_DIRECTOR_CREATE;
import ufps.edu.co.records.input.entity.CohorteInput.COHORTE_DIRECTOR_UPDATE;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.CRITERIO_BULK_SAVE;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.CRITERIO_CREATE_BODY;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.CRITERIO_UPDATE_BODY;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.CRITERIOEVALUACION_FIND;
import ufps.edu.co.records.input.entity.CalificacioncriterioInput.CALIFICACIONCRITERIO_FIND_BY_ASPIRANTE;
import ufps.edu.co.records.input.entity.CalificacioncriterioInput.CALIFICACIONCRITERIO_UPDATE;
import ufps.edu.co.records.input.entity.DocumentoInput.DOCUMENTO_ESTADO_UPDATE;
import ufps.edu.co.records.output.entity.CalificacionCriterioSimpleOutput;
import ufps.edu.co.records.output.entity.CalificacioncriterioOutput;
import ufps.edu.co.records.output.entity.AspiranteCohorteOutput;
import ufps.edu.co.records.output.entity.AspiranteDocumentosOutput;
import ufps.edu.co.records.output.entity.CriterioevaluacionOutput;
import ufps.edu.co.records.output.entity.DocumentoEstadoOutput;
import ufps.edu.co.records.output.entity.SuccessOutput;
import ufps.edu.co.records.input.entity.DocumentoInput.DOCUMENTO_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_CREATE;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_RATE;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_RESCHEDULE;
import ufps.edu.co.records.input.entity.ListaadmitidosInput.GENERATE_LISTA;
import ufps.edu.co.records.input.entity.ListaadmitidosInput.RECHAZAR_ASPIRANTE;
import ufps.edu.co.records.input.entity.ProgramaInput.PROGRAMA_FIND;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.CohorteDetalleOutput;
import ufps.edu.co.records.output.entity.CohorteListadoOutput;
import ufps.edu.co.records.output.entity.CohorteResumenOutput;
import ufps.edu.co.records.output.entity.ListaAdmitidosResumenOutput;
import ufps.edu.co.records.output.entity.CriteriosCohorteOutput;
import ufps.edu.co.records.output.entity.ProgramaInicioOutput;
import ufps.edu.co.records.output.entity.AspiranteCalificacionOutput;
import ufps.edu.co.records.output.entity.AspiranteCriteriosOutput;
import ufps.edu.co.records.output.entity.AspiranteOutput;
import ufps.edu.co.records.output.entity.DocumentoOutput;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.records.output.entity.EntrevistaResumenOutput;
import ufps.edu.co.records.output.entity.EntrevistaSimpleOutput;
import ufps.edu.co.records.output.entity.ListaadmitidosOutput;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.dto.UsuarioDTO;
import ufps.edu.co.rest.services.AdministrativoService;
import ufps.edu.co.rest.services.UsuarioService;
import ufps.edu.co.services.EmailService;

import ufps.edu.co.services.PdfGeneratorService;
import ufps.edu.co.services.S3Service;

@RestController
@RequestMapping("/director-programa")
public class DirectorProgramaCase {

    private static final Logger logger = LoggerFactory.getLogger(DirectorProgramaCase.class);

    @Autowired
    private S3Service s3Service;

    @Autowired
    private DocumentoProcessor documentoProcessor;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @Autowired
    private AdministrativoProcessor administrativoProcessor;

    @Autowired
    private AspiranteProcessor aspiranteProcessor;

    @Autowired
    private EntrevistaProcessor entrevistaProcessor;

    @Autowired
    private ListaadmitidosProcessor listaadmitidosProcessor;

    @Autowired
    private CalificacioncriterioProcessor calificacioncriterioProcessor;

    @Autowired
    private CriterioevaluacionProcessor criterioevaluacionProcessor;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AdministrativoService administrativoService;

    @GetMapping(value = "/cohortes")
    public ResponseEntity<List<CohorteResumenOutput>> getCohortesByPrograma() {
        try {
            Integer programaId = resolvePrograma();
            return ResponseEntity.ok(aspiranteProcessor.getCohortesByProgramaResumen(programaId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/cohortes/{idCohorte}/aspirantes")
    public ResponseEntity<List<AspiranteCohorteOutput>> getAspirantesByCohorte(@PathVariable Integer idCohorte) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.findByCohorteConResumen(idCohorte));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/aspirantes/{idAspirante}/documentos")
    public ResponseEntity<AspiranteDocumentosOutput> getDocumentosDeAspirante(@PathVariable Integer idAspirante) {
        try {
            return ResponseEntity.ok(documentoProcessor.getDocumentosDeAspirante(idAspirante));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(value = "/documentos/{idDoc}/estado", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentoEstadoOutput> updateEstadoDocumento(
            @PathVariable Integer idDoc,
            @RequestBody DOCUMENTO_ESTADO_UPDATE body) {
        try {
            return ResponseEntity.ok(documentoProcessor.updateEstadoDocumento(idDoc, body));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/downloadByDocumentId", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> download(@RequestBody DOCUMENTO_FIND request) {
        try {
            byte[] fileContent = s3Service.downloadDocument(request);
            DocumentoOutput fileInfo = documentoProcessor.findById(request);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileInfo.keyfile() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping(value = "/aspirantsWithDocuments")
    public ResponseEntity<List<AspiranteOutput>> findAspirantsWithDocuments() {
        try {
            List<AspiranteOutput> outputs = aspiranteProcessor.findWithDocuments();
            return ResponseEntity.ok(outputs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{idAspirante}/criterios")
    public ResponseEntity<AspiranteCriteriosOutput> getCriteriosCalificacionByAspirantId(
            @PathVariable Integer idAspirante) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.findCriteriosCalificacion(new ASPIRANTE_FIND(idAspirante)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/calificacion/listado")
    public ResponseEntity<List<AspiranteCalificacionOutput>> findAllValidadosCalificacion(
            @RequestParam Integer programaId) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.findAllValidadosCalificacion(programaId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/calificacion/count/validados")
    public ResponseEntity<Long> countValidados(@RequestParam Integer programaId) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.countValidados(programaId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/calificacion/count/por-calificar")
    public ResponseEntity<Long> countPorCalificar(@RequestParam Integer programaId) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.countPorCalificar(programaId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/calificacion/count/calificados")
    public ResponseEntity<Long> countCalificados(@RequestParam Integer programaId) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.countCalificados(programaId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/programa/director/{idUsuario}")
    public ResponseEntity<Integer> findProgramaByUsuarioDirector(@PathVariable Integer idUsuario) {
        try {
            Integer idPrograma = administrativoProcessor.findProgramaDirectorByUsuarioId(idUsuario);
            return ResponseEntity.ok(idPrograma);
        } catch (Exception e) {
            logger.error("Error obteniendo el id del programa del director para el usuario {}", idUsuario, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/programa/{programaId}/cohorte-actual/criterios")
    public ResponseEntity<CriteriosCohorteOutput> getCriteriosByPrograma(@PathVariable Integer programaId) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.getCriteriosByPrograma(programaId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/programa/{programaId}/cohortes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CohorteListadoOutput> createCohorte(
            @PathVariable Integer programaId,
            @RequestBody COHORTE_DIRECTOR_CREATE body) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(aspiranteProcessor.createCohorte(programaId, body));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/programa/{programaId}/cohortes")
    public ResponseEntity<List<CohorteResumenOutput>> listCohorteResumen(@PathVariable Integer programaId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(aspiranteProcessor.getCohortesByProgramaResumen(programaId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/cohorte/{cohorteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CohorteListadoOutput> updateCohorte(
            @PathVariable Integer cohorteId,
            @RequestBody COHORTE_DIRECTOR_UPDATE body) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.updateCohorte(cohorteId, body));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/programa/inicio", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgramaInicioOutput> getProgramaInicio(@RequestBody PROGRAMA_FIND request) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.getProgramaInicio(request.id()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/calificacion/criterio/aspirante", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CalificacioncriterioOutput>> findCalificacionesByAspirante(
            @RequestBody CALIFICACIONCRITERIO_FIND_BY_ASPIRANTE request) {
        try {
            return ResponseEntity.ok(calificacioncriterioProcessor.findByIdAspirante(request.idAspirante()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/{idAspirante}/criterios/calificar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalificacionCriterioSimpleOutput> calificarCriterio(
            @PathVariable Integer idAspirante,
            @RequestBody CALIFICACIONCRITERIO_CREATE request) {
        try {
            CalificacioncriterioOutput result = calificacioncriterioProcessor.create(request);
            CriterioevaluacionOutput criterio = criterioevaluacionProcessor.findById(
                    new CRITERIOEVALUACION_FIND(result.idCriterio()));
            return ResponseEntity.ok(CalificacionCriterioSimpleOutput.builder()
                    .idAspirante(result.idAspirante())
                    .idCriterio(result.idCriterio())
                    .nombreCriterio(criterio.nombre())
                    .puntuacion(result.puntuacion())
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/calificacion/criterio/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalificacioncriterioOutput> updateCalificacionCriterio(
            @RequestBody CALIFICACIONCRITERIO_UPDATE request) {
        try {
            return ResponseEntity.ok(calificacioncriterioProcessor.update(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/cohorte/{cohorteId}")
    public ResponseEntity<CohorteDetalleOutput> getCohorteDetalle(@PathVariable Integer cohorteId) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.getCohorteDetalle(cohorteId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/cohorte/{cohorteId}/aspirantes/paz-y-salvo")
    public ResponseEntity<List<AspiranteOutput>> findPazYSalvoByCohorte(@PathVariable Integer cohorteId) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.findPazYSalvoByCohorte(cohorteId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/programa/{programaId}/cohorte/{cohorteId}/criterios",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CriterioevaluacionOutput> createCriterio(
            @PathVariable Integer programaId,
            @PathVariable Integer cohorteId,
            @RequestBody CRITERIO_CREATE_BODY body) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(criterioevaluacionProcessor.createForCohorte(programaId, cohorteId, body));
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/programa/{programaId}/cohorte/{cohorteId}/criterios/{criterioId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CriterioevaluacionOutput> updateCriterio(
            @PathVariable Integer programaId,
            @PathVariable Integer cohorteId,
            @PathVariable Integer criterioId,
            @RequestBody CRITERIO_UPDATE_BODY body) {
        try {
            return ResponseEntity.ok(
                    criterioevaluacionProcessor.updateForCohorte(programaId, cohorteId, criterioId, body));
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/programa/{programaId}/cohorte/{cohorteId}/criterios/{criterioId}")
    public ResponseEntity<SuccessOutput> deleteCriterio(
            @PathVariable Integer programaId,
            @PathVariable Integer cohorteId,
            @PathVariable Integer criterioId) {
        try {
            criterioevaluacionProcessor.deleteForCohorte(programaId, cohorteId, criterioId);
            return ResponseEntity.ok(SuccessOutput.builder().success(true).build());
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/programa/{programaId}/cohorte/{cohorteId}/criterios/save",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessOutput> bulkSaveCriterios(
            @PathVariable Integer programaId,
            @PathVariable Integer cohorteId,
            @RequestBody CRITERIO_BULK_SAVE body) {
        try {
            criterioevaluacionProcessor.bulkSaveForCohorte(programaId, cohorteId, body);
            return ResponseEntity.ok(SuccessOutput.builder().success(true).build());
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/generateAdmittedList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListaAdmitidosResumenOutput> generateAdmittedList(@RequestBody GENERATE_LISTA request) {
        try {
            return ResponseEntity.ok(listaadmitidosProcessor.generateAdmittedList(request));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/admitAspirants", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListaadmitidosOutput>> admitirAspirantes(@RequestBody GENERATE_LISTA request) {
        List<ListaadmitidosOutput> outputs;
        try {
            outputs = listaadmitidosProcessor.admitirAspirantes(request);
        } catch (DuplicateAdmisionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error al admitir aspirantes para cohorte {}", request.idCohorte(), e);
            return ResponseEntity.internalServerError().build();
        }

        if (outputs.isEmpty()) {
            return ResponseEntity.ok(outputs);
        }

        try {
            String cohorteNombre = outputs.get(0).cohorte() != null
                    ? outputs.get(0).cohorte().nombre()
                    : "Cohorte";

            List<AspiranteOutput> admitidos = outputs.stream()
                    .map(ListaadmitidosOutput::aspirante)
                    .filter(Objects::nonNull)
                    .toList();

            AdministrativoOutput admin = administrativoProcessor.findById(
                    new ADMINISTRATIVO_FIND(request.idAdministrativo()));
            String directorNombre = admin.persona().nombres() + " " + admin.persona().apellidos();

            byte[] pdf = pdfGeneratorService.generarListaAdmitidos(
                    cohorteNombre, LocalDateTime.now(), admitidos, directorNombre);

            emailService.sendPdfToDirector(directorNombre, cohorteNombre, pdf);

        } catch (Exception e) {
            logger.error("Error generando PDF o enviando correo al director tras admisión de aspirantes "
                    + "en cohorte {}", request.idCohorte(), e);
            throw new PdfEmailException(
                    "Aspirantes admitidos correctamente, pero no se pudo enviar el correo al director.", e);
        }

        return ResponseEntity.ok(outputs);
    }

    @PostMapping(value = "/rejectAspirant", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListaadmitidosOutput>> rechazarAspirante(@RequestBody RECHAZAR_ASPIRANTE request) {
        try {
            List<ListaadmitidosOutput> outputs = listaadmitidosProcessor.rechazarAspirante(request);
            return ResponseEntity.ok(outputs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{idAspirante}/entrevistas")
    public ResponseEntity<List<EntrevistaResumenOutput>> findInterviewsByAspirantId(
            @PathVariable Integer idAspirante) {
        try {
            return ResponseEntity.ok(entrevistaProcessor.findByIdAspirante(new ASPIRANTE_FIND(idAspirante)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/{idAspirante}/entrevistas/agendar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaResumenOutput> scheduleInterview(
            @PathVariable Integer idAspirante,
            @RequestBody ENTREVISTA_CREATE request) {
        try {
            return ResponseEntity.ok(toResumen(entrevistaProcessor.create(request)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(value = "/{idAspirante}/entrevistas/reagendar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaResumenOutput> rescheduleInterview(
            @PathVariable Integer idAspirante,
            @RequestBody ENTREVISTA_RESCHEDULE request) {
        try {
            return ResponseEntity.ok(toResumen(entrevistaProcessor.reschedule(request)));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(value = "/{idAspirante}/entrevistas/completar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaSimpleOutput> completeInterview(
            @PathVariable Integer idAspirante,
            @RequestBody ENTREVISTA_FIND request) {
        try {
            return ResponseEntity.ok(toSimple(entrevistaProcessor.completeInterview(request)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(value = "/{idAspirante}/entrevistas/cancelar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaSimpleOutput> cancelInterview(
            @PathVariable Integer idAspirante,
            @RequestBody ENTREVISTA_FIND request) {
        try {
            return ResponseEntity.ok(toSimple(entrevistaProcessor.cancelInterview(request)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private EntrevistaResumenOutput toResumen(EntrevistaOutput o) {
        return EntrevistaResumenOutput.builder()
                .id(o.id())
                .fecha(o.fecha())
                .hora(o.tiempo())
                .idEstado(o.idEstado())
                .idTipoentrevista(o.idTipoentrevista())
                .ubicacion(o.ubicacion() != null ? o.ubicacion().direccion() : null)
                .motivocambio(o.motivocambio())
                .build();
    }

    private EntrevistaSimpleOutput toSimple(EntrevistaOutput o) {
        return EntrevistaSimpleOutput.builder()
                .idEntrevista(o.id())
                .idAspirante(o.idAspirante())
                .estado(o.estado() != null ? o.estado().tipo() : null)
                .motivocambio(null)
                .build();
    }

    private Integer resolvePrograma() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDTO usuario = usuarioService.findByNombreusuario(username);
        if (usuario == null || usuario.getIdPersona() == null) {
            throw new RuntimeException("No se pudo derivar el administrativo desde el usuario autenticado");
        }
        AdministrativoDTO admin = administrativoService.findByIdPersona(usuario.getIdPersona());
        if (admin == null || admin.getCargo() == null || admin.getCargo().getIdPrograma() == null) {
            throw new RuntimeException("El usuario autenticado no tiene un programa asignado");
        }
        return admin.getCargo().getIdPrograma();
    }

    @PutMapping("/interview/rate")
    public ResponseEntity<EntrevistaOutput> rateInterview(@RequestBody ENTREVISTA_RATE request) {
        try {
            EntrevistaOutput update = entrevistaProcessor.rateInterview(request);
            return ResponseEntity.ok(update);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
