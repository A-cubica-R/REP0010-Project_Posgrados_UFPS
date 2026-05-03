package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record SedeOutput(
        Integer id,
        String nombre,
        UbicacionOutput ubicacion
) implements OutputResponse {
}
