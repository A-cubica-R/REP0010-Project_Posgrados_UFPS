package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.time.LocalDate;

@Builder
public record PlazoOutput(
        Integer id,
        Integer idTipoplazo,
        LocalDate fechainicio,
        LocalDate fechafin,
        TipoplazoOutput tipoplazo
) implements OutputResponse {}
