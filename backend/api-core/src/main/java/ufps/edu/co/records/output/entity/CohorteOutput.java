package ufps.edu.co.records.output.entity;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CohorteOutput(
    Integer id,
    Integer cupos,
    Boolean requiereentrevista,
    Boolean requiereprueba,
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
    ProgramaOutput programa
) implements OutputResponse {}
