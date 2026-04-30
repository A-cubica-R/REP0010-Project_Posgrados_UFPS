package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;
import ufps.edu.co.records.input.UbicacionInput.UBICACION_CREATE;

public enum SedeInput {
    ;

    public record SEDE_CREATE(
            @NotNull String nombre,
            @NotNull Integer idUbicacion) implements CreateType {
    }

    public record SEDE_UPDATE(
            @NotNull Integer id,
            @NotNull String nombre,
            @NotNull Integer idUbicacion) implements UpdateType {
    }

    public record SEDE_PATCH(
            @NotNull Integer id,
            @NotNull String nombre,
            @NotNull Integer idUbicacion) implements PatchType {
    }

    public record SEDE_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record SEDE_FIND(@NotNull Integer id) implements FindType {
    }

    public record SEDE_CREATE_WITH_UBICACION(
            @NotNull String nombre,
            @NotNull UBICACION_CREATE ubicacion) implements CreateType {
    }
}
