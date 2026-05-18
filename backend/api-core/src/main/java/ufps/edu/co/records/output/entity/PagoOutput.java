package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PagoOutput(
                Integer id,
                Integer idAspirante,
                Integer idEstado,
                Integer idPagoconcepto,
                AspiranteOutput aspirante,
                EstadoOutput estado,
                PagoconceptoOutput pagoconcepto) implements OutputResponse {
}