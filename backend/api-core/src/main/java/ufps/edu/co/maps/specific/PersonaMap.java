package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.PersonaInput.PERSONA_CREATE;
import ufps.edu.co.records.input.entity.PersonaInput.PERSONA_DELETE;
import ufps.edu.co.records.input.entity.PersonaInput.PERSONA_FIND;
import ufps.edu.co.records.input.entity.PersonaInput.PERSONA_PATCH;
import ufps.edu.co.records.input.entity.PersonaInput.PERSONA_UPDATE;
import ufps.edu.co.records.output.entity.PersonaOutput;
import ufps.edu.co.rest.dto.PersonaDTO;

@Component
public class PersonaMap extends
        GlobalMapper<PERSONA_CREATE, PERSONA_UPDATE, PERSONA_DELETE, PERSONA_PATCH, PERSONA_FIND, PersonaOutput, PersonaDTO> {

    @Autowired private UbicacionMap ubicacionMap;
    @Autowired private GeneroMap generoMap;

    public PersonaMap() {
        super(PERSONA_CREATE.class, PERSONA_UPDATE.class, PERSONA_DELETE.class, PERSONA_PATCH.class,
                PERSONA_FIND.class);
    }

    @Override
    protected PersonaDTO toDtoCreate(PERSONA_CREATE input) {
        PersonaDTO dto = new PersonaDTO();
        dto.setNombres(input.nombres());
        dto.setApellidos(input.apellidos());
        dto.setCorreo(input.correo());
        dto.setFechanacimiento(input.fechanacimiento());
        dto.setCelular(input.celular());
        dto.setTelefono(input.telefono());
        dto.setIdUbicacion(input.idUbicacion());
        dto.setIdGenero(input.idGenero());
        return dto;
    }

    @Override
    protected PersonaDTO toDtoUpdate(PERSONA_UPDATE input) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(input.id());
        dto.setNombres(input.nombres());
        dto.setApellidos(input.apellidos());
        dto.setCorreo(input.correo());
        dto.setFechanacimiento(input.fechanacimiento());
        dto.setCelular(input.celular());
        dto.setTelefono(input.telefono());
        dto.setIdUbicacion(input.idUbicacion());
        dto.setIdGenero(input.idGenero());
        return dto;
    }

    @Override
    protected PersonaDTO toDtoDelete(PERSONA_DELETE input) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected PersonaDTO toDtoPatch(PERSONA_PATCH input) {
        PersonaDTO.PersonaDTOBuilder builder = PersonaDTO.builder()
                .id(input.id());

        if (input.nombres() != null) builder.nombres(input.nombres());
        if (input.apellidos() != null) builder.apellidos(input.apellidos());
        if (input.correo() != null) builder.correo(input.correo());
        if (input.fechanacimiento() != null) builder.fechanacimiento(input.fechanacimiento());
        if (input.celular() != null) builder.celular(input.celular());
        if (input.telefono() != null) builder.telefono(input.telefono());
        if (input.idUbicacion() != null) builder.idUbicacion(input.idUbicacion());
        if (input.idGenero() != null) builder.idGenero(input.idGenero());

        return builder.build();
    }

    @Override
    protected PersonaDTO toDtoFind(PERSONA_FIND input) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public PersonaOutput toOutput(PersonaDTO dto) {
        if (dto == null) return null;
        return PersonaOutput.builder()
                .id(dto.getId())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .correo(dto.getCorreo())
                .fechanacimiento(dto.getFechanacimiento())
                .celular(dto.getCelular())
                .telefono(dto.getTelefono())
                .idUbicacion(dto.getIdUbicacion())
                .idGenero(dto.getIdGenero())
                .ubicacion(dto.getUbicacion() != null ? ubicacionMap.toOutput(dto.getUbicacion()) : null)
                .genero(dto.getGenero() != null ? generoMap.toOutput(dto.getGenero()) : null)
                .build();
    }

    public List<PersonaOutput> toOutputList(List<PersonaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
