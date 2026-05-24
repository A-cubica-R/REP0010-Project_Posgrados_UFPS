package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PruebaOutput(
        Integer id,
        String nombre,
        String descripcion,
        Integer idAspirante,
        Integer idCohorte,
        Integer idUbicacion,
        AspiranteOutput aspirante,
        UbicacionOutput ubicacion,
        CohorteOutput cohorte) implements OutputResponse {}