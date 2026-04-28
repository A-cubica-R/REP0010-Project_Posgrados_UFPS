package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record JornadaOutput(
        Integer id,
        String tipo
) implements OutputResponse {}
