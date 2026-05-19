package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CriterioFilaOutput(
        String nombreCriterio,
        BigDecimal puntajeObtenido
) implements OutputResponse {}
