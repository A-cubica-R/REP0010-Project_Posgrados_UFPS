package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public enum EstadoInput implements InputRequest {
    ;

    public record ESTADO_CREATE(
            @NotBlank String tipo) implements CreateType {
    };

    public record ESTADO_DELETE(
            @NotNull Integer id) implements DeleteType {
    };

    public record ESTADO_UPDATE(
            @NotNull Integer id,
            @NotBlank String tipo) implements UpdateType {
    };

    public record ESTADO_PATCH(
            @NotNull Integer id,
            String tipo) implements PatchType {
    };

    public record ESTADO_FIND(
            @NotNull Integer id) implements FindType {
    };
}
