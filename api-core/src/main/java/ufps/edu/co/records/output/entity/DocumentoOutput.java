package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.time.LocalDate;

@Builder
public record DocumentoOutput(
        Integer id,
        LocalDate fechacargue,
        String enlaceurl,
        String keyfile,
        String observaciones,
        PlazoOutput plazo,
        TipodocumentoOutput tipodocumento,
        EstadodocumentoOutput estadodocumento,
        AdministrativoOutput administrativo,
        AspiranteOutput aspirante
) implements OutputResponse {}