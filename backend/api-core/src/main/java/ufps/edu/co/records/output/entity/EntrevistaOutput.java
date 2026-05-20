package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record EntrevistaOutput(
                Integer id,
                BigDecimal calificacion,
                LocalDate fecha,
                LocalTime tiempo,
                String motivocambio,
                Integer idAspirante,
                Integer idEstado,
                Integer idTipoentrevista,
                Integer idUbicacion,
                AspiranteOutput aspirante,
                EstadoOutput estado,
                TipoentrevistaOutput tipoentrevista,
                UbicacionOutput ubicacion) implements OutputResponse {
}
