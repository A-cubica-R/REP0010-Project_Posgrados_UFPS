package ufps.edu.co.controllers.cases.command;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufps.edu.co.processor.crud.OfertaacademicaProcessor;
import ufps.edu.co.records.input.entity.OfertaacademicaInput.*;
import ufps.edu.co.records.output.entity.OfertaacademicaOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/ofertaacademica", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfertaacademicaCommand {

    @Autowired
    private OfertaacademicaProcessor processor;

    @PostMapping("/createWithPlazo")
    public ResponseEntity<OfertaacademicaOutput> createWithPlazo(
            @RequestBody OFERTAACADEMICA_CREATE_WITH_PLAZO request) {
        OfertaacademicaOutput output = processor.createWithPlazo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

}
