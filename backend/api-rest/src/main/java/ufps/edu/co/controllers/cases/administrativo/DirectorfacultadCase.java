package ufps.edu.co.controllers.cases.administrativo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import ufps.edu.co.processor.crud.AdministrativoProcessor;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.ProgramaOutput;


@RestController
@RequestMapping(value = "/Directorfacultad", produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectorfacultadCase {

     @Autowired
    private AdministrativoProcessor processor;

     @GetMapping("/listPosibleDirector")
    public ResponseEntity<List<AdministrativoOutput>> findPosiblesDirectores() {
        List<AdministrativoOutput> list = processor.findPosiblesDirectores();
            return ResponseEntity.ok(list);
    }

    public ResponseEntity<List<ProgramaOutput>> findProgramasFacultad() {
        // TODO
        return null;
        /*List<ProgramaOutput> list = processor.findProgramasFacultad();
            return ResponseEntity.ok(list);*/
    }
    
}
