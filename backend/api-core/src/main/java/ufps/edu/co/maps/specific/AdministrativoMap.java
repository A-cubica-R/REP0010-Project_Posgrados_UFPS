package ufps.edu.co.maps.specific;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_CREATE;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_DELETE;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_FIND;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_PATCH;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_UPDATE;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.CargoOutput;
import ufps.edu.co.records.output.entity.EstadoOutput;
import ufps.edu.co.records.output.entity.PersonaOutput;
import ufps.edu.co.rest.dto.AdministrativoDTO;

@Component
public class AdministrativoMap extends
        GlobalMapper<ADMINISTRATIVO_CREATE, ADMINISTRATIVO_UPDATE, ADMINISTRATIVO_DELETE, ADMINISTRATIVO_PATCH, ADMINISTRATIVO_FIND, AdministrativoOutput, AdministrativoDTO> {

    @Autowired
    private PersonaMap personaMap;

    public AdministrativoMap() {
        super(ADMINISTRATIVO_CREATE.class, ADMINISTRATIVO_UPDATE.class, ADMINISTRATIVO_DELETE.class,
                ADMINISTRATIVO_PATCH.class, ADMINISTRATIVO_FIND.class);
    }

    @Override
    protected AdministrativoDTO toDtoCreate(ADMINISTRATIVO_CREATE input) {
        AdministrativoDTO dto = new AdministrativoDTO();
        dto.setIdPersona(input.idPersona());
        dto.setFechainicio(input.fechainicio());
        dto.setFechasalida(input.fechasalida());
        dto.setIdEstado(input.idEstado());
        dto.setIdCargo(input.idCargo());
        return dto;
    }

    @Override
    protected AdministrativoDTO toDtoUpdate(ADMINISTRATIVO_UPDATE input) {
        AdministrativoDTO dto = new AdministrativoDTO();
        dto.setId(input.id());
        dto.setIdPersona(input.idPersona());
        dto.setFechainicio(input.fechainicio());
        dto.setFechasalida(input.fechasalida());
        dto.setIdEstado(input.idEstado());
        dto.setIdCargo(input.idCargo());
        return dto;
    }

    @Override
    protected AdministrativoDTO toDtoDelete(ADMINISTRATIVO_DELETE input) {
        AdministrativoDTO dto = new AdministrativoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected AdministrativoDTO toDtoPatch(ADMINISTRATIVO_PATCH input) {
        AdministrativoDTO dto = new AdministrativoDTO();
        dto.setId(input.id());
        if (input.idPersona() != null)
            dto.setIdPersona(input.idPersona());
        if (input.fechainicio() != null)
            dto.setFechainicio(input.fechainicio());
        if (input.fechasalida() != null)
            dto.setFechasalida(input.fechasalida());
        if (input.idEstado() != null)
            dto.setIdEstado(input.idEstado());
        if (input.idCargo() != null)
            dto.setIdCargo(input.idCargo());
        return dto;
    }

    @Override
    protected AdministrativoDTO toDtoFind(ADMINISTRATIVO_FIND input) {
        AdministrativoDTO dto = new AdministrativoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public AdministrativoOutput toOutput(AdministrativoDTO dto) {
        if (dto == null)
            return null;
        PersonaOutput persona = null;
        if (dto.getPersona() != null) {
            persona = personaMap.toOutput(dto.getPersona());
        }
        EstadoOutput estado = EstadoOutput.builder()
                .id(
                        dto.getEstado() != null ? (dto.getEstado().getId()) : null)
                .entidad(
                        dto.getEstado() != null ? (dto.getEstado().getEntidad()) : null)
                .tipo(
                        dto.getEstado() != null ? (dto.getEstado().getTipo()) : null)
                .build();
                
        CargoOutput cargo = CargoOutput.builder()
                .id(
                        dto.getCargo() != null ? (dto.getCargo().getId()) : null)
                .nombre(
                        dto.getCargo() != null ? (dto.getCargo().getNombre()) : null)
                .descripcion(
                        dto.getCargo() != null ? (dto.getCargo().getDescripcion()) : null)
                .build();

        return AdministrativoOutput.builder()
                .id(dto.getId())
                .fechainicio(dto.getFechainicio())
                .fechasalida(dto.getFechasalida())
                .persona(persona)
                .estado(estado)
                .cargo(cargo)
                .build();
    }

    public java.util.List<AdministrativoOutput> toOutputList(java.util.List<AdministrativoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }

    public java.util.List<AdministrativoDTO> toDtoList(java.util.List<ADMINISTRATIVO_FIND> inputList) {
        return inputList.stream().map(this::toDtoFind).toList();
    }
}
