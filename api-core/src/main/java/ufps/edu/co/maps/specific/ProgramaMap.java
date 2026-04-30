package ufps.edu.co.maps.specific;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.ProgramaInput.*;
import ufps.edu.co.records.output.ProgramaOutput;
import ufps.edu.co.rest.dto.ProgramaDTO;
import ufps.edu.co.records.output.SedeOutput;
import ufps.edu.co.records.output.UbicacionOutput;
import ufps.edu.co.records.output.AdministrativoOutput;
import ufps.edu.co.records.output.FacultadOutput;
import ufps.edu.co.records.output.CargoOutput;
import ufps.edu.co.records.output.OfertaacademicaOutput;

@Component
public class ProgramaMap extends
        GlobalMapper<PROGRAMA_CREATE, PROGRAMA_UPDATE, PROGRAMA_DELETE, PROGRAMA_PATCH, PROGRAMA_FIND, ProgramaOutput, ProgramaDTO> {

    public ProgramaMap() {
        super(PROGRAMA_CREATE.class, PROGRAMA_UPDATE.class, PROGRAMA_DELETE.class, PROGRAMA_PATCH.class,
                PROGRAMA_FIND.class);
    }

    @Override
    protected ProgramaDTO toDtoCreate(PROGRAMA_CREATE input) {
        ProgramaDTO dto = new ProgramaDTO();
        dto.setCodigo(input.codigo());
        dto.setNombre(input.nombre());
        dto.setSemestres(input.semestres());
        dto.setCorreo(input.correo());
        dto.setRegistrosnies(input.registrosnies());
        dto.setNivelformacion(input.nivelformacion());
        dto.setTitulo(input.titulo());
        dto.setRcmineducacion(input.rcmineducacion());
        dto.setCreditos(input.creditos());
        dto.setPeriodicidad(input.periodicidad());
        dto.setValormatricula(input.valormatricula());
        dto.setIdSede(input.idSede());
        dto.setIdAdministrativo(input.idAdministrativo());
        dto.setIdFacultad(input.idFacultad());
        dto.setIdOtros(input.idOtros());
        return dto;
    }

    @Override
    protected ProgramaDTO toDtoUpdate(PROGRAMA_UPDATE input) {
        ProgramaDTO dto = new ProgramaDTO();
        dto.setId(input.id());
        dto.setCodigo(input.codigo());
        dto.setNombre(input.nombre());
        dto.setSemestres(input.semestres());
        dto.setCorreo(input.correo());
        dto.setRegistrosnies(input.registrosnies());
        dto.setNivelformacion(input.nivelformacion());
        dto.setTitulo(input.titulo());
        dto.setRcmineducacion(input.rcmineducacion());
        dto.setCreditos(input.creditos());
        dto.setPeriodicidad(input.periodicidad());
        dto.setValormatricula(input.valormatricula());
        dto.setIdSede(input.idSede());
        dto.setIdAdministrativo(input.idAdministrativo());
        dto.setIdFacultad(input.idFacultad());
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
        if (input.semestres() != null)
            dto.setSemestres(input.semestres());
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
        if (input.idAdministrativo() != null)
            dto.setIdAdministrativo(input.idAdministrativo());
        if (input.idFacultad() != null)
            dto.setIdFacultad(input.idFacultad());
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

        UbicacionOutput ubicacion = null;
        if (dto.getSede().getUbicacion() != null) {
            ubicacion = UbicacionOutput.builder()
                    .id(dto.getSede().getUbicacion().getId())
                    .direccion(dto.getSede().getUbicacion().getDireccion())
                    .build();
        }

        SedeOutput sede = null;
        if (dto.getSede() != null) {
            sede = SedeOutput.builder()
                    .id(dto.getSede().getId())
                    .nombre(dto.getSede().getNombre())
                    .ubicacion(ubicacion)
                    .build();
        }

        AdministrativoOutput administrativo = null;
        if (dto.getAdministrativo() != null) {
            administrativo = AdministrativoOutput.builder()
                    .id(dto.getAdministrativo().getId())
                    .idPersona(dto.getAdministrativo().getIdPersona())
                    .fechainicio(dto.getAdministrativo().getFechainicio())
                    .fechasalida(dto.getAdministrativo().getFechasalida())
                    .idEstado(dto.getAdministrativo().getIdEstado())
                    .idCargo(dto.getAdministrativo().getIdCargo())
                    .build();
        }

        FacultadOutput facultad = null;
        if (dto.getFacultad() != null) {
            facultad = FacultadOutput.builder()
                    .id(dto.getFacultad().getId())
                    .nombre(dto.getFacultad().getNombre())
                    .correo(dto.getFacultad().getCorreo())
                    .idAdministrativo(dto.getFacultad().getIdAdministrativo())
                    .build();
        }

        List<CargoOutput> cargoList = null;
        if (dto.getCargoList() != null) {
            cargoList = dto.getCargoList().stream().map(c -> CargoOutput.builder()
                    .id(c.getId())
                    .nombre(c.getNombre())
                    .descripcion(c.getDescripcion())
                    .idPrograma(c.getIdPrograma())
                    .build()).collect(Collectors.toList());
        }

        List<OfertaacademicaOutput> ofertaList = null;
        if (dto.getOfertaacademicaList() != null) {
            ofertaList = dto.getOfertaacademicaList().stream().map(o -> OfertaacademicaOutput.builder()
                    .id(o.getId())
                    .encuentros(o.getEncuentros())
                    .idPrograma(o.getIdPrograma())
                    .idModalidad(o.getIdModalidad())
                    .idJornada(o.getIdJornada())
                    .build()).collect(Collectors.toList());
        }

        return ProgramaOutput.builder()
                .id(dto.getId())
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre())
                .semestres(dto.getSemestres())
                .correo(dto.getCorreo())
                .registrosnies(dto.getRegistrosnies())
                .nivelformacion(dto.getNivelformacion())
                .titulo(dto.getTitulo())
                .rcmineducacion(dto.getRcmineducacion())
                .creditos(dto.getCreditos())
                .periodicidad(dto.getPeriodicidad())
                .valormatricula(dto.getValormatricula())
                .idSede(dto.getIdSede())
                .idAdministrativo(dto.getIdAdministrativo())
                .idFacultad(dto.getIdFacultad())
                .idOtros(dto.getIdOtros())
                .sede(sede)
                .administrativo(administrativo)
                .facultad(facultad)
                .cargoList(cargoList)
                .ofertaacademicaList(ofertaList)
                .build();
    }

    public List<ProgramaOutput> toOutputList(List<ProgramaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
