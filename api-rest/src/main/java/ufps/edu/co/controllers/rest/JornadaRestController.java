package ufps.edu.co.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.processor.JornadaProcessor;
import ufps.edu.co.records.input.JornadaInput.*;
import ufps.edu.co.records.output.JornadaOutput;

@RestController
@RequestMapping(value = "/jornada", produces = MediaType.APPLICATION_JSON_VALUE)
public class JornadaRestController {

    @Autowired
    private JornadaProcessor processor;

    @GetMapping("/listall")
    public ResponseEntity<List<JornadaOutput>> findAll() {
        List<JornadaOutput> list = processor.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/list")
    public ResponseEntity<JornadaOutput> findById(@PathVariable JORNADA_FIND request) {
        JornadaOutput output = processor.findById(request);
        if (output != null) {
            return ResponseEntity.ok(output);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<JornadaOutput> create(@RequestBody JORNADA_CREATE request) {
        JornadaOutput output = processor.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/update")
    public ResponseEntity<JornadaOutput> update(@PathVariable int id, @RequestBody JORNADA_UPDATE request) {
        try {
            JornadaOutput updated = processor.update(request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(@PathVariable JORNADA_DELETE request) {
        try {
            processor.deleteById(request);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
