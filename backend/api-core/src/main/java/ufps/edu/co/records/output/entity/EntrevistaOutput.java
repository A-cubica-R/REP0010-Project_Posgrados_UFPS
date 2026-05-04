package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.time.LocalDate;
import java.util.List;

@Builder
public record EntrevistaOutput(
        Integer id,
        LocalDate fecha,
        Float calificacion,
        TipoentrevistaOutput tipoentrevista,
        EntrevistadorOutput entrevistador,
        AspiranteOutput aspirante,
        EstadoOutput estado,
        String nombreAspirante,
        List<String> nombresEntrevistadores
) implements OutputResponse {}
