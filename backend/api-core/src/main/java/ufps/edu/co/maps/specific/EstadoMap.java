package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.EstadoInput.*;
import ufps.edu.co.records.output.entity.EstadoOutput;
import ufps.edu.co.rest.dto.EstadoDTO;

@Component
public class EstadoMap extends
        GlobalMapper<ESTADO_CREATE, ESTADO_UPDATE, ESTADO_DELETE, ESTADO_PATCH, ESTADO_FIND, EstadoOutput, EstadoDTO> {

    public EstadoMap() {
        super(ESTADO_CREATE.class, ESTADO_UPDATE.class,
                ESTADO_DELETE.class, ESTADO_PATCH.class, ESTADO_FIND.class);
    }

    @Override
    public EstadoDTO toDtoCreate(ESTADO_CREATE input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setTipo(input.tipo());
        return dto;
    }

    @Override
    public EstadoDTO toDtoUpdate(ESTADO_UPDATE input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    @Override
    public EstadoDTO toDtoPatch(ESTADO_PATCH input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    @Override
    public EstadoDTO toDtoDelete(ESTADO_DELETE input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public EstadoDTO toDtoFind(ESTADO_FIND input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        return dto;
    }

    /*
     * EstadoMap es un leaf map: solo mapea sus campos propios (id, entidad, tipo).
     * Las listas inversas (cohorteList, entrevistaList, etc.) NO se mapean aquí
     * porque crean dependencias circulares con CohorteMap, EntrevistaMap, PagoMap.
     * Si se necesitan esas listas, se deben obtener via endpoints dedicados.
     */
    @Override
    public EstadoOutput toOutput(EstadoDTO dto) {
        if (dto == null) return null;
        return EstadoOutput.builder()
                .id(dto.getId())
                .entidad(dto.getEntidad())
                .tipo(dto.getTipo())
                .build();
    }

    public List<EstadoOutput> toOutputList(List<EstadoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}