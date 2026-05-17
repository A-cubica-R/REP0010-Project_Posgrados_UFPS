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
    @Autowired private EstadocivilMap estadocivilMap;
    @Autowired private GrupoetnicoMap grupoetnicoMap;
    @Autowired private PoblacionindigenaMap poblacionindigenaMap;
    @Autowired private DiscapacidadMap discapacidadMap;
    @Autowired private CapacidadexepcionalMap capacidadexepcionalMap;

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
        dto.setIdUbicacionvivienda(input.idUbicacion());
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
        dto.setIdUbicacionvivienda(input.idUbicacion());
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
        // Corregir
        if (input.idUbicacion() != null) builder.idUbicacionnacimiento(input.idUbicacion());
        if (input.idUbicacion() != null) builder.idUbicaciontrabajo(input.idUbicacion());
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
                .egresadoufps(dto.isEgresadoufps())
                .empresa(dto.getEmpresa())
                .experiencialaboral(dto.getExperiencialaboral())
                .promediopregrado(dto.getPromediopregrado())
                .titulopregrado(dto.getTitulopregrado())
                .titulosposgrados(dto.getTitulosposgrados())
                .idGenero(dto.getIdGenero())
                .idEstadocivil(dto.getIdEstadocivil())
                .idGrupoetnico(dto.getIdGrupoetnico())
                .idPoblacionindigena(dto.getIdPoblacionindigena())
                .idDiscapacidad(dto.getIdDiscapacidad())
                .idCapacidadexepcional(dto.getIdCapacidadexepcional())
                .idUbicacionvivienda(dto.getIdUbicacionvivienda())
                .idUbicacionnacimiento(dto.getIdUbicacionnacimiento())
                .idUbicaciontrabajo(dto.getIdUbicaciontrabajo())
                .genero(dto.getGenero() != null ? generoMap.toOutput(dto.getGenero()) : null)
                .estadocivil(dto.getEstadocivil() != null ? estadocivilMap.toOutput(dto.getEstadocivil()) : null)
                .grupoetnico(dto.getGrupoetnico() != null ? grupoetnicoMap.toOutput(dto.getGrupoetnico()) : null)
                .poblacionindigena(dto.getPoblacionindigena() != null ? poblacionindigenaMap.toOutput(dto.getPoblacionindigena()) : null)
                .discapacidad(dto.getDiscapacidad() != null ? discapacidadMap.toOutput(dto.getDiscapacidad()) : null)
                .capacidadexepcional(dto.getCapacidadexepcional() != null ? capacidadexepcionalMap.toOutput(dto.getCapacidadexepcional()) : null)
                .ubicacionVivienda(dto.getUbicacion() != null ? ubicacionMap.toOutput(dto.getUbicacion()) : null)
                .ubicacionNacimiento(dto.getUbicacion2() != null ? ubicacionMap.toOutput(dto.getUbicacion2()) : null)
                .ubicacionTrabajo(dto.getUbicacion3() != null ? ubicacionMap.toOutput(dto.getUbicacion3()) : null)
                .build();
    }

    public List<PersonaOutput> toOutputList(List<PersonaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
