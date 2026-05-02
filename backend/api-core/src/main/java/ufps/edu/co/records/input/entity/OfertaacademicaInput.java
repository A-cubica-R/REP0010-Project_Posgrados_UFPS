package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import ufps.edu.co.records.contracts.*;

public enum OfertaacademicaInput {
    ;

    public record OFERTAACADEMICA_CREATE(@NotBlank String encuentros, @NotNull Integer idPrograma, @NotNull Integer idModalidad, @NotNull Integer idJornada) implements CreateType {}

    public record OFERTAACADEMICA_UPDATE(@NotNull Integer id, @NotBlank String encuentros, @NotNull Integer idPrograma, @NotNull Integer idModalidad, @NotNull Integer idJornada) implements UpdateType {}

    public record OFERTAACADEMICA_PATCH(@NotNull Integer id, String encuentros, Integer idPrograma, Integer idModalidad, Integer idJornada) implements PatchType {}

    public record OFERTAACADEMICA_DELETE(@NotNull Integer id) implements DeleteType {}

    public record OFERTAACADEMICA_FIND(@NotNull Integer id) implements FindType {}
}
