package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum GrupoetnicoInput {
        ;

        public record GRUPOETNICO_CREATE(
                        @NotBlank String grupo) implements CreateType {
        }

        public record GRUPOETNICO_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String grupo) implements UpdateType {
        }

        public record GRUPOETNICO_PATCH(
                        @NotNull Integer id,
                        String grupo) implements PatchType {
        }

        public record GRUPOETNICO_DELETE(@NotNull Integer id) implements DeleteType {
        }

        public record GRUPOETNICO_FIND(@NotNull Integer id) implements FindType {
        }
}
