package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.ProgramaInput.*;
import ufps.edu.co.records.output.entity.FacultadOutput;
import ufps.edu.co.records.output.entity.OtrosvaloresOutput;
import ufps.edu.co.records.output.entity.ProgramaOutput;
import ufps.edu.co.records.output.entity.SedeOutput;
import ufps.edu.co.records.output.entity.TiporegistroOutput;
import ufps.edu.co.records.output.entity.UbicacionOutput;
import ufps.edu.co.rest.dto.ProgramaDTO;

@Component
public class ProgramaMap extends
        GlobalMapper<PROGRAMA_CREATE, PROGRAMA_UPDATE, PROGRAMA_DELETE, PROGRAMA_PATCH, PROGRAMA_FIND, ProgramaOutput, ProgramaDTO> {

    public ProgramaMap() {
        super(PROGRAMA_CREATE.class, PROGRAMA_UPDATE.class, PROGRAMA_DELETE.class, PROGRAMA_PATCH.class,
                PROGRAMA_FIND.class);
    }

    @Override
    protected ProgramaDTO toDtoCreate(PROGRAMA_CREATE input) {
        ProgramaDTO dto = ProgramaDTO.builder()
                .codigo(input.codigo())
                .nombre(input.nombre())
                .duracion(input.duracion())
                .correo(input.correo())
                .registrosnies(input.registrosnies())
                .nivelformacion(input.nivelformacion())
                .titulo(input.titulo())
                .rcmineducacion(input.rcmineducacion())
                .creditos(input.creditos())
                .periodicidad(input.periodicidad())
                .valormatricula(input.valormatricula())
                .idSede(input.idSede())
                .idFacultad(input.idFacultad())
                .idTiporegistro(input.idTiporegistro())
                .idModalidad(input.idModalidad())
                .idOtros(input.idOtros())
                .build();
        return dto;
    }

    @Override
    protected ProgramaDTO toDtoUpdate(PROGRAMA_UPDATE input) {
        ProgramaDTO dto = new ProgramaDTO();
        dto.setId(input.id());
        dto.setCodigo(input.codigo());
        dto.setNombre(input.nombre());
        dto.setDuracion(input.duracion());
        dto.setCorreo(input.correo());
        dto.setRegistrosnies(input.registrosnies());
        dto.setNivelformacion(input.nivelformacion());
        dto.setTitulo(input.titulo());
        dto.setRcmineducacion(input.rcmineducacion());
        dto.setCreditos(input.creditos());
        dto.setPeriodicidad(input.periodicidad());
        dto.setValormatricula(input.valormatricula());
        dto.setIdSede(input.idSede());
        dto.setIdFacultad(input.idFacultad());
        dto.setIdTiporegistro(input.idTiporegistro());
        dto.setIdModalidad(input.idModalidad());
        dto.setIdOtros(input.idOtros());
        return dto;
    }

    @Override
    protected ProgramaDTO toDtoDelete(PROGRAMA_DELETE input) {
        ProgramaDTO dto = new ProgramaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected ProgramaDTO toDtoPatch(PROGRAMA_PATCH input) {
        ProgramaDTO dto = new ProgramaDTO();
        dto.setId(input.id());
        if (input.codigo() != null)
            dto.setCodigo(input.codigo());
        if (input.nombre() != null)
            dto.setNombre(input.nombre());
        if (input.duracion() != null)
            dto.setDuracion(input.duracion());
        if (input.correo() != null)
            dto.setCorreo(input.correo());
        if (input.registrosnies() != null)
            dto.setRegistrosnies(input.registrosnies());
        if (input.nivelformacion() != null)
            dto.setNivelformacion(input.nivelformacion());
        if (input.titulo() != null)
            dto.setTitulo(input.titulo());
        if (input.rcmineducacion() != null)
            dto.setRcmineducacion(input.rcmineducacion());
        if (input.creditos() != null)
            dto.setCreditos(input.creditos());
        if (input.periodicidad() != null)
            dto.setPeriodicidad(input.periodicidad());
        if (input.valormatricula() != null)
            dto.setValormatricula(input.valormatricula());
        if (input.idSede() != null)
            dto.setIdSede(input.idSede());
        if (input.idFacultad() != null)
            dto.setIdFacultad(input.idFacultad());
        if (input.idTiporegistro() != null)
            dto.setIdTiporegistro(input.idTiporegistro());
        if (input.idModalidad() != null)
            dto.setIdModalidad(input.idModalidad());
        if (input.idOtros() != null)
            dto.setIdOtros(input.idOtros());
        return dto;
    }

    @Override
    protected ProgramaDTO toDtoFind(PROGRAMA_FIND input) {
        ProgramaDTO dto = new ProgramaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public ProgramaOutput toOutput(ProgramaDTO dto) {
        if (dto == null)
            return null;

        SedeOutput sedeOutput = dto.getSede() != null ? SedeOutput.builder()
                .id(dto.getSede().getId())
                .nombre(dto.getSede().getNombre())
                .idUbicacion(dto.getSede().getIdUbicacion())
                .ubicacion(dto.getSede().getUbicacion() != null ? UbicacionOutput.builder()
                        .id(dto.getSede().getIdUbicacion())
                        .direccion(dto.getSede().getUbicacion().getDireccion())
                        .build() : null)
                .build() : null;

        FacultadOutput facultadOutput = dto.getFacultad() != null ? FacultadOutput.builder()
                .id(dto.getFacultad().getId())
                .nombre(dto.getFacultad().getNombre())
                .correo(dto.getFacultad().getCorreo())
                .build() : null;

        OtrosvaloresOutput otrosOutput = dto.getOtrosvalores() != null ? OtrosvaloresOutput.builder()
                .id(dto.getOtrosvalores().getId())
                .carnet(dto.getOtrosvalores().getCarnet())
                .estampilla(dto.getOtrosvalores().getEstampilla())
                .seguro(dto.getOtrosvalores().getSeguro())
                .build() : null;

        TiporegistroOutput tiporegistroOutput = dto.getTiporegistro() != null ? TiporegistroOutput.builder()
                .id(dto.getTiporegistro().getId())
                .tipo(dto.getTiporegistro().getTipo())
                .build() : null;

        return ProgramaOutput.builder()
                .id(dto.getId())
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre())
                .duracion(dto.getDuracion())
                .correo(dto.getCorreo())
                .registrosnies(dto.getRegistrosnies())
                .nivelformacion(dto.getNivelformacion())
                .titulo(dto.getTitulo())
                .rcmineducacion(dto.getRcmineducacion())
                .creditos(dto.getCreditos())
                .periodicidad(dto.getPeriodicidad())
                .valormatricula(dto.getValormatricula())
                .idFacultad(dto.getIdFacultad())
                .idOtros(dto.getIdOtros())
                .idSede(dto.getIdSede())
                .idTiporegistro(dto.getIdTiporegistro())
                .idModalidad(dto.getIdModalidad())
                .sede(sedeOutput)
                .facultad(facultadOutput)
                .otrosvalores(otrosOutput)
                .tiporegistro(tiporegistroOutput)
                .build();
    }

    public List<ProgramaOutput> toOutputList(List<ProgramaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }

    public List<ProgramaDTO> toDtoList(List<PROGRAMA_FIND> inputList) {
        return inputList.stream().map(this::toDtoFind).toList();
    }
}
