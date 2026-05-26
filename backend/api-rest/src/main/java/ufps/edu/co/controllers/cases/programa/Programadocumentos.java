package ufps.edu.co.controllers.cases.programa;

import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.*;
import ufps.edu.co.records.output.entity.DocumentosrequisitoprogramaOutput;
import ufps.edu.co.processor.cases.DocumentosrequisitoprogramaPE;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping(value = "/director-programa/programa", produces = MediaType.APPLICATION_JSON_VALUE)
public class Programadocumentos {

    @Autowired
    private DocumentosrequisitoprogramaPE processor;

    @PostMapping(value = "/{idPrograma}/documentos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDocumentosrequisitoprograma(
            @RequestBody DOCUMENTOSREQUISITOPROGRAMA_CREATE body,
            @PathVariable Integer idPrograma) {

        if (body != null) {
            try {

                return ResponseEntity.status(HttpStatus.CREATED).body(processor.create(body, idPrograma));

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
        }
        return ResponseEntity.badRequest().build();
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
    public ResponseEntity<List<DocumentosrequisitoprogramaOutput>> getDocsfromProgramaAndConsejo(
            @PathVariable Integer idprograma) {

        if (idprograma != null) {
            try {
                return ResponseEntity.ok(processor.findByIdProgramaAndConsejo(idprograma));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
