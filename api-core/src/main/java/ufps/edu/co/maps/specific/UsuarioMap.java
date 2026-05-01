package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.UsuarioInput.*;
import ufps.edu.co.records.output.*;
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
        UsuarioDTO.UsuarioDTOBuilder builder = UsuarioDTO.builder()
                .id(input.id());

        if (input.nombreusuario() != null) {
            builder.nombreusuario(input.nombreusuario());
        }
        if (input.idPersona() != null) {
            builder.idPersona(input.idPersona());
        }
        if (input.idRol() != null) {
            builder.idRol(input.idRol());
        }
        if (input.idClave() != null) {
            builder.idClave(input.idClave());
        }

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
        if (dto == null) {
            return null;
        }

        PersonaOutput persona = null;
        if (dto.getPersona() != null) {
            persona = PersonaOutput.builder()
                    .id(dto.getPersona().getId())
                    .nombres(dto.getPersona().getNombres())
                    .apellidos(dto.getPersona().getApellidos())
                    .correo(dto.getPersona().getCorreo())
                    .build();
        }

        RolOutput rol = null;
        if (dto.getRol() != null) {
            rol = RolOutput.builder()
                    .id(dto.getRol().getId())
                    .nombre(dto.getRol().getNombre())
                    .build();
        }

        ClaveOutput clave = null;
        if (dto.getClave() != null) {
            clave = ClaveOutput.builder()
                    .id(dto.getClave().getId())
                    .build();
        }

        return new UsuarioOutput(dto.getId(), dto.getNombreusuario(), dto.getIdPersona(), dto.getIdRol(),
                dto.getIdClave(), persona, rol, clave);
    }

    public List<UsuarioOutput> toOutputList(List<UsuarioDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
