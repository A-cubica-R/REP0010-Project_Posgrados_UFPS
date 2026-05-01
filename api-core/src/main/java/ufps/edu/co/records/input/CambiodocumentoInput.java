package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum CambiodocumentoInput {
    ;

    public record CAMBIODOCUMENTO_CREATE(@NotNull Integer idDocumentoanterior, @NotNull Integer idDocumentoactual) implements CreateType {}

    public record CAMBIODOCUMENTO_UPDATE(@NotNull Integer id, @NotNull Integer idDocumentoanterior, @NotNull Integer idDocumentoactual) implements UpdateType {}

    public record CAMBIODOCUMENTO_PATCH(@NotNull Integer id, Integer idDocumentoanterior, Integer idDocumentoactual) implements PatchType {}

    public record CAMBIODOCUMENTO_DELETE(@NotNull Integer id) implements DeleteType {}

    public record CAMBIODOCUMENTO_FIND(@NotNull Integer id) implements FindType {}
}
