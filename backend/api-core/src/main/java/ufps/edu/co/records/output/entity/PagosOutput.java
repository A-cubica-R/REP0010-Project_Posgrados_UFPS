package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record PagosOutput(
        Integer id,
        Integer idAspirante,
        Integer idEstado,
        Integer idTipopago,
        AspiranteOutput aspirante,
        EstadoOutput estado,
        TipopagoOutput tipopago
) implements OutputResponse {}