package ufps.edu.co.controllers.cases.administrativo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.processor.crud.EntrevistaProcessor;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_RATE;
import ufps.edu.co.records.output.entity.EntrevistaOutput;

@RestController
@RequestMapping("/interview")
public class ComiteCurricularRestCase {

    @Autowired
    private EntrevistaProcessor entrevistaProcessor;

    @PutMapping("/rate")
    public ResponseEntity<EntrevistaOutput> rateInterview(@RequestBody ENTREVISTA_RATE request){
        try{
            EntrevistaOutput update = entrevistaProcessor.rateInterview(request);
            return ResponseEntity.ok(update);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
