package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record OfertaacademicaOutput(
		Integer id,
		String encuentros,
		Integer cupos,
		ProgramaOutput programa,
		ModalidadOutput modalidad,
		JornadaOutput jornada,
		CohorteOutput cohorte,
		PlazoOutput plazo)
		implements OutputResponse {
}
