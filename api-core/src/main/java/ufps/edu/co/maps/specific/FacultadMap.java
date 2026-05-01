package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.FacultadInput.*;
import ufps.edu.co.records.output.FacultadOutput;
import ufps.edu.co.rest.dto.FacultadDTO;

@Component
public class FacultadMap extends GlobalMapper<FACULTAD_CREATE, FACULTAD_UPDATE, FACULTAD_DELETE, FACULTAD_PATCH, FACULTAD_FIND, FacultadOutput, FacultadDTO> {

    public FacultadMap() {
        super(FACULTAD_CREATE.class, FACULTAD_UPDATE.class, FACULTAD_DELETE.class, FACULTAD_PATCH.class, FACULTAD_FIND.class);
    }

    @Override
    protected FacultadDTO toDtoCreate(FACULTAD_CREATE input) {
        FacultadDTO dto = new FacultadDTO();
        dto.setNombre(input.nombre());
        dto.setCorreo(input.correo());
        dto.setIdAdministrativo(input.idAdministrativo());
        return dto;
    }

    @Override
    protected FacultadDTO toDtoUpdate(FACULTAD_UPDATE input) {
        FacultadDTO dto = new FacultadDTO();
        dto.setId(input.id());
        dto.setNombre(input.nombre());
        dto.setCorreo(input.correo());
        dto.setIdAdministrativo(input.idAdministrativo());
        return dto;
    }

    @Override
    protected FacultadDTO toDtoDelete(FACULTAD_DELETE input) {
        FacultadDTO dto = new FacultadDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected FacultadDTO toDtoPatch(FACULTAD_PATCH input) {
        FacultadDTO dto = new FacultadDTO();
        dto.setId(input.id());
        if (input.nombre() != null) dto.setNombre(input.nombre());
        if (input.correo() != null) dto.setCorreo(input.correo());
        if (input.idAdministrativo() != null) dto.setIdAdministrativo(input.idAdministrativo());
        return dto;
    }

    @Override
    protected FacultadDTO toDtoFind(FACULTAD_FIND input) {
        FacultadDTO dto = new FacultadDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public FacultadOutput toOutput(FacultadDTO dto) {
        if (dto == null) return null;
        return FacultadOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .build();
    }

    public List<FacultadOutput> toOutputList(List<FacultadDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
