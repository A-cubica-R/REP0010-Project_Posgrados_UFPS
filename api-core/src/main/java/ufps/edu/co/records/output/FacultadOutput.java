package ufps.edu.co.records.output;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record FacultadOutput(
        Integer id,
        String nombre,
        String correo,
        Integer idAdministrativo)
        implements OutputResponse {
}
