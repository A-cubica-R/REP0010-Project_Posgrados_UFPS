package posgrados.ufps.demo.dto.Residencia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResidenciaResponseDTO {
    private Long id;
    private String pais;
    private String departamento;
    private String municipio;
    private String direccion;
    
}
