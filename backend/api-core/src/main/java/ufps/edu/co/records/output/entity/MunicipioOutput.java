package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record MunicipioOutput(
        Integer id,
        Integer idDepartamento,
        String nombre,
        DepartamentoOutput departamento,
        List<UbicacionOutput> ubicacionList
) implements OutputResponse {}
