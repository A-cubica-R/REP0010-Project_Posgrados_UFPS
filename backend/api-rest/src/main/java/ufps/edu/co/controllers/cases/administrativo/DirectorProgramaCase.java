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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.domain.exceptions.DuplicateAdmisionException;
import ufps.edu.co.exception.PdfEmailException;
import ufps.edu.co.processor.crud.AdministrativoProcessor;
import ufps.edu.co.processor.crud.AspiranteProcessor;
import ufps.edu.co.processor.crud.DocumentoProcessor;
import ufps.edu.co.processor.crud.EntrevistaProcessor;
import ufps.edu.co.processor.crud.ListaadmitidosProcessor;
import ufps.edu.co.processor.crud.TipodocumentoProcessor;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_FIND;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.DocumentoInput.DOCUMENTO_FIND;
import ufps.edu.co.records.input.entity.DocumentoInput.DOCUMENTO_REJECT;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_CREATE;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_RATE;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_RESCHEDULE;
import ufps.edu.co.records.input.entity.ListaadmitidosInput.GENERATE_LISTA;
import ufps.edu.co.records.input.entity.ListaadmitidosInput.RECHAZAR_ASPIRANTE;
import ufps.edu.co.records.input.entity.TipodocumentoInput.TIPODOCUMENTO_FIND;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.AspiranteCalificacionOutput;
import ufps.edu.co.records.output.entity.AspiranteCriteriosOutput;
import ufps.edu.co.records.output.entity.AspiranteOutput;
import ufps.edu.co.records.output.entity.DocumentoOutput;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.records.output.entity.EntrevistaResumenOutput;
import ufps.edu.co.records.output.entity.ListaadmitidosOutput;
import ufps.edu.co.records.output.entity.PersonaOutput;
import ufps.edu.co.records.output.entity.TipodocumentoOutput;
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
    private TipodocumentoProcessor tipoDocumentoProcessor;

    @Autowired
    private AspiranteProcessor aspiranteProcessor;

    @Autowired
    private EntrevistaProcessor entrevistaProcessor;

    @Autowired
    private ListaadmitidosProcessor listaadmitidosProcessor;

    private String correo = "jljb1704@gmail.com";

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

    @PostMapping(value = "/approveByDocumentId", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentoOutput> approveDocument(@RequestBody DOCUMENTO_FIND request) {
        try {
            DocumentoOutput output = documentoProcessor.approveDocument(request);
            PersonaOutput persona = documentoProcessor.findPersonByDocument(request);
            TipodocumentoOutput documento = tipoDocumentoProcessor
                    .findById(TIPODOCUMENTO_FIND.builder().id(output.tipodocumento().id()).build());
            notifyAspirant(persona.correo(), persona.nombres(), "Su documento " +
                    documento.descripcion() + " ha sido aprobado. ¡Felicidades!");
            notifyAspirant(this.correo, persona.nombres(),
                    "Su documento '" + documento.tipo() + "' ha sido aprobado. ¡Felicidades!");
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/rejectByDocumentId", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentoOutput> rejectDocument(@RequestBody DOCUMENTO_REJECT request) {
        try {
            DocumentoOutput output = documentoProcessor.rejectDocument(request);
            PersonaOutput persona = documentoProcessor
                    .findPersonByDocument(DOCUMENTO_FIND.builder().id(request.id()).build());
            TipodocumentoOutput documento = tipoDocumentoProcessor
                    .findById(TIPODOCUMENTO_FIND.builder().id(output.tipodocumento().id()).build());
            notifyAspirant(persona.correo(), persona.nombres(), "Su documento " +
                    documento.descripcion()
                    + " ha sido rechazado. Realice el cargue nuevamente teniendo en cuenta las observaciones");
            notifyAspirant(this.correo, persona.nombres(), "Su documento '" + documento.tipo()
                    + "' ha sido rechazado. Realice el cargue nuevamente teniendo en cuenta las observaciones");
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/listByAspirantId", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentoOutput>> findDocumentByAspirantId(@RequestBody ASPIRANTE_FIND request) {
        try {
            List<DocumentoOutput> outputs = documentoProcessor.findByAspiranteId(request);
            return ResponseEntity.ok(outputs);
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

    @PostMapping(value = "/aspirants/criteriosById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AspiranteCriteriosOutput> getCriteriosCalificacionByAspirantId(
            @RequestBody ASPIRANTE_FIND request) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.findCriteriosCalificacion(request));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/aspirants/evaluationcriteriaById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AspiranteOutput> getEvaluationCriteriaByAspirantId(@RequestBody ASPIRANTE_FIND request) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.findById(request));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/calificacion/listado")
    public ResponseEntity<List<AspiranteCalificacionOutput>> findAllValidadosCalificacion() {
        try {
            return ResponseEntity.ok(aspiranteProcessor.findAllValidadosCalificacion());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/calificacion/count/validados")
    public ResponseEntity<Long> countValidados() {
        try {
            return ResponseEntity.ok(aspiranteProcessor.countValidados());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/calificacion/count/por-calificar")
    public ResponseEntity<Long> countPorCalificar() {
        try {
            return ResponseEntity.ok(aspiranteProcessor.countPorCalificar());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/calificacion/count/calificados")
    public ResponseEntity<Long> countCalificados() {
        try {
            return ResponseEntity.ok(aspiranteProcessor.countCalificados());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private void notifyAspirant(String email, String name, String message) {
        emailService.sendEmail(
                email,
                "Notificación sobre tu documento - Posgrados UFPS",
                "<p>Hola <strong>" + name + "</strong>,</p><p>" + message + "</p>");
    }

    @PostMapping(value = "/generateAdmittedList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListaadmitidosOutput>> generateAdmittedList(@RequestBody GENERATE_LISTA request) {
        try {
            List<ListaadmitidosOutput> outputs = listaadmitidosProcessor.generateAdmittedList(request);
            return ResponseEntity.ok(outputs);
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

    @PostMapping(value = "/aspirants/interviewsById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntrevistaResumenOutput>> findInterviewsByAspirantId(@RequestBody ASPIRANTE_FIND request) {
        try {
            List<EntrevistaResumenOutput> outputs = entrevistaProcessor.findByIdAspirante(request);
            return ResponseEntity.ok(outputs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/interview/schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaOutput> scheduleInterview(@RequestBody ENTREVISTA_CREATE request) {
        try {
            EntrevistaOutput output = entrevistaProcessor.create(request);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/interview/reschedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaOutput> rescheduleInterview(@RequestBody ENTREVISTA_RESCHEDULE request) {
        try {
            EntrevistaOutput output = entrevistaProcessor.reschedule(request);
            return ResponseEntity.ok(output);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/interview/complete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaOutput> completeInterview(@RequestBody ENTREVISTA_FIND request) {
        try {
            EntrevistaOutput output = entrevistaProcessor.completeInterview(request);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/interview/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaOutput> cancelInterview(@RequestBody ENTREVISTA_FIND request) {
        try {
            EntrevistaOutput output = entrevistaProcessor.cancelInterview(request);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
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
