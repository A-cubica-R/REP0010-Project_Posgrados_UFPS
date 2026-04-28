package ufps.edu.co.records.output;

import ufps.edu.co.records.OutputResponse;

public record EstadoOutput(
        Integer id,
        String tipo
) implements OutputResponse {
}
