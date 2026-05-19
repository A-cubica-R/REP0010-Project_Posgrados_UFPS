package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AspiranteCriteriosOutput(
        List<CriterioFilaOutput> criterios,
        BigDecimal puntajeTotal
) implements OutputResponse {}
