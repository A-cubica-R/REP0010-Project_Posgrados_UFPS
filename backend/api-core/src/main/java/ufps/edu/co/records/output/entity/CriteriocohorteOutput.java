package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;
import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CriteriocohorteOutput (
        Integer id,
        Integer idCohorte,
        Integer idCriterio,
        BigDecimal pesoSnapshot,
        CohorteOutput cohorte,
        CriterioevaluacionOutput criterioevaluacion
        ) implements OutputResponse {
}
