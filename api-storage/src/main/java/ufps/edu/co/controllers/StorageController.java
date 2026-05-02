package ufps.edu.co.controllers;

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
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.DocumentoInput.DOCUMENTO_FIND;
import ufps.edu.co.records.input.entity.DocumentoInput.DOCUMENTO_REJECT;
import ufps.edu.co.records.output.entity.DocumentoOutput;
import ufps.edu.co.services.S3Service;

@RestController
@RequestMapping("/documents")
public class StorageController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private DocumentoProcessor documentoProcessor;

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
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/reject", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentoOutput> rejectDocument(@RequestBody DOCUMENTO_REJECT request) {
        try {
            DocumentoOutput output = s3Service.rejectDocument(request);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
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

}
