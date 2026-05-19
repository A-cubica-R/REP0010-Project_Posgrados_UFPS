package ufps.edu.co.records.input.entity;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;
import ufps.edu.co.records.input.entity.OtrosvaloresInput.OTROSVALORES_CREATE;

public enum ProgramaInput {
    ;

    public record PROGRAMA_CREATE(
            @NotNull Integer codigo,
            @NotBlank String nombre,
            @NotNull Integer semestres,
            String correo,
            String registrosnies,
            String nivelformacion,
            String titulo,
            String rcmineducacion,
            Integer creditos,
            String periodicidad,
            BigDecimal valormatricula,
            @NotNull Integer idSede,
            @NotNull Integer idAdministrativo,
            @NotNull Integer idFacultad,
            @NotNull Integer idOtros) implements CreateType {}

    public record PROGRAMA_UPDATE(
            @NotNull Integer id,
            @NotNull Integer codigo,
            @NotBlank String nombre,
            @NotNull Integer semestres,
            String correo,
            String registrosnies,
            String nivelformacion,
            String titulo,
            String rcmineducacion,
            Integer creditos,
            String periodicidad,
            BigDecimal valormatricula,
            @NotNull Integer idSede,
            @NotNull Integer idAdministrativo,
            @NotNull Integer idFacultad,
            @NotNull Integer idOtros) implements UpdateType {}

    public record PROGRAMA_PATCH(
            @NotNull Integer id,
            Integer codigo,
            String nombre,
            Integer semestres,
            String correo,
            String registrosnies,
            String nivelformacion,
            String titulo,
            String rcmineducacion,
            Integer creditos,
            String periodicidad,
            BigDecimal valormatricula,
            Integer idSede,
            Integer idAdministrativo,
            Integer idFacultad,
            Integer idOtros) implements PatchType {}

    public record PROGRAMA_DELETE(@NotNull Integer id) implements DeleteType {}

    public record PROGRAMA_FIND(@NotNull Integer id) implements FindType {}

    public record PROGRAMA_CREATE_WITH_RELATIONS(
            @NotNull Integer codigo,
            @NotBlank String nombre,
            @NotNull Integer semestres,
            String correo,
            String registrosnies,
            String nivelformacion,
            String titulo,
            String rcmineducacion,
            Integer creditos,
            String periodicidad,
            BigDecimal valormatricula,
            @NotBlank String sedeNombre,
            @NotBlank String tiporegistroTipo,
            @NotNull OTROSVALORES_CREATE otrosvalores) implements CreateType {}

    public record PROGRAMA_UPDATE_WITH_RELATIONS(
            @NotNull Integer id,
            @NotNull Integer codigo,
            @NotBlank String nombre,
            @NotNull Integer semestres,
            String correo,
            String registrosnies,
            String nivelformacion,
            String titulo,
            String rcmineducacion,
            Integer creditos,
            String periodicidad,
            BigDecimal valormatricula,
            @NotBlank String sedeNombre,
            @NotBlank String tiporegistroTipo,
            @NotNull OTROSVALORES_CREATE otrosvalores) implements UpdateType {}
}
