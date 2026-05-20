package ufps.edu.co.maps.specific;

import java.util.List;

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
        dto.setIdUbicacionvivienda(input.idUbicacionvivienda());
        dto.setIdUbicacionnacimiento(input.idUbicacionnacimiento());
        dto.setIdUbicaciontrabajo(input.idUbicaciontrabajo());
        dto.setIdGenero(input.idGenero());
        dto.setEgresadoufps(input.egresadoufps());
        dto.setEmpresa(input.empresa());
        dto.setExperiencialaboral(input.experiencialaboral());
        dto.setPromediopregrado(input.promediopregrado());
        dto.setTitulopregrado(input.titulopregrado());
        dto.setTitulosposgrados(input.titulosposgrados());
        dto.setIdCapacidadexepcional(input.idCapacidadexepcional());
        dto.setIdDiscapacidad(input.idDiscapacidad());
        dto.setIdEstadocivil(input.idEstadocivil());
        dto.setIdGrupoetnico(input.idGrupoetnico());
        dto.setIdPoblacionindigena(input.idPoblacionindigena());
        dto.setIdDocumentopersona(input.idDocumentopersona());
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
        dto.setIdUbicacionvivienda(input.idUbicacionvivienda());
        dto.setIdUbicacionnacimiento(input.idUbicacionnacimiento());
        dto.setIdUbicaciontrabajo(input.idUbicaciontrabajo());
        dto.setIdGenero(input.idGenero());
        dto.setEgresadoufps(input.egresadoufps());
        dto.setEmpresa(input.empresa());
        dto.setExperiencialaboral(input.experiencialaboral());
        dto.setPromediopregrado(input.promediopregrado());
        dto.setTitulopregrado(input.titulopregrado());
        dto.setTitulosposgrados(input.titulosposgrados());
        dto.setIdCapacidadexepcional(input.idCapacidadexepcional());
        dto.setIdDiscapacidad(input.idDiscapacidad());
        dto.setIdEstadocivil(input.idEstadocivil());
        dto.setIdGrupoetnico(input.idGrupoetnico());
        dto.setIdPoblacionindigena(input.idPoblacionindigena());
        dto.setIdDocumentopersona(input.idDocumentopersona());
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

        if (input.nombres() != null)
            builder.nombres(input.nombres());
        if (input.apellidos() != null)
            builder.apellidos(input.apellidos());
        if (input.correo() != null)
            builder.correo(input.correo());
        if (input.fechanacimiento() != null)
            builder.fechanacimiento(input.fechanacimiento());
        if (input.celular() != null)
            builder.celular(input.celular());
        if (input.telefono() != null)
            builder.telefono(input.telefono());
        // Mapear ubicaciones si vienen en el patch
        if (input.idUbicacionvivienda() != null)
            builder.idUbicacionvivienda(input.idUbicacionvivienda());
        if (input.idUbicacionnacimiento() != null)
            builder.idUbicacionnacimiento(input.idUbicacionnacimiento());
        if (input.idUbicaciontrabajo() != null)
            builder.idUbicaciontrabajo(input.idUbicaciontrabajo());
        if (input.idGenero() != null)
            builder.idGenero(input.idGenero());
        if (input.egresadoufps() != null)
            builder.egresadoufps(input.egresadoufps());
        if (input.empresa() != null)
            builder.empresa(input.empresa());
        if (input.experiencialaboral() != null)
            builder.experiencialaboral(input.experiencialaboral());
        if (input.promediopregrado() != null)
            builder.promediopregrado(input.promediopregrado());
        if (input.titulopregrado() != null)
            builder.titulopregrado(input.titulopregrado());
        if (input.titulosposgrados() != null)
            builder.titulosposgrados(input.titulosposgrados());
        if (input.idCapacidadexepcional() != null)
            builder.idCapacidadexepcional(input.idCapacidadexepcional());
        if (input.idDiscapacidad() != null)
            builder.idDiscapacidad(input.idDiscapacidad());
        if (input.idEstadocivil() != null)
            builder.idEstadocivil(input.idEstadocivil());
        if (input.idGrupoetnico() != null)
            builder.idGrupoetnico(input.idGrupoetnico());
        if (input.idPoblacionindigena() != null)
            builder.idPoblacionindigena(input.idPoblacionindigena());
        if (input.idDocumentopersona() != null)
            builder.idDocumentopersona(input.idDocumentopersona());

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
        if (dto == null)
            return null;

        GeneroMap generoMap = new GeneroMap();
        EstadocivilMap estadocivilMap = new EstadocivilMap();
        GrupoetnicoMap grupoetnicoMap = new GrupoetnicoMap();
        PoblacionindigenaMap poblacionindigenaMap = new PoblacionindigenaMap();
        DiscapacidadMap discapacidadMap = new DiscapacidadMap();
        CapacidadexepcionalMap capacidadexepcionalMap = new CapacidadexepcionalMap();
        UbicacionMap ubicacionMap = new UbicacionMap();

        return PersonaOutput.builder()
                .id(dto.getId())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .correo(dto.getCorreo())
                .fechanacimiento(dto.getFechanacimiento())
                .celular(dto.getCelular())
                .telefono(dto.getTelefono())
                .egresadoufps(dto.getEgresadoufps())
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
                .idDocumentopersona(dto.getIdDocumentopersona())
                .idUbicacionvivienda(dto.getIdUbicacionvivienda())
                .idUbicacionnacimiento(dto.getIdUbicacionnacimiento())
                .idUbicaciontrabajo(dto.getIdUbicaciontrabajo())
                .genero(dto.getGenero() != null ? generoMap.toOutput(dto.getGenero()) : null)
                .estadocivil(dto.getEstadocivil() != null ? estadocivilMap.toOutput(dto.getEstadocivil()) : null)
                .grupoetnico(dto.getGrupoetnico() != null ? grupoetnicoMap.toOutput(dto.getGrupoetnico()) : null)
                .poblacionindigena(
                        dto.getPoblacionindigena() != null ? poblacionindigenaMap.toOutput(dto.getPoblacionindigena())
                                : null)
                .discapacidad(dto.getDiscapacidad() != null ? discapacidadMap.toOutput(dto.getDiscapacidad()) : null)
                .capacidadexepcional(dto.getCapacidadexepcional() != null
                        ? capacidadexepcionalMap.toOutput(dto.getCapacidadexepcional())
                        : null)
                .ubicacionVivienda(
                        dto.getUbicacionVivienda() != null ? ubicacionMap.toOutput(dto.getUbicacionVivienda()) : null)
                .ubicacionNacimiento(
                        dto.getUbicacionNacimiento() != null ? ubicacionMap.toOutput(dto.getUbicacionNacimiento())
                                : null)
                .ubicacionTrabajo(
                        dto.getUbicacionTrabajo() != null ? ubicacionMap.toOutput(dto.getUbicacionTrabajo()) : null)
                .build();
    }

    public List<PersonaOutput> toOutputList(List<PersonaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
