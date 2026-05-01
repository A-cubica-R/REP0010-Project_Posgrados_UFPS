package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AspiranteOutput(
        Integer id,
        PersonaOutput persona
) implements OutputResponse {}
