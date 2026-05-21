package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record RequisitoDocumentoOutput(
        Integer idRequisito,
        String nombre,
        boolean obligatorio
) implements OutputResponse {
}
