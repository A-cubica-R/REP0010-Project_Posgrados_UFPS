package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record DocumentocohorteOutput(
        Integer id,
        String nombre,
        Boolean obligatorio,
        Integer idCohorte) implements OutputResponse {
}