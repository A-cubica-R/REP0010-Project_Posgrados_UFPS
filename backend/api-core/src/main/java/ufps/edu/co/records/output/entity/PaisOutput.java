package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PaisOutput(
        Integer id,
        String nombre,
        List<DepartamentoOutput> departamentoList
) implements OutputResponse {}
