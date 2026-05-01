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
import ufps.edu.co.processor.EntrevistadorProcessor;
import ufps.edu.co.records.input.EntrevistadorInput.*;
import ufps.edu.co.records.output.EntrevistadorOutput;

@RestController
@RequestMapping(value = "/entrevistador", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntrevistadorRestController {

    @Autowired
    private EntrevistadorProcessor processor;

    @GetMapping("/listall")
    public ResponseEntity<List<EntrevistadorOutput>> findAll() {
        List<EntrevistadorOutput> list = processor.findAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistadorOutput> findById(@RequestBody ENTREVISTADOR_FIND request) {
        EntrevistadorOutput output = processor.findById(request);
        if (output != null) {
            return ResponseEntity.ok(output);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<EntrevistadorOutput> create(@RequestBody ENTREVISTADOR_CREATE request) {
        EntrevistadorOutput output = processor.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/update")
    public ResponseEntity<EntrevistadorOutput> update(@RequestBody ENTREVISTADOR_UPDATE request) {
        try {
            EntrevistadorOutput updated = processor.update(request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteById(@RequestBody ENTREVISTADOR_DELETE request) {
        try {
            processor.deleteById(request);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
