package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.UsuarioInput.*;
import ufps.edu.co.records.output.entity.UsuarioOutput;
import ufps.edu.co.rest.dto.UsuarioDTO;

@Component
public class UsuarioMap extends
        GlobalMapper<USUARIO_CREATE, USUARIO_UPDATE, USUARIO_DELETE, USUARIO_PATCH, USUARIO_FIND, UsuarioOutput, UsuarioDTO> {

    public UsuarioMap() {
        super(USUARIO_CREATE.class, USUARIO_UPDATE.class, USUARIO_DELETE.class, USUARIO_PATCH.class,
                USUARIO_FIND.class);
    }

    @Override
    protected UsuarioDTO toDtoCreate(USUARIO_CREATE input) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombreusuario(input.nombreusuario());
        dto.setIdPersona(input.idPersona());
        dto.setIdRol(input.idRol());
        dto.setIdClave(input.idClave());
        return dto;
    }

    @Override
    protected UsuarioDTO toDtoUpdate(USUARIO_UPDATE input) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(input.id());
        dto.setNombreusuario(input.nombreusuario());
        dto.setIdPersona(input.idPersona());
        dto.setIdRol(input.idRol());
        dto.setIdClave(input.idClave());
        return dto;
    }

    @Override
    protected UsuarioDTO toDtoDelete(USUARIO_DELETE input) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected UsuarioDTO toDtoPatch(USUARIO_PATCH input) {
        UsuarioDTO.UsuarioDTOBuilder builder = UsuarioDTO.builder().id(input.id());
        if (input.nombreusuario() != null)
            builder.nombreusuario(input.nombreusuario());
        if (input.idPersona() != null)
            builder.idPersona(input.idPersona());
        if (input.idRol() != null)
            builder.idRol(input.idRol());
        if (input.idClave() != null)
            builder.idClave(input.idClave());
        return builder.build();
    }

    @Override
    protected UsuarioDTO toDtoFind(USUARIO_FIND input) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public UsuarioOutput toOutput(UsuarioDTO dto) {
        if (dto == null)
            return null;

        PersonaMap personaMap = new PersonaMap();
        RolMap rolMap = new RolMap();
        ClaveMap claveMap = new ClaveMap();

        return UsuarioOutput.builder()
                .id(dto.getId())
                .nombreusuario(dto.getNombreusuario())
                .idPersona(dto.getIdPersona())
                .idRol(dto.getIdRol())
                .idClave(dto.getIdClave())
                .persona(dto.getPersona() != null ? personaMap.toOutput(dto.getPersona()) : null)
                .rol(dto.getRol() != null ? rolMap.toOutput(dto.getRol()) : null)
                .clave(dto.getClave() != null ? claveMap.toOutput(dto.getClave()) : null)
                .build();
    }

    public List<UsuarioOutput> toOutputList(List<UsuarioDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
