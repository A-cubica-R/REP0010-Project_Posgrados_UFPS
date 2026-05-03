package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import ufps.edu.co.records.contracts.*;

public enum DocumentoInput {
    ;

    public record DOCUMENTO_CREATE(
        @NotNull LocalDate fechacargue,
        @NotNull Integer idEstadodocumento,
        @NotNull Integer idTipodocumento,
        @NotNull Integer idAdministrativo,
        @NotNull Integer idPlazo,
        @NotNull Integer idAspirante,
        @NotBlank String enlaceurl,
        @NotBlank String keyfile,
        String observaciones
    ) implements CreateType {}

    public record DOCUMENTO_UPDATE(
        @NotNull Integer id,
        @NotNull LocalDate fechacargue,
        @NotNull Integer idEstadodocumento,
        @NotNull Integer idTipodocumento,
        @NotNull Integer idAdministrativo,
        @NotNull Integer idPlazo,
        @NotNull Integer idAspirante,
        @NotBlank String enlaceurl,
        @NotBlank String keyfile,
        String observaciones
    ) implements UpdateType {}

    public record DOCUMENTO_PATCH(
        @NotNull Integer id,
        LocalDate fechacargue,
        Integer idEstadodocumento,
        Integer idTipodocumento,
        Integer idAdministrativo,
        Integer idPlazo,
        Integer idAspirante,
        String enlaceurl,
        String keyfile,
        String observaciones
    ) implements PatchType {}

    public record DOCUMENTO_DELETE(@NotNull Integer id) implements DeleteType {}

    @Builder
    public record DOCUMENTO_FIND(@NotNull Integer id) implements FindType {}

    public record DOCUMENTO_REJECT(@NotNull Integer id, @NotBlank String observaciones) implements PatchType {}
}