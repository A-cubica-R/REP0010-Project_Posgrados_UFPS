package ufps.edu.co.records.output.entity;

import java.time.LocalDate;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AdministrativoOutput(
        Integer id,
        LocalDate fechainicio,
        LocalDate fechasalida,
        PersonaOutput persona,
        EstadoOutput estado,
        CargoOutput cargo) implements OutputResponse {
}
