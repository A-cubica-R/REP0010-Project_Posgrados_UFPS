package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.GrupoetnicoInput.*;
import ufps.edu.co.records.output.entity.GrupoetnicoOutput;
import ufps.edu.co.rest.dto.GrupoetnicoDTO;

@Component
public class GrupoetnicoMap extends GlobalMapper<GRUPOETNICO_CREATE, GRUPOETNICO_UPDATE, GRUPOETNICO_DELETE, GRUPOETNICO_PATCH, GRUPOETNICO_FIND, GrupoetnicoOutput, GrupoetnicoDTO> {

    public GrupoetnicoMap() {
        super(GRUPOETNICO_CREATE.class, GRUPOETNICO_UPDATE.class, GRUPOETNICO_DELETE.class, GRUPOETNICO_PATCH.class, GRUPOETNICO_FIND.class);
    }

    @Override
    protected GrupoetnicoDTO toDtoCreate(GRUPOETNICO_CREATE input) {
        GrupoetnicoDTO dto = new GrupoetnicoDTO();
        dto.setGrupo(input.grupo());
        return dto;
    }

    @Override
    protected GrupoetnicoDTO toDtoUpdate(GRUPOETNICO_UPDATE input) {
        GrupoetnicoDTO dto = new GrupoetnicoDTO();
        dto.setId(input.id());
        dto.setGrupo(input.grupo());
        return dto;
    }

    @Override
    protected GrupoetnicoDTO toDtoPatch(GRUPOETNICO_PATCH input) {
        GrupoetnicoDTO dto = new GrupoetnicoDTO();
        dto.setId(input.id());
        if (input.grupo() != null) dto.setGrupo(input.grupo());
        return dto;
    }

    @Override
    protected GrupoetnicoDTO toDtoDelete(GRUPOETNICO_DELETE input) {
        GrupoetnicoDTO dto = new GrupoetnicoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected GrupoetnicoDTO toDtoFind(GRUPOETNICO_FIND input) {
        GrupoetnicoDTO dto = new GrupoetnicoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public GrupoetnicoOutput toOutput(GrupoetnicoDTO dto) {
        if (dto == null) return null;
        return GrupoetnicoOutput.builder()
                .id(dto.getId())
                .grupo(dto.getGrupo())
                .build();
    }

    public List<GrupoetnicoOutput> toOutputList(List<GrupoetnicoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
