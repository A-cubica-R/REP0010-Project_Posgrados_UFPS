package posgrados.ufps.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import posgrados.ufps.demo.dto.DocumentoDTO;
import posgrados.ufps.demo.service.DocumentoService;

@RestController
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestPart("file") MultipartFile file,
            @RequestPart("datos") DocumentoDTO dto) throws IOException {
        documentoService.cargarDocumento(dto, file);
        return ResponseEntity.ok("¡Archivo subido correctamente!");
    }
}
