package ufps.edu.co.records.output.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record SemestreOutput(
        Integer id,
        String nombre,
        LocalDate fechainicio,
        LocalDate fechafin,
        Integer idEstado,
        EstadoOutput estado,
        List<CohorteOutput> cohorteList
) implements OutputResponse {}
