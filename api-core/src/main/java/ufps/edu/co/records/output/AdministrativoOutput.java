package ufps.edu.co.records.output;

import java.time.LocalDate;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AdministrativoOutput(
        Integer id,
        Integer idPersona,
        LocalDate fechainicio,
        LocalDate fechasalida,
        Integer idEstado,
        Integer idCargo,
        PersonaOutput persona,
        EstadoOutput estado,
        CargoOutput cargo) implements OutputResponse {
}
