package posgrados.ufps.demo.dto.Aspirante;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AspiranteRequestDTO {//entrada

    @NotNull
    private Integer idTipoDocumento;

    @NotBlank
    private String numeroDocumento;

    @NotBlank
    private String primerNombre;

    private String segundoNombre;

    @NotBlank
    private String primerApellido;

    private String segundoApellido;

    @NotNull
    private Integer idGenero;

    @NotNull
    private Integer idPaisResidencia;

    private Integer idDepartamentoResidencia;

    private Integer idMunicipioResidencia;


    //verificar si es necesario el barrio, o mejor direccion completa
    private Integer idBarrioResidencia;

    private Boolean egresado_ufps;

    private String telefono;

    @NotBlank
    private String celular;

    @NotBlank
    private String direccion;

    @NotBlank
    private String tituloPregrado;

    @NotBlank
    private String universidadEgreso;

    @NotNull
    private LocalDate fechaGraduacion;

    @Email
    @NotBlank
    private String correoElectronico;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotNull
    private Integer idOfertaAcademica;

    @NotNull
    private Integer idEstadoAspirante;
}
