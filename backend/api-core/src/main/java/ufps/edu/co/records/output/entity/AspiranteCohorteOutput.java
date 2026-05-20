package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AspiranteCohorteOutput(
        Integer id,
        String nombre,
        String cedula,
        String correo,
        long documentosValidados,
        long totalDocumentos,
        String estadoGeneral
) implements OutputResponse {}
