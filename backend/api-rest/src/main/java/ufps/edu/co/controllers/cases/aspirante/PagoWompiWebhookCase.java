package ufps.edu.co.controllers.cases.aspirante;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.processor.crud.PagoProcessor;
import ufps.edu.co.records.output.entity.PagoOutput;
import ufps.edu.co.wompi.model.WompiWebhookRequest;

@RestController
@RequestMapping(value = "/wompi", produces = MediaType.APPLICATION_JSON_VALUE)
public class PagoWompiWebhookCase {

    private final PagoProcessor pagoProcessor;

    public PagoWompiWebhookCase(PagoProcessor pagoProcessor) {
        this.pagoProcessor = pagoProcessor;
    }

    @PostMapping(value = "/webhook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagoOutput> recibirWebhook(@RequestBody WompiWebhookRequest request) {
        return ResponseEntity.ok(pagoProcessor.confirmarWebhook(request));
    }
}