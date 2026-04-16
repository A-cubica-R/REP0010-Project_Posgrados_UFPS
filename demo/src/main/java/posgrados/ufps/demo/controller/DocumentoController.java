package posgrados.ufps.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import posgrados.ufps.demo.dto.DocumentoDTO;
import posgrados.ufps.demo.entity.DocumentoEntity;
import posgrados.ufps.demo.service.DocumentoRequeridoService;

@RestController
public class DocumentoController {

    @Autowired
    private DocumentoRequeridoService documentoService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestPart("file") MultipartFile file,
            @RequestPart("datos") DocumentoDTO dto) throws IOException {
        documentoService.cargarDocumento(dto, file);
        return ResponseEntity.ok("¡Archivo subido correctamente!");
    }

    @GetMapping("/{idAspirante}")
    public ResponseEntity<Object> listarDocumentos(@PathVariable Integer idAspirante) {
        return ResponseEntity.ok().body(documentoService.listarDocumentos(idAspirante));
    }

    @GetMapping("/download/{idDocumento}")
    public ResponseEntity<byte[]> download(@PathVariable Integer idDocumento) {
        byte[] data = documentoService.descargarDocumento(idDocumento);
        DocumentoEntity documento = documentoService.buscarPorId(idDocumento);
        String keyFile = documento.getKeyFile();
        String originalFilename = keyFile.substring(keyFile.indexOf("_") + 1);
        System.out.println(originalFilename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFilename + "\"")
                .contentType(MediaType.parseMediaType(documento.getIdTipoDocumentoRequerido().getFormato()))
                .contentLength(data.length)
                .body(data);
    }
}
