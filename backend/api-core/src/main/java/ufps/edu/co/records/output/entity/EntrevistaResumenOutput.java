package ufps.edu.co.records.output.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record EntrevistaResumenOutput(
        LocalDate fecha,
        LocalTime hora,
        String estado,
        String modalidad,
        String ubicacion
) implements OutputResponse {}
