package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record TipoplazoOutput(
        Integer id,
        String nombre,
        String descripcion
) implements OutputResponse {}
