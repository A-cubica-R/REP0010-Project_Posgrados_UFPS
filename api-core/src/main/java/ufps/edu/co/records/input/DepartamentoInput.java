package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum DepartamentoInput{
        ;

        public record DEPARTAMENTO_CREATE(
                        @NotBlank String nombre,
                        @NotNull Integer idPais)
                        implements CreateType {
        };

        public record DEPARTAMENTO_DELETE(
                        @NotNull Integer id)
                        implements DeleteType {
        };

        public record DEPARTAMENTO_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String nombre,
                        @NotNull Integer idPais)
                        implements UpdateType {
        };

        public record DEPARTAMENTO_PATCH(
                        @NotNull Integer id,
                        String nombre,
                        Integer idPais)
                        implements PatchType {
        };

        public record DEPARTAMENTO_FIND(
                        @NotNull Integer id)
                        implements FindType {
        };
}
