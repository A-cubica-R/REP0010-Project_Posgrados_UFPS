package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CohorteOutput(
    Integer id,
    String nombre,
    Integer cupos,
    Integer idEstado,
    Integer idSemestre,
    Integer idModalidad,
    Integer idPlazodocumentacion,
    Integer idPlazoinscripcion,
    Integer idPlazopago,
    Integer idPrograma,
    EstadoOutput estado,
    SemestreOutput semestre,
    ModalidadOutput modalidad,
    PlazoOutput plazodocumentacion,
    PlazoOutput plazoinscripcion,
    PlazoOutput plazopago,
    ProgramaOutput programa,
    List<AspiranteOutput> aspiranteList,
    List<CriterioevaluacionOutput> criterioevaluacionList,
    List<PruebaOutput> pruebaList
) implements OutputResponse {}
