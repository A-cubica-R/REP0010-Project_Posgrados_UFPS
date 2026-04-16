package posgrados.ufps.demo.dto.Aspirante;

import jakarta.validation.constraints.*;
import lombok.Data;
import posgrados.ufps.demo.entity.ResidenciaEntity;

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




    private ResidenciaEntity idResidencia;




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
