package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CalificacionCriterioSimpleOutput(
        Integer idAspirante,
        Integer idCriterio,
        String nombreCriterio,
        BigDecimal puntajeObtenido,
        BigDecimal puntajeTotal
) implements OutputResponse {}
