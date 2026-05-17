package ufps.edu.co.records.output.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record AdministrativoOutput(
        Integer id,
        LocalDate fechainicio,
        LocalDate fechasalida,
        Integer idCargo,
        Integer idEstado,
        Integer idPersona,
        PersonaOutput persona,
        EstadoOutput estado,
        CargoOutput cargo,
        List<DocumentoOutput> documentoList) implements OutputResponse {
}
