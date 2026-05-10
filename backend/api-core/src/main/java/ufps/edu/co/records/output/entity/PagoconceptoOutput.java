package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PagoconceptoOutput(
                Integer id,
                String tipo,
                List<PagoOutput> pagoList) implements OutputResponse {
}