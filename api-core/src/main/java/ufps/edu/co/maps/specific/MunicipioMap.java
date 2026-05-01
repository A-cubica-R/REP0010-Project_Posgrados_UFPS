package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.MunicipioInput.*;
import ufps.edu.co.records.output.entity.MunicipioOutput;
import ufps.edu.co.rest.dto.MunicipioDTO;

@Component
public class MunicipioMap extends
        GlobalMapper<MUNICIPIO_CREATE, MUNICIPIO_UPDATE, MUNICIPIO_DELETE, MUNICIPIO_PATCH, MUNICIPIO_FIND, MunicipioOutput, MunicipioDTO> {

    public MunicipioMap() {
        super(MUNICIPIO_CREATE.class, MUNICIPIO_UPDATE.class,
                MUNICIPIO_DELETE.class, MUNICIPIO_PATCH.class, MUNICIPIO_FIND.class);
    }

    @Override
    protected MunicipioDTO toDtoCreate(MUNICIPIO_CREATE input) {
        MunicipioDTO dto = new MunicipioDTO();
        dto.setNombre(input.nombre());
        dto.setIdDepartamento(input.idDepartamento());
        return dto;
    }

    @Override
    protected MunicipioDTO toDtoUpdate(MUNICIPIO_UPDATE input) {
        MunicipioDTO dto = new MunicipioDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        dto.setIdDepartamento(input.idDepartamento());
        return dto;
    }

    @Override
    protected MunicipioDTO toDtoDelete(MUNICIPIO_DELETE input) {
        MunicipioDTO dto = new MunicipioDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected MunicipioDTO toDtoPatch(MUNICIPIO_PATCH input) {
        MunicipioDTO dto = new MunicipioDTO();
        dto.setId(input.id());
        if (input.nombre() != null) {
            dto.setNombre(input.nombre());
        }
        if (input.idDepartamento() != null) {
            dto.setIdDepartamento(input.idDepartamento());
        }
        return dto;
    }

    @Override
    protected MunicipioDTO toDtoFind(MUNICIPIO_FIND input) {
        MunicipioDTO dto = new MunicipioDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public MunicipioOutput toOutput(MunicipioDTO dto) {
        if (dto != null) {
            return MunicipioOutput.builder()
                    .id(dto.getId())
                    .nombre(dto.getNombre())
                    .build();
        }
        return null;
    }
}
