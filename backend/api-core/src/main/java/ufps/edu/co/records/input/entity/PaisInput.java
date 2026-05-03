package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.contracts.*;

public enum PaisInput implements InputRequest {
        ;

        public record PAIS_CREATE(
                        @NotBlank String nombre)
                        implements CreateType {
        }

        public record PAIS_DELETE(
                        @NotNull Integer id)
                        implements DeleteType {
        }

        public record PAIS_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String nombre)
                        implements UpdateType {
        }

        public record PAIS_PATCH(
                        @NotNull Integer id,
                        @NotBlank String nombre)
                        implements PatchType {
        }

        public record PAIS_FIND(
                        @NotNull Integer id)
                        implements FindType {
        }
}
