package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record DocumentoEstadoOutput(
        Integer id,
        String nombre,
        String estado,
        String motivoRechazo
) implements OutputResponse {}
