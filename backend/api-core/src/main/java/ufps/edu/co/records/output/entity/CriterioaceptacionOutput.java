package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CriterioaceptacionOutput(
        Integer id,
        String nombre,
        String descripcion,
        BigDecimal peso,
        Integer idCohorte,
        CohorteOutput cohorte
) implements OutputResponse {}