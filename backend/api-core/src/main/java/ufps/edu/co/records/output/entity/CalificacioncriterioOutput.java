package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CalificacioncriterioOutput(
        Integer id,
        Integer idAspirante,
        Integer idCriteriocohorte,
        BigDecimal puntuacion,
        BigDecimal pesoSnapshot,
        AspiranteOutput aspirante,
        CriteriocohorteOutput criteriocohorte
) implements OutputResponse {}
