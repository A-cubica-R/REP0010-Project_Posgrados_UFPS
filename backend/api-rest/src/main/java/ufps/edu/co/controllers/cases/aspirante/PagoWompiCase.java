package ufps.edu.co.controllers.cases.aspirante;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.auth.model.AuthPrincipal;
import ufps.edu.co.processor.crud.PagoProcessor;
import ufps.edu.co.records.output.entity.PagoListadoOutput;
import ufps.edu.co.wompi.model.WompiCheckoutResponse;
import ufps.edu.co.wompi.model.WompiReceiptData;

@RestController
@RequestMapping(value = "/aspirantes/{idAspirante}/pagos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PagoWompiCase {

    private final PagoProcessor pagoProcessor;

    public PagoWompiCase(PagoProcessor pagoProcessor) {
        this.pagoProcessor = pagoProcessor;
    }

    @GetMapping
    public ResponseEntity<List<PagoListadoOutput>> listarPagos(@PathVariable Integer idAspirante) {
        return ResponseEntity.ok(pagoProcessor.findByAspirante(idAspirante));
    }

    @PostMapping(value = "/inscripcion/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WompiCheckoutResponse> iniciarCheckoutInscripcion(@PathVariable Integer idAspirante,
            Authentication authentication) {
        Integer authenticatedUserId = extractAuthenticatedUserId(authentication);
        return ResponseEntity.ok(pagoProcessor.iniciarCheckoutInscripcion(idAspirante, authenticatedUserId, true));
    }

    @GetMapping(value = "/inscripcion/recibo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WompiReceiptData> prepararReciboInscripcion(@PathVariable Integer idAspirante,
            Authentication authentication) {
        Integer authenticatedUserId = extractAuthenticatedUserId(authentication);
        return ResponseEntity.ok(pagoProcessor.prepararReciboInscripcion(idAspirante, authenticatedUserId,
                true));
    }

    private Integer extractAuthenticatedUserId(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthPrincipal authPrincipal) {
            return authPrincipal.userId();
        }
        return null;
    }
}