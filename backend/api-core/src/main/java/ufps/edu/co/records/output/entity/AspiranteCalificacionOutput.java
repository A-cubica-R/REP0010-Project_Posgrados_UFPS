package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;
import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AspiranteCalificacionOutput(
        String nombreCompleto,
        String estadoCalificacion,
        String correo,
        BigDecimal puntajeTotal
) implements OutputResponse {}
