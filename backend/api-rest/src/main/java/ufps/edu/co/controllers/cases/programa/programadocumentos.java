package ufps.edu.co.controllers.cases.programa;

import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.*;
import ufps.edu.co.records.output.entity.DocumentosrequisitoprogramaOutput;
import ufps.edu.co.processor.crud.DocumentosrequisitoprogramaProcessor;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping(value = "/director-programa/programa", produces = MediaType.APPLICATION_JSON_VALUE)
public class programadocumentos {

    @Autowired
    private DocumentosrequisitoprogramaProcessor processor;

    @PostMapping(value = "/{programaId}/documentos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDocumentosrequisitoprograma(
            @PathVariable Integer programaId,
            @RequestBody DOCUMENTOSREQUISITOPROGRAMA_CREATE body) {

        if (programaId != null) {
            try {

                return ResponseEntity.status(HttpStatus.CREATED).body(processor.create(body));

            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{programaId}/documentos")
    public ResponseEntity<List<DocumentosrequisitoprogramaOutput>> listDocumentosrequisitoprogramaByPrograma(
            @PathVariable Integer programaId) {

        if (programaId != null) {
            try {

                return ResponseEntity.ok(processor.findByIdPrograma(programaId));

            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
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
                return ResponseEntity.internalServerError().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/documento/{documentoId}")
    public ResponseEntity<Void> deleteDocumentosrequisitoprograma(@PathVariable Integer id) {
        try {

            processor.deleteById(new DOCUMENTOSREQUISITOPROGRAMA_DELETE(id));
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ###################### CASES ######################

    @GetMapping("documentos/requeridos")
    public ResponseEntity<List<DocumentosrequisitoprogramaOutput>> lalalolo(
            @PathVariable Integer documentoId) {

        if (documentoId != null) {
            throw new UnsupportedOperationException("Not implemented yet");
        }
        return ResponseEntity.badRequest().build();
    }
}
