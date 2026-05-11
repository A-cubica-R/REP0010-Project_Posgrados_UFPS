package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.records.input.entity.CohorteInput.*;
import ufps.edu.co.records.output.entity.CohorteOutput;

@Component
public class CohorteMap extends
        GlobalMapper<COHORTE_CREATE, COHORTE_UPDATE, COHORTE_DELETE, COHORTE_PATCH, COHORTE_FIND, CohorteOutput, CohorteDTO> {

    @Autowired private EstadoMap estadoMap;
    @Autowired private ModalidadMap modalidadMap;
    @Autowired private PlazoMap plazoMap;
    @Autowired private ProgramaMap programaMap;

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
        if (dto == null) return null;
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
                .estado(mapOrNull(dto.getEstado(), estadoMap::toOutput))
                .modalidad(mapOrNull(dto.getModalidad(), modalidadMap::toOutput))
                .plazodocumentacion(mapOrNull(dto.getPlazo(), plazoMap::toOutput))
                .plazoinscripcion(mapOrNull(dto.getPlazo2(), plazoMap::toOutput))
                .plazopago(mapOrNull(dto.getPlazo3(), plazoMap::toOutput))
                .programa(mapOrNull(dto.getPrograma(), programaMap::toOutput))
                .build();
    }

    public List<CohorteOutput> toOutputList(List<CohorteDTO> dtoList) {
        return dtoList.stream()
                .map(this::toOutput)
                .toList();
    }
}
