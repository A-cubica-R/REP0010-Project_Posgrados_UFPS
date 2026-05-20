package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CriterioFilaOutput(
        Integer id,
        String nombreCriterio,
        BigDecimal peso,
        BigDecimal puntajeObtenido
) implements OutputResponse {}
