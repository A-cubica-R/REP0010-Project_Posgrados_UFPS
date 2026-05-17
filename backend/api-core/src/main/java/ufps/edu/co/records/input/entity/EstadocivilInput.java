package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum EstadocivilInput {
        ;

        public record ESTADOCIVIL_CREATE(
                        @NotBlank String estado) implements CreateType {
        }

        public record ESTADOCIVIL_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String estado) implements UpdateType {
        }

        public record ESTADOCIVIL_PATCH(
                        @NotNull Integer id,
                        String estado) implements PatchType {
        }

        public record ESTADOCIVIL_DELETE(@NotNull Integer id) implements DeleteType {
        }

        public record ESTADOCIVIL_FIND(@NotNull Integer id) implements FindType {
        }
}
