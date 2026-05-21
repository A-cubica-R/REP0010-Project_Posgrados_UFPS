package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PruebaSimpleOutput(
        Integer idPrueba,
        Integer idAspirante,
        String nombre,
        String estado,
        String motivocambio
) implements OutputResponse {}
