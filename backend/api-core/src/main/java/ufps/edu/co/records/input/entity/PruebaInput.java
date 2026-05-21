package ufps.edu.co.records.input.entity;

import java.time.LocalDate;
import java.time.LocalTime;
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
            @NotBlank String ubicacion) implements CreateType {
    }

    public record PRUEBA_UPDATE(
            @NotNull Integer id,
            @NotBlank String nombre,
            String descripcion,
            @NotNull Integer idAspirante,
            @NotNull Integer idCohorte,
            @NotBlank String ubicacion) implements UpdateType {
    }

    public record PRUEBA_PATCH(
            @NotNull Integer id,
            String nombre,
            String descripcion,
            Integer idAspirante,
            Integer idCohorte,
            String ubicacion) implements PatchType {
    }

    public record PRUEBA_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record PRUEBA_FIND(
            @NotNull Integer id) implements FindType {
    }

    public record PRUEBA_CREAR_REQUEST(
            @NotBlank String nombre,
            String descripcion,
            @NotNull LocalDate fecha,
            @NotNull LocalTime tiempo,
            @NotNull Integer idTipoprueba,
            @NotBlank String ubicacion) implements CreateType {
    }

    public record PRUEBA_REAGENDAR_REQUEST(
            @NotNull LocalDate fecha,
            @NotNull LocalTime tiempo,
            @NotNull Integer idTipoprueba,
            @NotBlank String ubicacion) implements UpdateType {
    }

    public record PRUEBA_CANCELAR_REQUEST(
            @NotBlank String motivocambio) implements PatchType {
    }
}
