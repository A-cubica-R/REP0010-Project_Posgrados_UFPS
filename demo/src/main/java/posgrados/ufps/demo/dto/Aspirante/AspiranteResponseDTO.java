package posgrados.ufps.demo.dto.Aspirante;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class AspiranteResponseDTO {// salida

    private Long id;
    private String numeroDocumento;
    private String nombreCompleto;
    private String tituloPregrado;
    private String universidadEgreso;
    private LocalDate fechaGraduacion;
    private String celular;
    private String correoElectronico;
    private BigDecimal promedioAcumulado;
}
