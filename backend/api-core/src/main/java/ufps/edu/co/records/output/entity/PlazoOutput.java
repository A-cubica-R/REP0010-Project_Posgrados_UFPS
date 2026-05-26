package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.time.LocalDate;

@Builder
public record PlazoOutput(
        Integer id,
        Integer idTipoplazo,
        LocalDate fechainicio,
        LocalDate fechafin,
        TipoplazoOutput tipoplazo,
        List<CohorteOutput> cohorteList,
        List<CohorteOutput> cohorteList2,
        List<CohorteOutput> cohorteList3,
        List<DocumentoOutput> documentoList
) implements OutputResponse {}
