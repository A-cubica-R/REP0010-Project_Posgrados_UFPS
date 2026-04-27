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

import ufps.edu.co.processor.ModalidadProcessor;
import ufps.edu.co.records.input.ModalidadInput.*;
import ufps.edu.co.records.output.ModalidadOutput;

@RestController
@RequestMapping(value = "/modalidad", produces = MediaType.APPLICATION_JSON_VALUE)
public class ModalidadRestController {

    @Autowired
    private ModalidadProcessor processor;

    @GetMapping("/listall")
    public ResponseEntity<List<ModalidadOutput>> findAll() {
        List<ModalidadOutput> list = processor.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<ModalidadOutput> findById(@PathVariable int id) {
        ModalidadOutput output = processor.findById(id);
        if (output != null) {
            return ResponseEntity.ok(output);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ModalidadOutput> create(@RequestBody CREATE request) {
        ModalidadOutput output = processor.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ModalidadOutput> update(@PathVariable int id, @RequestBody UPDATE request) {
        try {
            ModalidadOutput updated = processor.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        try {
            processor.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
