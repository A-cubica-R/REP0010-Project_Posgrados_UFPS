package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record ProgramaOutput(
                Integer id,
                String codigo,
                String nombre,
                Integer duracion,
                String correo,
                String registrosnies,
                String nivelformacion,
                String titulo,
                String rcmineducacion,
                Integer creditos,
                String periodicidad,
                BigDecimal valormatricula,
                Integer idFacultad,
                Integer idOtros,
                Integer idSede,
                Integer idTiporegistro,
                Integer idModalidad,
                SedeOutput sede,
                FacultadOutput facultad,
                OtrosvaloresOutput otrosvalores,
                TiporegistroOutput tiporegistro) implements OutputResponse {
}
