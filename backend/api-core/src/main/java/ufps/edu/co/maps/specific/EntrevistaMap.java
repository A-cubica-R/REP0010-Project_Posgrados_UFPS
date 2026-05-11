package ufps.edu.co.maps.specific;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.EntrevistaInput.*;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.rest.dto.EntrevistaDTO;
import ufps.edu.co.rest.dto.EntrevistadorDTO;

@Component
public class EntrevistaMap extends
        GlobalMapper<ENTREVISTA_CREATE, ENTREVISTA_UPDATE, ENTREVISTA_DELETE, ENTREVISTA_PATCH, ENTREVISTA_FIND, EntrevistaOutput, EntrevistaDTO> {

    @Autowired private EntrevistadorMap entrevistadorMap;
    @Autowired private AspiranteMap aspiranteMap;
    @Autowired private EstadoMap estadoMap;
    @Autowired private TipoentrevistaMap tipoentrevistaMap;
    @Autowired private UbicacionMap ubicacionMap;

    public EntrevistaMap() {
        super(ENTREVISTA_CREATE.class, ENTREVISTA_UPDATE.class, ENTREVISTA_DELETE.class, ENTREVISTA_PATCH.class,
                ENTREVISTA_FIND.class);
    }

    @Override
    protected EntrevistaDTO toDtoCreate(ENTREVISTA_CREATE input) {
        return EntrevistaDTO.builder()
                .fecha(input.fecha())
                .tiempo(input.tiempo())
                .idAspirante(input.idAspirante())
                .idEstado(input.idEstado())
                .idTipoentrevista(input.idTipoentrevista())
                .idUbicacion(input.idUbicacion())
                .entrevistadorList(
                        input.idEntrevistadores().stream().map(id -> {
                            return EntrevistadorDTO.builder().id(id).build();
                        }).collect(Collectors.toList()))
                .build();
    }

    @Override
    protected EntrevistaDTO toDtoUpdate(ENTREVISTA_UPDATE input) {
        return EntrevistaDTO.builder()
                .id(input.id())
                .fecha(input.fecha())
                .tiempo(input.tiempo())
                .idAspirante(input.idAspirante())
                .idEstado(input.idEstado())
                .idTipoentrevista(input.idTipoentrevista())
                .idUbicacion(input.idUbicacion())
                .entrevistadorList(
                        input.idEntrevistadores().stream().map(id -> {
                            return EntrevistadorDTO.builder().id(id).build();
                        }).collect(Collectors.toList()))
                .build();
    }

    @Override
    protected EntrevistaDTO toDtoDelete(ENTREVISTA_DELETE input) {
        return EntrevistaDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    protected EntrevistaDTO toDtoPatch(ENTREVISTA_PATCH input) {
        // TODO Aún no usaremos funciones PATCH
        throw new UnsupportedOperationException("Función PATCH no implementada para Entrevista");
    }

    @Override
    protected EntrevistaDTO toDtoFind(ENTREVISTA_FIND input) {
        EntrevistaDTO dto = new EntrevistaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public EntrevistaOutput toOutput(EntrevistaDTO dto) {
        if (dto == null) return null;
        return EntrevistaOutput.builder()
                .id(dto.getId())
                .calificacion(dto.getCalificacion())
                .fecha(dto.getFecha())
                .tiempo(dto.getTiempo())
                .idAspirante(dto.getIdAspirante())
                .idEstado(dto.getIdEstado())
                .idTipoentrevista(dto.getIdTipoentrevista())
                .idUbicacion(dto.getIdUbicacion())
                .aspirante(dto.getAspirante() != null ? aspiranteMap.toOutput(dto.getAspirante()) : null)
                .estado(dto.getEstado() != null ? estadoMap.toOutput(dto.getEstado()) : null)
                .tipoentrevista(dto.getTipoentrevista() != null ? tipoentrevistaMap.toOutput(dto.getTipoentrevista()) : null)
                .ubicacion(dto.getUbicacion() != null ? ubicacionMap.toOutput(dto.getUbicacion()) : null)
                .entrevistadores(dto.getEntrevistadorList() != null && !dto.getEntrevistadorList().isEmpty()
                        ? dto.getEntrevistadorList().stream().map(entrevistadorMap::toOutput).toList()
                        : null)
                .build();
    }

    public List<EntrevistaOutput> toOutputList(List<EntrevistaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}