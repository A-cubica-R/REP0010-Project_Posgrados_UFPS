package ufps.edu.co.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ufps.edu.co.processor.DocumentoProcessor;
import ufps.edu.co.records.input.DocumentoInput.DOCUMENTO_FIND;
import ufps.edu.co.records.output.DocumentoOutput;
import ufps.edu.co.services.S3Service;

@Controller
public class StorageController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private DocumentoProcessor documentoProcessor;

    @PostMapping(value = "/documents", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> download(@RequestBody DOCUMENTO_FIND request) {
        try{
            byte[] fileContent = s3Service.descargarDocumento(request);
            DocumentoOutput fileInfo = documentoProcessor.findById(request);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileInfo.keyfile() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileContent);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }

    }
}
