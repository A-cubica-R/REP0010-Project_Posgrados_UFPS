package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum EstadodocumentoInput {
    ;

    public record ESTADODOCUMENTO_CREATE(@NotNull String estado) implements CreateType {}

    public record ESTADODOCUMENTO_UPDATE(@NotNull Integer id, @NotNull String estado) implements UpdateType {}

    public record ESTADODOCUMENTO_PATCH(@NotNull Integer id, String estado) implements PatchType {}

    public record ESTADODOCUMENTO_DELETE(@NotNull Integer id) implements DeleteType {}

    public record ESTADODOCUMENTO_FIND(@NotNull Integer id) implements FindType {}
}
