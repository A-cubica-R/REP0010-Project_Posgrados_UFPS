package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record TipoplazoOutput(
        Integer id,
        String nombre,
        String descripcion,
        String tipo,
        List<PlazoOutput> plazoList
) implements OutputResponse {}
