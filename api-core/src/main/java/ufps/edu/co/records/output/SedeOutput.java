package ufps.edu.co.records.output;

import ufps.edu.co.records.OutputResponse;

public record SedeOutput(
        Integer id,
        String nombre,
        UbicacionOutput ubicacion
) implements OutputResponse {
}
