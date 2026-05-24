package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ufps.edu.co.records.contracts.*;

public enum TipodocumentopersonaInput {
    ;

    public record TIPODOCUMENTOPERSONA_CREATE(
        @NotBlank String tipo) implements CreateType {}

    public record TIPODOCUMENTOPERSONA_UPDATE(
        @NotNull Integer id, 
        @NotBlank String tipo) implements UpdateType {}

    public record TIPODOCUMENTOPERSONA_PATCH(
        @NotNull Integer id, 
        @NotBlank String tipo) implements PatchType {}

    public record TIPODOCUMENTOPERSONA_DELETE(
        @NotNull Integer id) implements DeleteType {}

    @Builder
    public record TIPODOCUMENTOPERSONA_FIND(
        @NotNull Integer id) implements FindType {}
}
