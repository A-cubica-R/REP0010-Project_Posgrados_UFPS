package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum UbicacionInput {
        ;

        public record UBICACION_CREATE(
                        @NotBlank String direccion,
                        @NotNull Integer idMunicipio)
                        implements CreateType {

                public UBICACION_CREATE(UBICACION_CREATE ubicacion) {
                        this(ubicacion.direccion(), ubicacion.idMunicipio());
                }

        };

        public record UBICACION_DELETE(
                        @NotNull Integer id)
                        implements DeleteType {
        };

        public record UBICACION_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String direccion,
                        @NotNull Integer idMunicipio)
                        implements UpdateType {
        };

        public record UBICACION_PATCH(
                        @NotNull Integer id,
                        @NotBlank String direccion,
                        Integer idMunicipio)
                        implements PatchType {
        };

        public record UBICACION_FIND(
                        @NotNull Integer id)
                        implements FindType {
        };
}
