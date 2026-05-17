package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum DiscapacidadInput {
        ;

        public record DISCAPACIDAD_CREATE(
                        @NotBlank String tipodiscapacidad) implements CreateType {
        }

        public record DISCAPACIDAD_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String tipodiscapacidad) implements UpdateType {
        }

        public record DISCAPACIDAD_PATCH(
                        @NotNull Integer id,
                        String tipodiscapacidad) implements PatchType {
        }

        public record DISCAPACIDAD_DELETE(@NotNull Integer id) implements DeleteType {
        }

        public record DISCAPACIDAD_FIND(@NotNull Integer id) implements FindType {
        }
}
