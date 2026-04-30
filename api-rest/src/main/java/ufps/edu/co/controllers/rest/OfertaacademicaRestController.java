package ufps.edu.co.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.processor.OfertaacademicaProcessor;
import ufps.edu.co.records.input.OfertaacademicaInput.*;
import ufps.edu.co.records.output.OfertaacademicaOutput;

@RestController
@RequestMapping(value = "/ofertaacademica", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfertaacademicaRestController {

    @Autowired
    private OfertaacademicaProcessor processor;

    @GetMapping("/listall")
    public ResponseEntity<List<OfertaacademicaOutput>> findAll() {
        List<OfertaacademicaOutput> list = processor.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfertaacademicaOutput> findById(@RequestBody OFERTAACADEMICA_FIND request) {
        OfertaacademicaOutput output = processor.findById(request);
        if (output != null) {
            return ResponseEntity.ok(output);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<OfertaacademicaOutput> create(@RequestBody OFERTAACADEMICA_CREATE request) {
        OfertaacademicaOutput output = processor.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/update")
    public ResponseEntity<OfertaacademicaOutput> update(@RequestBody OFERTAACADEMICA_UPDATE request) {
        try {
            OfertaacademicaOutput updated = processor.update(request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(@RequestBody OFERTAACADEMICA_DELETE request) {
        try {
            processor.deleteById(request);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
