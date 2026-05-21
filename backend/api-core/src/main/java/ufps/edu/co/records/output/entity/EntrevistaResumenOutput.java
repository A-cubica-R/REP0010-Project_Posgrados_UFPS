package ufps.edu.co.records.output.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record EntrevistaResumenOutput(
        Integer id,
        LocalDate fecha,
        LocalTime tiempo,
        Integer idEstado,
        String estado,
        Integer idTipoentrevista,
        String tipoentrevista,
        String ubicacion,
        String motivocambio
) implements OutputResponse {}
