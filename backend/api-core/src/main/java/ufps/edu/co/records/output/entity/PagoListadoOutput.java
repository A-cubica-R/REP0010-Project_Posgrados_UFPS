package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PagoListadoOutput(
        Integer id,
        Integer idAspirante,
        Integer idEstado,
        Integer idPagoconcepto,
        String aspirante,
        String estado,
        PagoconceptoResumenOutput pagoconcepto,
        BigDecimal valorPagoPesos) implements OutputResponse {
}