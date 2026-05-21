package ufps.edu.co.records.input.entity;

import java.time.LocalDate;
import java.math.BigDecimal;

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
            @NotNull Integer idUbicacionvivienda,
            Integer idUbicacionnacimiento,
            Integer idUbicaciontrabajo,
            @NotNull Integer idGenero,
            @NotNull Integer idEstadocivil,
            @NotNull Integer idGrupoetnico,
            @NotNull Integer idPoblacionindigena,
            @NotNull Integer idDiscapacidad,
            @NotNull Integer idCapacidadexepcional,
            @NotNull Integer idDocumentopersona,
            BigDecimal promediopregrado,
            @NotBlank String titulopregrado,
            String titulosposgrados,
            String empresa,
            String experiencialaboral,
            @NotNull Boolean egresadoufps) implements CreateType {
    }

    public record PERSONA_UPDATE(
            @NotNull Integer id,
            @NotBlank String nombres,
            @NotBlank String apellidos,
            @NotBlank String correo,
            @NotNull LocalDate fechanacimiento,
            @NotBlank String celular,
            String telefono,
            @NotNull Integer idUbicacionvivienda,
            Integer idUbicacionnacimiento,
            Integer idUbicaciontrabajo,
            @NotNull Integer idGenero,
            @NotNull Integer idEstadocivil,
            @NotNull Integer idGrupoetnico,
            @NotNull Integer idPoblacionindigena,
            @NotNull Integer idDiscapacidad,
            @NotNull Integer idCapacidadexepcional,
            @NotNull Integer idDocumentopersona,
            BigDecimal promediopregrado,
            @NotBlank String titulopregrado,
            String titulosposgrados,
            String empresa,
            String experiencialaboral,
            @NotNull Boolean egresadoufps) implements UpdateType {
    }

    public record PERSONA_PATCH(
            @NotNull Integer id,
            String nombres,
            String apellidos,
            String correo,
            LocalDate fechanacimiento,
            String celular,
            String telefono,
            Integer idUbicacionvivienda,
            Integer idUbicacionnacimiento,
            Integer idUbicaciontrabajo,
            Integer idGenero,
            Integer idEstadocivil,
            Integer idGrupoetnico,
            Integer idPoblacionindigena,
            Integer idDiscapacidad,
            Integer idCapacidadexepcional,
            Integer idDocumentopersona,
            BigDecimal promediopregrado,
            String titulopregrado,
            String titulosposgrados,
            String empresa,
            String experiencialaboral,
            Boolean egresadoufps) implements PatchType {
    }

    public record PERSONA_DELETE(
            @NotNull Integer id) implements DeleteType {
    }

    public record PERSONA_FIND(
            @NotNull Integer id) implements FindType {
    }
}
