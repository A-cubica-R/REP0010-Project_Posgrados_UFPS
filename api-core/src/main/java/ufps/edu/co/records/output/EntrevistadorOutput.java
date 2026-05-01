package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record EntrevistadorOutput(
        Integer id,
        Integer idAdministrativo,
        String observaciones,
        AdministrativoOutput administrativo
) implements OutputResponse {}
