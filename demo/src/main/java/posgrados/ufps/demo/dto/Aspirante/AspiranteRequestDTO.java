package posgrados.ufps.demo.dto.Aspirante;

import jakarta.validation.constraints.*;
import lombok.Data;
import posgrados.ufps.demo.entity.EstadoAspiranteEntity;
import posgrados.ufps.demo.entity.OfertaAcademicaEntity;
import posgrados.ufps.demo.entity.TipoDocumentoEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AspiranteRequestDTO {

    @NotNull
    private TipoDocumentoEntity TipoDocumento;

    @NotBlank
    private String numeroDocumento;

    @NotBlank
    private String primerNombre;

    private String segundoNombre;

    @NotBlank
    private String primerApellido;

    private String segundoApellido;

    @NotBlank
    private String tituloPregrado;

    @NotBlank
    private String universidadEgreso;

    @NotNull
    private LocalDate fechaGraduacion;

    private String telefono;

    @NotBlank
    private String celular;

    @NotBlank
    private String direccion;

    @Email
    @NotBlank
    private String correoElectronico;

    @NotNull
    private LocalDate fechaNacimiento;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private BigDecimal promedioAcumulado;

    @NotNull
    private OfertaAcademicaEntity OfertaAcademica;

    private EstadoAspiranteEntity EstadoAspirante;
}
