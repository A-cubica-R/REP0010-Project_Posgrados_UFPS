package ufps.edu.co.records.output.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PruebaOutput(
        Integer id,
        String nombre,
        String descripcion,
        LocalDate fecha,
        LocalTime tiempo,
        String motivocambio,
        Integer idAspirante,
        Integer idCohorte,
        Integer idUbicacion,
        Integer idEstado,
        Integer idTipoprueba) implements OutputResponse {}