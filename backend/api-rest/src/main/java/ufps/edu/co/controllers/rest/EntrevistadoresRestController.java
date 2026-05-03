package ufps.edu.co.controllers.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.processor.crud.EntrevistadoresProcessor;
import ufps.edu.co.records.input.entity.EntrevistadoresInput.*;
import ufps.edu.co.records.output.entity.EntrevistadoresOutput;

@RestController
@RequestMapping(value = "/entrevistadores", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntrevistadoresRestController {

    @Autowired
    private EntrevistadoresProcessor processor;

    @GetMapping("/listall")
    public ResponseEntity<List<EntrevistadoresOutput>> findAll() {
        List<EntrevistadoresOutput> list = processor.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistadoresOutput> findById(@RequestBody ENTREVISTADORES_FIND request) {
        EntrevistadoresOutput output = processor.findById(request);
        if (output != null) {
            return ResponseEntity.ok(output);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<EntrevistadoresOutput> create(@RequestBody ENTREVISTADORES_CREATE request) {
        EntrevistadoresOutput output = processor.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/update")
    public ResponseEntity<EntrevistadoresOutput> update(@RequestBody ENTREVISTADORES_UPDATE request) {
        try {
            EntrevistadoresOutput updated = processor.update(request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(@RequestBody ENTREVISTADORES_DELETE request) {
        try {
            processor.deleteById(request);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listall")
    public ResponseEntity<List<EntrevistadoresOutput>> listAll() {
        List<EntrevistadoresOutput> list = processor.findAll();
        return ResponseEntity.ok(list);
    }

    @PatchMapping(value = "/patch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistadoresOutput> patch(@RequestBody ENTREVISTADORES_PATCH request) {
        try {
            EntrevistadoresOutput output = processor.patch(request);
            return ResponseEntity.ok(output);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
