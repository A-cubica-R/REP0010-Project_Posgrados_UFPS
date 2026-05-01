package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum AspiranteInput {
    ;

    public record ASPIRANTE_CREATE(
            @NotNull Integer idPersona) implements CreateType {
    }

    public record ASPIRANTE_UPDATE(
            @NotNull Integer id,
            @NotNull Integer idPersona) implements UpdateType {
    }

    public record ASPIRANTE_PATCH(
            @NotNull Integer id,
            Integer idPersona) implements PatchType {
    }

    public record ASPIRANTE_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record ASPIRANTE_FIND(
            @NotNull Integer id) implements FindType {
    }
}
