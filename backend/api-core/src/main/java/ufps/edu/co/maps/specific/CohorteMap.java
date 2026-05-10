package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.records.input.entity.CohorteInput.*;
import ufps.edu.co.records.output.entity.CohorteOutput;

@Component
public class CohorteMap extends
        GlobalMapper<COHORTE_CREATE, COHORTE_UPDATE, COHORTE_DELETE, COHORTE_PATCH, COHORTE_FIND, CohorteOutput, CohorteDTO> {

    public CohorteMap() {
        super(COHORTE_CREATE.class, COHORTE_UPDATE.class, COHORTE_DELETE.class, COHORTE_PATCH.class,
                COHORTE_FIND.class);
    }

    @Override
    protected CohorteDTO toDtoCreate(COHORTE_CREATE input) {
        return CohorteDTO.builder()
                .nombre(input.nombre())
                .cupos(input.cupos())
                .requiereentrevista(input.requiereentrevista())
                .requiereprueba(input.requiereprueba())
                .idEstado(input.idEstado())
                .idSemestre(input.idSemestre())
                .idModalidad(input.idModalidad())
                .idPlazodocumentacion(input.idPlazodocumentacion())
                .idPlazoinscripcion(input.idPlazoinscripcion())
                .idPlazopago(input.idPlazopago())
                .idPrograma(input.idPrograma())
                .build();
    }

    @Override
    protected CohorteDTO toDtoUpdate(COHORTE_UPDATE input) {
        return CohorteDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .cupos(input.cupos())
                .requiereentrevista(input.requiereentrevista())
                .requiereprueba(input.requiereprueba())
                .idEstado(input.idEstado())
                .idSemestre(input.idSemestre())
                .idModalidad(input.idModalidad())
                .idPlazodocumentacion(input.idPlazodocumentacion())
                .idPlazoinscripcion(input.idPlazoinscripcion())
                .idPlazopago(input.idPlazopago())
                .idPrograma(input.idPrograma())
                .build();
    }

    @Override
    protected CohorteDTO toDtoDelete(COHORTE_DELETE input) {
        return CohorteDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    protected CohorteDTO toDtoPatch(COHORTE_PATCH input) {
        return CohorteDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .cupos(input.cupos())
                .requiereentrevista(input.requiereentrevista())
                .requiereprueba(input.requiereprueba())
                .idEstado(input.idEstado())
                .idSemestre(input.idSemestre())
                .idModalidad(input.idModalidad())
                .idPlazodocumentacion(input.idPlazodocumentacion())
                .idPlazoinscripcion(input.idPlazoinscripcion())
                .idPlazopago(input.idPlazopago())
                .idPrograma(input.idPrograma())
                .build();
    }

    @Override
    protected CohorteDTO toDtoFind(COHORTE_FIND input) {
        return CohorteDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    public CohorteOutput toOutput(CohorteDTO dto) {
        if (dto != null) {
            EstadoMap estadoMap = new EstadoMap();
            // TODO
            // FIXME
            // SemestreMap semestreMap = new SemestreMap();
            ModalidadMap modalidadMap = new ModalidadMap();
            PlazoMap plazoMap = new PlazoMap();
            ProgramaMap programaMap = new ProgramaMap();

            return CohorteOutput.builder()
                    .id(dto.getId())
                    .cupos(dto.getCupos())
                    .requiereentrevista(dto.getRequiereentrevista())
                    .requiereprueba(dto.getRequiereprueba())
                    .id_estado(dto.getIdEstado())
                    .id_semestre(dto.getIdSemestre())
                    .id_modalidad(dto.getIdModalidad())
                    .id_plazodocumentacion(dto.getIdPlazodocumentacion())
                    .id_plazoinscripcion(dto.getIdPlazoinscripcion())
                    .id_plazopago(dto.getIdPlazopago())
                    .id_programa(dto.getIdPrograma())
                    .estado(dto.getEstado() != null ? estadoMap.toOutput(dto.getEstado()) : null)
                    // .semestre(dto.getSemestre() != null ? semestreMap.toOutput(dto.getSemestre())
                    // : null)
                    .modalidad(dto.getModalidad() != null ? modalidadMap.toOutput(dto.getModalidad()) : null)
                    .plazodocumentacion(dto.getPlazo() != null ? plazoMap.toOutput(dto.getPlazo()) : null)
                    .plazoinscripcion(dto.getPlazo2() != null ? plazoMap.toOutput(dto.getPlazo2()) : null)
                    .plazopago(dto.getPlazo3() != null ? plazoMap.toOutput(dto.getPlazo3()) : null)
                    .programa(dto.getPrograma() != null ? programaMap.toOutput(dto.getPrograma()) : null)
                    .build();
        }
        return null;
    }

    public List<CohorteOutput> toOutputList(List<CohorteDTO> dtoList) {
        return dtoList.stream()
                .map(this::toOutput)
                .toList();
    }
}
