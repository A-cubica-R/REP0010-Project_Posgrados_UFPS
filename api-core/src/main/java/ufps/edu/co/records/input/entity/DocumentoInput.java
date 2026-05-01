package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import ufps.edu.co.records.contracts.*;

public enum DocumentoInput {
    ;

    public record DOCUMENTO_CREATE(@NotNull LocalDate fechacargue, @NotNull Integer idEstadodocumento, @NotNull Integer idTipodocumento, @NotNull Integer idAdministrativo, @NotNull Integer idPlazo, @NotNull Integer idAspirante) implements CreateType {}

    public record DOCUMENTO_UPDATE(@NotNull Integer id, @NotNull LocalDate fechacargue, @NotNull Integer idEstadodocumento, @NotNull Integer idTipodocumento, @NotNull Integer idAdministrativo, @NotNull Integer idPlazo, @NotNull Integer idAspirante) implements UpdateType {}

    public record DOCUMENTO_PATCH(@NotNull Integer id, LocalDate fechacargue, Integer idEstadodocumento, Integer idTipodocumento, Integer idAdministrativo, Integer idPlazo, Integer idAspirante) implements PatchType {}

    public record DOCUMENTO_DELETE(@NotNull Integer id) implements DeleteType {}

    public record DOCUMENTO_FIND(@NotNull Integer id) implements FindType {}
}
