package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum CohorteInput {
    ;

    public record COHORTE_CREATE(
            String nombre,
            @NotBlank Integer cupos,
            @NotNull Boolean requiereentrevista,
            @NotNull Boolean requiereprueba,
            @NotNull Integer idEstado,
            @NotNull Integer idSemestre,
            @NotNull Integer idModalidad,
            @NotNull Integer idPlazodocumentacion,
            @NotNull Integer idPlazoinscripcion,
            @NotNull Integer idPlazopago,
            @NotNull Integer idPrograma) implements CreateType {
    }

    public record COHORTE_UPDATE(
            @NotNull Integer id,
            @NotBlank String nombre,
            @NotBlank Integer cupos,
            @NotNull Boolean requiereentrevista,
            @NotNull Boolean requiereprueba,
            @NotNull Integer idEstado,
            @NotNull Integer idSemestre,
            @NotNull Integer idModalidad,
            @NotNull Integer idPlazodocumentacion,
            @NotNull Integer idPlazoinscripcion,
            @NotNull Integer idPlazopago,
            @NotNull Integer idPrograma) implements UpdateType {
    }

    public record COHORTE_PATCH(
            @NotNull Integer id,
            @NotBlank String nombre,
            @NotBlank Integer cupos,
            @NotNull Boolean requiereentrevista,
            @NotNull Boolean requiereprueba,
            @NotNull Integer idEstado,
            @NotNull Integer idSemestre,
            @NotNull Integer idModalidad,
            @NotNull Integer idPlazodocumentacion,
            @NotNull Integer idPlazoinscripcion,
            @NotNull Integer idPlazopago,
            @NotNull Integer idPrograma) implements PatchType {
    }

    public record COHORTE_DELETE(@NotNull Integer id) implements DeleteType {
    }

    public record COHORTE_FIND(@NotNull Integer id) implements FindType {
    }
}
