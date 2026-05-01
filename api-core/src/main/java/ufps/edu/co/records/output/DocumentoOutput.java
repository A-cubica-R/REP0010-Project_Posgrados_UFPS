package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.time.LocalDate;

@Builder
public record DocumentoOutput(
        Integer id,
        LocalDate fechacargue,
        Integer idEstadodocumento,
        Integer idTipodocumento,
        Integer idAdministrativo,
        Integer idPlazo,
        Integer idAspirante,
        PlazoOutput plazo,
        TipodocumentoOutput tipodocumento,
        EstadodocumentoOutput estadodocumento,
        AdministrativoOutput administrativo,
        AspiranteOutput aspirante
) implements OutputResponse {}
