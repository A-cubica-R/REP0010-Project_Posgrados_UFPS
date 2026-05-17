package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record DiscapacidadOutput(
        Integer id,
        String tipodiscapacidad
) implements OutputResponse {}
