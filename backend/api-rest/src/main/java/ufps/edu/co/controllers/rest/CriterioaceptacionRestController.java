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
import ufps.edu.co.processor.crud.CriterioaceptacionProcessor;
import ufps.edu.co.records.input.entity.CriterioaceptacionInput.*;
import ufps.edu.co.records.output.entity.CriterioaceptacionOutput;

@RestController
@RequestMapping(value = "/criterioaceptacion", produces = MediaType.APPLICATION_JSON_VALUE)
public class CriterioaceptacionRestController {

    @Autowired
    private CriterioaceptacionProcessor processor;

    @GetMapping("/listall")
    public ResponseEntity<List<CriterioaceptacionOutput>> findAll() {
        List<CriterioaceptacionOutput> list = processor.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CriterioaceptacionOutput> findById(@RequestBody CRITERIOACEPTACION_FIND request) {
        CriterioaceptacionOutput output = processor.findById(request);
        if (output != null) {
            return ResponseEntity.ok(output);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CriterioaceptacionOutput> create(@RequestBody CRITERIOACEPTACION_CREATE request) {
        CriterioaceptacionOutput output = processor.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/update")
    public ResponseEntity<CriterioaceptacionOutput> update(@RequestBody CRITERIOACEPTACION_UPDATE request) {
        try {
            CriterioaceptacionOutput updated = processor.update(request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(@RequestBody CRITERIOACEPTACION_DELETE request) {
        try {
            processor.deleteById(request);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
