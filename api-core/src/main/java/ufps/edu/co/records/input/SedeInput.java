package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;
import ufps.edu.co.records.input.UbicacionInput.UBICACION_CREATE;
import ufps.edu.co.records.output.UbicacionOutput;

public enum SedeInput {
    ;

    public record SEDE_CREATE(
            @NotNull String nombre,
            @NotNull Integer idDireccion) implements CreateType {
    }

    public record SEDE_UPDATE(
            @NotNull Integer id,
            @NotNull String nombre,
            @NotNull Integer idDireccion) implements UpdateType {
    }

    public record SEDE_PATCH(
            @NotNull Integer id,
            @NotNull String nombre,
            @NotNull Integer idDireccion) implements PatchType {
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
