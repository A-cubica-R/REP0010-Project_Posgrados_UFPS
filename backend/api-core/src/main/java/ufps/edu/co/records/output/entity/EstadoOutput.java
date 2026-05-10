package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record EstadoOutput(
        Integer id,
        String entidad,
        String tipo,
        List<AdministrativoOutput> administrativoList,
        List<AspiranteOutput> aspiranteList,
        List<CohorteOutput> cohorteList,
        List<EntrevistaOutput> entrevistaList,
        List<PagoOutput> pagoList,
        List<SemestreOutput> semestreList
) implements OutputResponse {
}
