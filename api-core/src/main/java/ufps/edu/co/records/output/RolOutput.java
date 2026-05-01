package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record RolOutput(
        Integer id,
        String nombre
) implements OutputResponse {}
