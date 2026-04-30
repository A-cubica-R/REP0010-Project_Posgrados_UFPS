package ufps.edu.co.records.input;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.CreateType;
import ufps.edu.co.records.contracts.DeleteType;
import ufps.edu.co.records.contracts.FindType;
import ufps.edu.co.records.contracts.PatchType;
import ufps.edu.co.records.contracts.UpdateType;

public enum PersonaInput {
    ;

    public record PERSONA_CREATE(
            @NotBlank String nombres,
            @NotBlank String apellidos,
            @NotBlank String correo,
            @NotNull LocalDate fechanacimiento,
            @NotBlank String celular,
            String telefono,
            @NotNull Integer idUbicacion,
            @NotNull Integer idGenero) implements CreateType {
    }

    public record PERSONA_UPDATE(
            @NotNull Integer id,
            @NotBlank String nombres,
            @NotBlank String apellidos,
            @NotBlank String correo,
            @NotNull LocalDate fechanacimiento,
            @NotBlank String celular,
            String telefono,
            @NotNull Integer idUbicacion,
            @NotNull Integer idGenero) implements UpdateType {
    }

    public record PERSONA_PATCH(
            @NotNull Integer id,
            String nombres,
            String apellidos,
            String correo,
            LocalDate fechanacimiento,
            String celular,
            String telefono,
            Integer idUbicacion,
            Integer idGenero) implements PatchType {
    }

    public record PERSONA_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record PERSONA_FIND(
            @NotNull Integer id) implements FindType {
    }
}
