package ufps.edu.co.records.input.entity;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum PagoInput {
    ;

    public record PAGO_CREATE(
        @NotNull Integer idAspirante,
        @NotNull Integer idEstado,
        @NotNull Integer idPagoconcepto) implements CreateType {}

    public record PAGO_UPDATE(
        @NotNull Integer id, 
        @NotNull Integer idAspirante,
        @NotNull Integer idEstado,
        @NotNull Integer idPagoconcepto) implements UpdateType {}

    public record PAGO_PATCH(
        @NotNull Integer id, 
        @NotNull Integer idAspirante,
        @NotNull Integer idEstado,
        @NotNull Integer idPagoconcepto) implements PatchType {}

    public record PAGO_DELETE(
        @NotNull Integer id) implements DeleteType {}

    public record PAGO_FIND(
        @NotNull Integer id) implements FindType {}
}
