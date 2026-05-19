package ufps.edu.co.controllers.cases.administrativo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ufps.edu.co.processor.crud.AdministrativoProcessor;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_FIND;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.ProgramaOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/ListProgramas")
    public ResponseEntity<List<ProgramaOutput>> findProgramasFacultad(@RequestBody ADMINISTRATIVO_FIND input) {
        List<ProgramaOutput> list = processor.findProgramasFacultad(input);
        return ResponseEntity.ok(list);
    }

}
