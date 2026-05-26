package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ufps.edu.co.records.contracts.*;

public enum TipodocumentoInput {
    ;

    public record TIPODOCUMENTO_CREATE(@NotBlank String nombre, String descripcion, @NotBlank String extension, @NotNull Integer tamanomaximo) implements CreateType {}

    public record TIPODOCUMENTO_UPDATE(@NotNull Integer id, @NotBlank String nombre, String descripcion, @NotBlank String extension, @NotNull Integer tamanomaximo) implements UpdateType {}

    public record TIPODOCUMENTO_PATCH(@NotNull Integer id, String nombre, String descripcion, String extension, Integer tamanomaximo) implements PatchType {}

    public record TIPODOCUMENTO_DELETE(@NotNull Integer id) implements DeleteType {}

    @Builder
    public record TIPODOCUMENTO_FIND(@NotNull Integer id) implements FindType {}
}
