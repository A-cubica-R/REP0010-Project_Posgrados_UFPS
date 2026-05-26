package ufps.edu.co.controllers.cases.programa;

import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.*;
import ufps.edu.co.records.output.cases.Listdocumentosprogramaconsejo;
import ufps.edu.co.records.output.entity.DocumentosrequisitoprogramaOutput;
import ufps.edu.co.services.S3Service;
import ufps.edu.co.processor.cases.DocumentosrequisitoprogramaPE;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/director-programa/programa", produces = MediaType.APPLICATION_JSON_VALUE)
public class Programadocumentos {

    @Autowired
    private DocumentosrequisitoprogramaPE processor;

    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "/documentos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createDocumentosrequisitoprograma(
            @RequestPart("body") DOCUMENTOSREQUISITOPROGRAMA_CREATE body,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            String urlformato = null;
            if (file != null && !file.isEmpty()) {
                urlformato = s3Service.uploadFile(file).enlaceurl();
            }
            DOCUMENTOSREQUISITOPROGRAMA_CREATE bodyConUrl = new DOCUMENTOSREQUISITOPROGRAMA_CREATE(
                    body.nombre(), body.tamanomaximo(), urlformato, body.idPrograma());
            return ResponseEntity.status(HttpStatus.CREATED).body(processor.create(bodyConUrl, body.idPrograma()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{idprograma}/documentos")
    public ResponseEntity<List<DocumentosrequisitoprogramaOutput>> listDocumentosrequisitoprogramaByPrograma(
            @PathVariable Integer idprograma) {

        if (idprograma != null) {
            try {

                return ResponseEntity.ok(processor.findByIdPrograma(idprograma));

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/documento/{documentoId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateDocumentosrequisitoprograma(
            @PathVariable Integer documentoId,
            @RequestBody DOCUMENTOSREQUISITOPROGRAMA_UPDATE body) {

        if (documentoId != null) {
            try {

                return ResponseEntity.status(HttpStatus.CREATED).body(processor.update(body));

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/documento/{documentoId}")
    public ResponseEntity<Void> deleteDocumentosrequisitoprograma(@PathVariable Integer documentoId) {
        try {

            processor.deleteById(new DOCUMENTOSREQUISITOPROGRAMA_DELETE(documentoId));
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ###################### CASES ######################

    @GetMapping("/{idprograma}/documentos/requeridos")
    public ResponseEntity<Listdocumentosprogramaconsejo> getDocsfromProgramaAndConsejo(
            @PathVariable Integer idprograma) {

        if (idprograma != null) {
            try {
                return ResponseEntity.ok(processor.findByIdProgramaAndConsejo(idprograma));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Listdocumentosprogramaconsejo.builder()
                                .documentosConsejo(List.of())
                                .documentosPrograma(List.of())
                                .build());
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
