package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

import java.time.LocalDate;

@Builder
public record DocumentoOutput(
        Integer id,
        String enlaceurl,
        LocalDate fechacargue,
        Integer idAdministrativo,
        Integer idAspirante,
        Integer idEstadodocumento,
        Integer idPlazo,
        String keyfile,
        String observaciones,
        Integer idDocumentosrequisitoconsejocohorte,
        Integer idDocumentosrequisitoprogramacohorte,
        AdministrativoOutput administrativo,
        AspiranteOutput aspirante,
        EstadodocumentoOutput estadodocumento,
        PlazoOutput plazo
) implements OutputResponse {}