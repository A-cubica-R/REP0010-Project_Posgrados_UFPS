package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum GeneroInput {
        ;

        public record GENERO_CREATE(
                        @NotBlank String nombre) implements CreateType {
        }

        public record GENERO_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String nombre) implements UpdateType {
        }

        public record GENERO_PATCH(
                        @NotNull Integer id,
                        @NotBlank String nombre) implements PatchType {
        }

        public record GENERO_DELETE(@NotNull Integer id) implements DeleteType {
        }

        public record GENERO_FIND(@NotNull Integer id) implements FindType {
        }
}
