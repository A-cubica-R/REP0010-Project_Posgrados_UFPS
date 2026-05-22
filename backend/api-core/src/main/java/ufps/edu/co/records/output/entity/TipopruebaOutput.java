package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record TipopruebaOutput(
        Integer id,
        String tipo,
        String descripcion
) implements OutputResponse {}
