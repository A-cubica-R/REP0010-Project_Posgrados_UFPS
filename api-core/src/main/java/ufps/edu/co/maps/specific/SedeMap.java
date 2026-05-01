package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.SedeInput.*;
import ufps.edu.co.records.output.entity.SedeOutput;
import ufps.edu.co.rest.dto.SedeDTO;

@Component
public class SedeMap extends GlobalMapper <SEDE_CREATE, SEDE_UPDATE, SEDE_DELETE, SEDE_PATCH, SEDE_FIND, SedeOutput, SedeDTO> {
    public SedeMap() {
        super(SEDE_CREATE.class, SEDE_UPDATE.class, SEDE_DELETE.class, SEDE_PATCH.class, SEDE_FIND.class);
    }

    @Override
    public SedeDTO toDtoCreate(SEDE_CREATE create) {
        SedeDTO dto = new SedeDTO();
        dto.setNombre(create.nombre());
        dto.setIdUbicacion(create.idUbicacion());
        return dto;
    }

    @Override
    public SedeDTO toDtoUpdate(SEDE_UPDATE input) {
        SedeDTO dto = new SedeDTO();
        dto.setNombre(input.nombre());
        dto.setIdUbicacion(input.idUbicacion());
        return dto;
    }

    @Override
    public SedeDTO toDtoPatch(SEDE_PATCH input) {
        SedeDTO dto = new SedeDTO();
        dto.setNombre(input.nombre());
        dto.setIdUbicacion(input.idUbicacion());
        return dto;
    }

    @Override
    protected SedeDTO toDtoDelete(SEDE_DELETE input) {
        SedeDTO dto = new SedeDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected SedeDTO toDtoFind(SEDE_FIND input) {
        SedeDTO dto = new SedeDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public SedeOutput toOutput(SedeDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toOutput'");
    }
    
}
