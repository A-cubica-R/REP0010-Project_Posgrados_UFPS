package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record EntrevistaSimpleOutput(
        Integer idEntrevista,
        Integer idAspirante,
        String estado,
        String motivocambio
) implements OutputResponse {}
