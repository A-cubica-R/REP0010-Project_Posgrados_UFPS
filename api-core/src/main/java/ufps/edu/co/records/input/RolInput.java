package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum RolInput {
    ;

    public record ROL_CREATE(@NotNull String nombre) implements CreateType {}

    public record ROL_UPDATE(@NotNull Integer id, @NotNull String nombre) implements UpdateType {}

    public record ROL_PATCH(@NotNull Integer id, String nombre) implements PatchType {}

    public record ROL_DELETE(@NotNull Integer id) implements DeleteType {}

    public record ROL_FIND(@NotNull Integer id) implements FindType {}
}
