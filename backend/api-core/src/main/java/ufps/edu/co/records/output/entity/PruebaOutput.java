package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PruebaOutput(
        Integer id,
        String nombre,
        String descripcion,
        Integer idCohorte,
        Integer idUbicacion,
        UbicacionOutput ubicacion,
        CohorteOutput cohorte,
        List<ResultadopruebaOutput> resultadopruebaList
) implements OutputResponse {}