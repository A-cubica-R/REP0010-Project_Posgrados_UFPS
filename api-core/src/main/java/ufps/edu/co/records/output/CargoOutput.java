package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CargoOutput(
        Integer id,
        String nombre,
        String descripcion) implements OutputResponse {
}
