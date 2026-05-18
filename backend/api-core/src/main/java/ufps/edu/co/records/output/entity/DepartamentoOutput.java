package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record DepartamentoOutput(
        Integer id,
        String nombre,
        PaisOutput pais,
        List<MunicipioOutput> municipioList
) implements OutputResponse {}