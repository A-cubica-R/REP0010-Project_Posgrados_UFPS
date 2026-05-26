package ufps.edu.co.controllers.cases.administrativo;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import ufps.edu.co.processor.crud.FacultadProcessor;
import ufps.edu.co.processor.crud.ProgramaProcessor;
import ufps.edu.co.records.output.entity.FacultadOutput;
import ufps.edu.co.records.output.entity.ProgramaOutput;

@RestController
@RequestMapping(value = "/director-posgrados", produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectorPosgradosCase {

    @Autowired
    private FacultadProcessor processor;

    @Autowired
    private ProgramaProcessor programaProcessor;

    @GetMapping("/listar-facultades")
    public ResponseEntity<List<FacultadOutput>> findAll() {
        List<FacultadOutput> list = processor.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/facultades/{idFacultad}/programas")
    public ResponseEntity<List<ProgramaOutput>> findProgramasByFacultad(@PathVariable Integer idFacultad) {
        List<ProgramaOutput> programas = programaProcessor.findByIdFacultad(idFacultad);
        return ResponseEntity.ok(programas);
    }
}
