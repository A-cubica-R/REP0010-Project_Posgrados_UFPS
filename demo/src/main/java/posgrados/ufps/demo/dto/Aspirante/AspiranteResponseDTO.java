package posgrados.ufps.demo.dto.Aspirante;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class AspiranteResponseDTO {// salida

    private Long id;
    private String numeroDocumento;
    private String nombreCompleto;
    private String genero;
    private String paisResidencia;
    private String departamentoResidencia;
    private String municipioResidencia;
    private String direccion;
    private Boolean egresado_ufps;
    private String celular;
    private String tituloPregrado;
    private String universidadEgreso;
    private LocalDate fechaGraduacion;

    private String correoElectronico;
}
