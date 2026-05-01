package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.UbicacionInput.*;
import ufps.edu.co.records.output.UbicacionOutput;
import ufps.edu.co.rest.dto.UbicacionDTO;

@Component
public class UbicacionMap extends
        GlobalMapper<UBICACION_CREATE, UBICACION_UPDATE, UBICACION_DELETE, UBICACION_PATCH, UBICACION_FIND, UbicacionOutput, UbicacionDTO> {

    public UbicacionMap() {
        super(UBICACION_CREATE.class, UBICACION_UPDATE.class, UBICACION_DELETE.class, UBICACION_PATCH.class,
                UBICACION_FIND.class);
    }

    @Override
    protected UbicacionDTO toDtoCreate(UBICACION_CREATE input) {
        UbicacionDTO dto = new UbicacionDTO();
        dto.setDireccion(input.direccion());
        dto.setIdMunicipio(input.idMunicipio());
        return dto;
    }

    @Override
    protected UbicacionDTO toDtoUpdate(UBICACION_UPDATE input) {
        UbicacionDTO dto = new UbicacionDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected UbicacionDTO toDtoDelete(UBICACION_DELETE input) {
        UbicacionDTO dto = new UbicacionDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected UbicacionDTO toDtoPatch(UBICACION_PATCH input) {
        UbicacionDTO dto = new UbicacionDTO();
        dto.setId(input.id());
        dto.setDireccion(input.direccion());
        dto.setIdMunicipio(input.idMunicipio());
        return dto;
    }

    @Override
    protected UbicacionDTO toDtoFind(UBICACION_FIND input) {
        UbicacionDTO dto = new UbicacionDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public UbicacionOutput toOutput(UbicacionDTO dto) {
        if (dto != null) {
            return UbicacionOutput.builder()
                    .id(dto.getId())
                    .direccion(dto.getDireccion())
                    .build();
        }
        return null;
    }

    public UbicacionDTO toDtoWithMunicipio (UbicacionOutput output) {
        if (output != null) {
            UbicacionDTO dto = new UbicacionDTO();
            dto.setId(output.id());
            dto.setDireccion(output.direccion());
            return dto;
        }
        return null;
    }

}
