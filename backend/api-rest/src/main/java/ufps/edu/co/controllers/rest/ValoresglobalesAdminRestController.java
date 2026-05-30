/*
 * Created on 2026-05-30
 */
package ufps.edu.co.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.processor.crud.ValoresglobalesProcessor;
import ufps.edu.co.rest.dto.ValoresglobalesDTO;

@RestController
@RequestMapping(value = "/superadmin/valoresglobales", produces = MediaType.APPLICATION_JSON_VALUE)
public class ValoresglobalesAdminRestController {

    @Autowired
    private ValoresglobalesProcessor valoresglobalesProcessor;

    @PostMapping(value = "/tamano-maximo-archivos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValoresglobalesDTO> createTamanoMaximoArchivos(@RequestBody ValoresglobalesDTO dto) {
        ValoresglobalesDTO created = valoresglobalesProcessor.createTamanoMaximoArchivos(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping(value = "/salario-minimo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValoresglobalesDTO> createSalarioMinimo(@RequestBody ValoresglobalesDTO dto) {
        ValoresglobalesDTO created = valoresglobalesProcessor.createSalarioMinimo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping(value = "/valor-inscripcion", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValoresglobalesDTO> createValorInscripcion(@RequestBody ValoresglobalesDTO dto) {
        ValoresglobalesDTO created = valoresglobalesProcessor.createValorInscripcion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
