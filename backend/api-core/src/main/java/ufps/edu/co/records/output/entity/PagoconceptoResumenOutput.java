package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PagoconceptoResumenOutput(
        Integer id,
        String tipo) implements OutputResponse {
}