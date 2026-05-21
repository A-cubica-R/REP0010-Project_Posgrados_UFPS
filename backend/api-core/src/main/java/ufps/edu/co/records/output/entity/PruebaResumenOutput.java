package ufps.edu.co.records.output.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PruebaResumenOutput(
        Integer id,
        String nombre,
        String descripcion,
        LocalDate fecha,
        LocalTime tiempo,
        Integer idEstado,
        String estado,
        Integer idTipoprueba,
        String tipoprueba,
        String ubicacion,
        String motivocambio
) implements OutputResponse {}
