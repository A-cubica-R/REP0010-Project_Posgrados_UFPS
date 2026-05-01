package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.time.LocalDate;

@Builder
public record PlazoOutput(
        Integer id,
        LocalDate fechainicio,
        LocalDate fechafin,
        Integer idTipoplazo,
        TipoplazoOutput tipoplazo
) implements OutputResponse {}
