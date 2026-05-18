package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AspiranteOutput(
        Integer id,
        BigDecimal puntuacion,
        Integer idCohorte,
        Integer idEstado,
        Integer idPersona,
        Integer idTipovinculacion,
        PersonaOutput persona,
        EstadoOutput estado,
        CohorteOutput cohorte,
        List<CalificacioncriterioOutput> calificacioncriterioList,
        List<DocumentoOutput> documentoList,
        List<EntrevistaOutput> entrevistaList,
        List<PagoOutput> pagoList,
        List<ResultadopruebaOutput> resultadopruebaList
) implements OutputResponse {}
