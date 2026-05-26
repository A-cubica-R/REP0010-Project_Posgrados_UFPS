package ufps.edu.co.controllers.cases.aspirante;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.records.output.entity.RequisitoDocumentoOutput;
import ufps.edu.co.rest.services.TipodocumentoService;

@RestController
@RequestMapping(value = "/inscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
public class InscripcionCase {

    @Autowired
    private TipodocumentoService tipodocumentoService;

    @GetMapping("/requisitos")
    public ResponseEntity<List<RequisitoDocumentoOutput>> getRequisitos() {
        List<RequisitoDocumentoOutput> requisitos = tipodocumentoService.findAll().stream()
                .map(t -> RequisitoDocumentoOutput.builder()
                        .idRequisito(t.getId())
                        .nombre(t.getDescripcion())
                        .obligatorio(true)
                        .build())
                .toList();
        return ResponseEntity.ok(requisitos);
    }
}
