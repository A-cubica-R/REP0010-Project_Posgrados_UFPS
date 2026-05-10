package ufps.edu.co.controllers.cases.administrativo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.processor.crud.DocumentoProcessor;
import ufps.edu.co.processor.crud.TipodocumentoProcessor;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.DocumentoInput.DOCUMENTO_FIND;
import ufps.edu.co.records.input.entity.DocumentoInput.DOCUMENTO_REJECT;
import ufps.edu.co.records.input.entity.TipodocumentoInput.TIPODOCUMENTO_FIND;
import ufps.edu.co.records.output.entity.DocumentoOutput;
import ufps.edu.co.records.output.entity.PersonaOutput;
import ufps.edu.co.records.output.entity.TipodocumentoOutput;
import ufps.edu.co.services.EmailService;
import ufps.edu.co.services.S3Service;

@RestController
@RequestMapping("/documents")
public class SecretariaCase {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private DocumentoProcessor documentoProcessor;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TipodocumentoProcessor tipoDocumentoProcessor;

    private String correo = "jljb1704@gmail.com";  

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/approve", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentoOutput> approveDocument(@RequestBody DOCUMENTO_FIND request) {
        try {
            DocumentoOutput output = s3Service.approveDocument(request);
            PersonaOutput persona = documentoProcessor.findPersonByDocument(request);
            TipodocumentoOutput documento = tipoDocumentoProcessor.findById(TIPODOCUMENTO_FIND.builder().id(output.tipodocumento().id()).build());
            // notifyAspirant(persona.correo(), persona.nombres(), "Su documento " + documento.nombre() + " ha sido aprobado. ¡Felicidades!");
            notifyAspirant(this.correo, persona.nombres(), "Su documento '" + documento.tipo() + "' ha sido aprobado. ¡Felicidades!");
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/reject", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentoOutput> rejectDocument(@RequestBody DOCUMENTO_REJECT request) {
        try {
            DocumentoOutput output = s3Service.rejectDocument(request);
            PersonaOutput persona = documentoProcessor.findPersonByDocument(DOCUMENTO_FIND.builder().id(request.id()).build());
            TipodocumentoOutput documento = tipoDocumentoProcessor.findById(TIPODOCUMENTO_FIND.builder().id(output.tipodocumento().id()).build());
            // notifyAspirant(persona.correo(), persona.nombres(), "Su documento " + documento.nombre() + " ha sido rechazado. Realice el cargue nuevamente teniendo en cuenta las observaciones");
            notifyAspirant(this.correo, persona.nombres(), "Su documento '" + documento.tipo() + "' ha sido rechazado. Realice el cargue nuevamente teniendo en cuenta las observaciones");
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/aspirantList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentoOutput>> findDocumentByAspirantId(@RequestBody ASPIRANTE_FIND request) {
        try {
            List<DocumentoOutput> outputs = s3Service.findDocumentByAspirantId(request);
            return ResponseEntity.ok(outputs);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void notifyAspirant(String email, String name, String message) {
    emailService.sendEmail(
        email,
        "Notificación sobre tu documento - Posgrados UFPS",
        "<p>Hola <strong>" + name + "</strong>,</p><p>" + message + "</p>"
    );
}

}
