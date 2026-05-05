package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import ufps.edu.co.records.contracts.*;

public enum OfertaacademicaInput {
    ;

    public record OFERTAACADEMICA_CREATE(
            @NotBlank String encuentros,
            @NotNull Integer cupos,
            @NotNull Integer idPrograma,
            @NotNull Integer idModalidad,
            @NotNull Integer idJornada,
            @NotNull Integer idCohorte,
            @NotNull Integer idPlazo) implements CreateType {
    }

    public record OFERTAACADEMICA_UPDATE(@NotNull Integer id, @NotBlank String encuentros, @NotNull Integer idPrograma,
            @NotNull Integer idModalidad, @NotNull Integer idJornada, @NotNull Integer idCohorte)
            implements UpdateType {
    }

    public record OFERTAACADEMICA_PATCH(@NotNull Integer id, String encuentros, Integer idPrograma, Integer idModalidad,
            Integer idJornada, Integer idCohorte) implements PatchType {
    }

    public record OFERTAACADEMICA_DELETE(@NotNull Integer id) implements DeleteType {
    }

    public record OFERTAACADEMICA_FIND(@NotNull Integer id) implements FindType {
    }

    public record OFERTAACADEMICA_CREATE_WITH_PLAZO(
            @NotBlank String encuentros,
            @NotNull Integer cupos,
            @NotNull Integer idPrograma,
            @NotNull Integer idModalidad,
            @NotNull Integer idJornada,
            @NotNull Integer idCohorte,
            @NotNull PlazoInput.PLAZO_CREATE plazo) implements CreateType {
    }
}
