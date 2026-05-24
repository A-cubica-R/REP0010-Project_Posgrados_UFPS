package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record DocumentosrequisitoconcejocohorteOutput(
        Integer id,
        Integer idDocrequisito,
        Integer idCohorte) implements OutputResponse {
}
