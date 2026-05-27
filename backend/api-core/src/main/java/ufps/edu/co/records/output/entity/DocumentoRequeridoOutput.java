package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record DocumentoRequeridoOutput(
        Integer id,
        String nombre,
        String urlformato
) implements OutputResponse {
}
