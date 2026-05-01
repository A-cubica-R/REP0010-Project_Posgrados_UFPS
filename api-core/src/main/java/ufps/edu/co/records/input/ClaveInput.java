package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum ClaveInput {
    ;

    public record CLAVE_CREATE() implements CreateType {}

    public record CLAVE_UPDATE(@NotNull Integer id) implements UpdateType {}

    public record CLAVE_PATCH(@NotNull Integer id) implements PatchType {}

    public record CLAVE_DELETE(@NotNull Integer id) implements DeleteType {}

    public record CLAVE_FIND(@NotNull Integer id) implements FindType {}
}
