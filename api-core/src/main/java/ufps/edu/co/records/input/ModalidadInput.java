package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.InputRequest;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public enum ModalidadInput implements InputRequest {
    ;

    public record MODALIDAD_CREATE(
            @NotBlank String nombre) implements CreateType {
    };

    public record MODALIDAD_DELETE(
            @NotNull Integer id) implements DeleteType {
    };

    public record MODALIDAD_UPDATE(
            @NotNull Integer id,
            @NotBlank String nombre) implements UpdateType {
    };

    public record MODALIDAD_PATCH(
            @NotNull Integer id,
            String nombre) implements PatchType {
    };

    public record MODALIDAD_FIND(
            @NotNull Integer id) implements FindType {
    };

}
