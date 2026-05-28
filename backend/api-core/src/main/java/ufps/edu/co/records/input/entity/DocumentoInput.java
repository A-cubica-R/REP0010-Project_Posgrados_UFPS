package ufps.edu.co.records.input.entity;

import lombok.Builder;

import java.time.LocalDate;
import ufps.edu.co.records.contracts.*;

public enum DocumentoInput {
    ;

    public record DOCUMENTO_CREATE(
            String enlaceurl,
            LocalDate fechacargue,
            Integer idAdministrativo,
            Integer idAspirante,
            Integer idEstadodocumento,
            Integer idPlazo,
            String keyfile,
            String observaciones,
            Integer idDocumentosrequisitoconsejocohorte,
            Integer idDocumentosrequisitoprogramacohorte) implements CreateType {
    }

    public record DOCUMENTO_UPDATE(
            Integer id,
            LocalDate fechacargue,
            Integer idEstadodocumento,
            Integer idAdministrativo,
            Integer idPlazo,
            Integer idAspirante,
            String enlaceurl,
            String keyfile,
            String observaciones,
            Integer idDocumentosrequisitoconsejocohorte,
            Integer idDocumentosrequisitoprogramacohorte) implements UpdateType {
    }

    public record DOCUMENTO_PATCH(
            Integer id,
            LocalDate fechacargue,
            Integer idEstadodocumento,
            Integer idAdministrativo,
            Integer idPlazo,
            Integer idAspirante,
            String enlaceurl,
            String keyfile,
            String observaciones,
            Integer idDocumentosrequisitoconsejocohorte,
            Integer idDocumentosrequisitoprogramacohorte) implements PatchType {
    }

    public record DOCUMENTO_DELETE(Integer id) implements DeleteType {
    }

    @Builder
    public record DOCUMENTO_FIND(Integer id) implements FindType {
    }

    @Builder
    public record DOCUMENTO_REJECT(Integer id, String motivoRechazo) implements PatchType {
    }

    public record DOCUMENTO_ESTADO_UPDATE(
            String estado,
            String motivoRechazo) {
    }
}