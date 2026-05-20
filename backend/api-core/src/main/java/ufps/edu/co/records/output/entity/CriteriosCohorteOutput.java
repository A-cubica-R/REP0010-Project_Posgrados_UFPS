package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CriteriosCohorteOutput(
        CohorteInfo cohorteActual,
        List<CriterioInfo> criterios
) implements OutputResponse {

    @Builder
    public record CohorteInfo(
            Integer id,
            String nombre,
            boolean activa
    ) {}

    @Builder
    public record CriterioInfo(
            Integer id,
            String nombre,
            String descripcion,
            BigDecimal peso
    ) {}
}
