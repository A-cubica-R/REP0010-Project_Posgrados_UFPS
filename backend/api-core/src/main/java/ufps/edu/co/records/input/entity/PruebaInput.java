package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum PruebaInput {
    ;

    public record PRUEBA_CREATE(
            @NotBlank String nombre,
            String descripcion,
            @NotNull Integer idAspirante,
            @NotNull Integer idCohorte,
            @NotNull Integer idUbicacion) implements CreateType {
    }

    public record PRUEBA_UPDATE(
            @NotNull Integer id,
            @NotBlank String nombre,
            String descripcion,
            @NotNull Integer idAspirante,
            @NotNull Integer idCohorte,
            @NotNull Integer idUbicacion) implements UpdateType {
    }

    public record PRUEBA_PATCH(
            @NotNull Integer id,
            String nombre,
            String descripcion,
            Integer idAspirante,
            Integer idCohorte,
            Integer idUbicacion) implements PatchType {
    }

    public record PRUEBA_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record PRUEBA_FIND(
            @NotNull Integer id) implements FindType {
    }
}
