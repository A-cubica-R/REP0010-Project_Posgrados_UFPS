package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record EntrevistadorOutput(
        Integer id,
        Integer idAdministrativo,
        Integer idEntrevista,
        String observaciones,
        AdministrativoOutput administrativo,
        EntrevistaOutput entrevista
) implements OutputResponse {}
