package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.InputRequest;

public enum MunicipioInput implements InputRequest {
        ;

        public record MUNICIPIO_CREATE(
                        @NotBlank String nombre,
                        @NotNull Integer idDepartamento)
                        implements InputRequest {
        };

        public record MUNICIPIO_DELETE(
                        @NotNull Integer id) implements InputRequest {
        };

        public record MUNICIPIO_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String nombre,
                        @NotNull Integer idDepartamento) implements InputRequest {
        };

        public record MUNICIPIO_PATCH(
                        @NotNull Integer id,
                        String nombre,
                        Integer idDepartamento) implements InputRequest {
        };

        public record MUNICIPIO_FIND(
                        @NotNull Integer id) implements InputRequest {
        };
}
