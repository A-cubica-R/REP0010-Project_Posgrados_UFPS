package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record TipopagoOutput(
        Integer id,
        String tipo,
        List<PagosOutput> pagosList
) implements OutputResponse {}