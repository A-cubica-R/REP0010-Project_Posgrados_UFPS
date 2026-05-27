package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.GeneroInput.*;
import ufps.edu.co.records.output.entity.GeneroOutput;
import ufps.edu.co.rest.dto.GeneroDTO;

@Component
public class GeneroMap extends GlobalMapper<GENERO_CREATE, GENERO_UPDATE, GENERO_DELETE, GENERO_PATCH, GENERO_FIND, GeneroOutput, GeneroDTO> {

    public GeneroMap() {
        super(GENERO_CREATE.class, GENERO_UPDATE.class, GENERO_DELETE.class, GENERO_PATCH.class, GENERO_FIND.class);
    }

    @Override
    public GeneroDTO toDtoCreate(GENERO_CREATE create) {
        GeneroDTO dto = new GeneroDTO();
        dto.setNombre(create.nombre());
        return dto;
    }

    @Override
    public GeneroDTO toDtoUpdate(GENERO_UPDATE input) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        return dto;
    }

    @Override
    public GeneroDTO toDtoPatch(GENERO_PATCH input) {
        GeneroDTO patched = new GeneroDTO();
        patched.setId(input.id());
        patched.setNombre(input.nombre());

        if (input.nombre() != null) {
            patched.setNombre(input.nombre());
        }

        return patched;
    }

    public GeneroOutput toOutput(GeneroDTO dto) {
        if (dto == null) return null;
        return GeneroOutput.builder()
                .id(dto.getId())
                .genero(dto.getNombre())
                .build();
    }

    public List<GeneroOutput> toOutputList(List<GeneroDTO> dtoList) {
        return dtoList.stream()
                .map(this::toOutput)
                .toList();
    }

    @Override
    protected GeneroDTO toDtoDelete(GENERO_DELETE input) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected GeneroDTO toDtoFind(GENERO_FIND input) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(input.id());
        return dto;
    }
}