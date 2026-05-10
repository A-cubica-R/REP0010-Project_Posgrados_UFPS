package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record EntrevistadoresOutput(
        Integer id,
        Integer idEntrevista,
        Integer idAdministrativo,
        EntrevistaOutput entrevista,
        AdministrativoOutput administrativo
) implements OutputResponse {}
