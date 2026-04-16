package posgrados.ufps.demo.dto.Residencia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResidenciaRequestDTO {
    private Long id;
    private Long idMunicipio;
    private String direccion;   
    
}
