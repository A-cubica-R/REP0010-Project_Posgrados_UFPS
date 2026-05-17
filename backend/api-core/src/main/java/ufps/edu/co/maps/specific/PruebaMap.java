package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.PruebaInput.*;
import ufps.edu.co.records.output.entity.PruebaOutput;
import ufps.edu.co.rest.dto.PruebaDTO;

@Component
public class PruebaMap extends
        GlobalMapper<PRUEBA_CREATE, PRUEBA_UPDATE, PRUEBA_DELETE, PRUEBA_PATCH, PRUEBA_FIND, PruebaOutput, PruebaDTO> {

    @Autowired private CohorteMap cohorteMap;
    @Autowired private UbicacionMap ubicacionMap;

    public PruebaMap() {
        super(PRUEBA_CREATE.class, PRUEBA_UPDATE.class, PRUEBA_DELETE.class,
                PRUEBA_PATCH.class, PRUEBA_FIND.class);
    }

    @Override
    protected PruebaDTO toDtoCreate(PRUEBA_CREATE input) {
        return PruebaDTO.builder()
                .nombre(input.nombre())
                .descripcion(input.descripcion())
                .idCohorte(input.idCohorte())
                .idUbicacion(input.idUbicacion())
                .build();
    }

    @Override
    protected PruebaDTO toDtoUpdate(PRUEBA_UPDATE input) {
        return PruebaDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .descripcion(input.descripcion())
                .idCohorte(input.idCohorte())
                .idUbicacion(input.idUbicacion())
                .build();
    }

    @Override
    protected PruebaDTO toDtoDelete(PRUEBA_DELETE input) {
        return PruebaDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    protected PruebaDTO toDtoPatch(PRUEBA_PATCH input) {
        return PruebaDTO.builder()
                .id(input.id())
                .nombre(input.nombre())
                .descripcion(input.descripcion())
                .idCohorte(input.idCohorte())
                .idUbicacion(input.idUbicacion())
                .build();
    }

    @Override
    protected PruebaDTO toDtoFind(PRUEBA_FIND input) {
        return PruebaDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    public PruebaOutput toOutput(PruebaDTO dto) {
        if (dto == null) return null;
        return PruebaOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .idCohorte(dto.getIdCohorte())
                .idUbicacion(dto.getIdUbicacion())
                .cohorte(dto.getCohorte() != null ? cohorteMap.toOutput(dto.getCohorte()) : null)
                .ubicacion(dto.getUbicacion() != null ? ubicacionMap.toOutput(dto.getUbicacion()) : null)
                .build();
    }

    public List<PruebaOutput> toOutputList(List<PruebaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
