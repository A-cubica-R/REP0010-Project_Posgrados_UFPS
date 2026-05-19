package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;
import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AspiranteCalificacionOutput(
        Integer id,
        String nombreCompleto,
        String estadoCalificacion,
        String correo,
        BigDecimal puntajeTotal
) implements OutputResponse {}
