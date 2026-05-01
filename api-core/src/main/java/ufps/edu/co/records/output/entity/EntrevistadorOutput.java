package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record EntrevistadorOutput(
        Integer id,
        String observaciones,
        AdministrativoOutput administrativo
) implements OutputResponse {}
