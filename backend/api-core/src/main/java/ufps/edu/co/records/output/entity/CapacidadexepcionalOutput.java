package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CapacidadexepcionalOutput(
        Integer id,
        String tipocapacidad
) implements OutputResponse {}
