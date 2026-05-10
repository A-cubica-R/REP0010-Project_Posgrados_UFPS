package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.time.LocalDate;
import java.util.List;

@Builder
public record DocumentoOutput(
        Integer id,
        String enlaceurl,
        LocalDate fechacargue,
        Integer idAdministrativo,
        Integer idAspirante,
        Integer idEstadodocumento,
        Integer idPlazo,
        Integer idTipodocumento,
        String keyfile,
        String observaciones,
        AdministrativoOutput administrativo,
        AspiranteOutput aspirante,
        EstadodocumentoOutput estadodocumento,
        PlazoOutput plazo,
        TipodocumentoOutput tipodocumento,
        List<CambiodocumentoOutput> cambiodocumentoList,
        List<CambiodocumentoOutput> cambiodocumentoList2
) implements OutputResponse {}