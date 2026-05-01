package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.time.LocalDate;

@Builder
public record EntrevistaOutput(
        Integer id,
        LocalDate fecha,
        Float calificacion,
        Integer idTipoentrevista,
        Integer idEntrevistador,
        Integer idAspirante,
        Integer idEstado,
        TipoentrevistaOutput tipoentrevista,
        EntrevistadorOutput entrevistador,
        AspiranteOutput aspirante,
        EstadoOutput estado
) implements OutputResponse {}
