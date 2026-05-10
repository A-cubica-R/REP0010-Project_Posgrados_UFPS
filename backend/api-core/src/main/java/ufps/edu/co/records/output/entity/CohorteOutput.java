package ufps.edu.co.records.output.entity;

import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CohorteOutput(
    Integer id,
    String nombre,
    Integer cupos,
    Boolean requiereentrevista,
    Boolean requiereprueba,
    Integer idEstado,
    Integer idSemestre,
    Integer idModalidad,
    Integer idPlazodocumentacion,
    Integer idPlazoinscripcion,
    Integer idPlazopago,
    Integer idPrograma,
    Integer id_estado,
    Integer id_semestre,
    Integer id_modalidad,
    Integer id_plazodocumentacion,
    Integer id_plazoinscripcion,
    Integer id_plazopago,
    Integer id_programa,
    EstadoOutput estado,
    SemestreOutput semestre,
    ModalidadOutput modalidad,
    PlazoOutput plazodocumentacion,
    PlazoOutput plazoinscripcion,
    PlazoOutput plazopago,
    ProgramaOutput programa,
    List<AspiranteOutput> aspiranteList,
    List<CriterioaceptacionOutput> criterioaceptacionList,
    List<PruebaOutput> pruebaList
) implements OutputResponse {}
