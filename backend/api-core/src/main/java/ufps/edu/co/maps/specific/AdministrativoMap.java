package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.AdministrativoInput.*;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.CargoOutput;
import ufps.edu.co.records.output.entity.EstadoOutput;
import ufps.edu.co.records.output.entity.GeneroOutput;
import ufps.edu.co.records.output.entity.PersonaOutput;
import ufps.edu.co.records.output.entity.UbicacionOutput;
import ufps.edu.co.rest.dto.AdministrativoDTO;

@Component
public class AdministrativoMap extends
        GlobalMapper<ADMINISTRATIVO_CREATE, ADMINISTRATIVO_UPDATE, ADMINISTRATIVO_DELETE, ADMINISTRATIVO_PATCH, ADMINISTRATIVO_FIND, AdministrativoOutput, AdministrativoDTO> {

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
        UbicacionOutput ubicacion = null;
        GeneroOutput genero = null;
        if (dto.getPersona() != null) {
            if (dto.getPersona().getUbicacion() != null) {
                UbicacionMap ubicacionMap = new UbicacionMap();
                ubicacion = ubicacionMap.toOutput(dto.getPersona().getUbicacion());
            }
            if (dto.getPersona().getGenero() != null) {
                GeneroMap generoMap = new GeneroMap();
                genero = generoMap.toOutput(dto.getPersona().getGenero());
            }
            persona = new PersonaOutput(dto.getPersona().getId(),
                    dto.getPersona().getNombres(),
                    dto.getPersona().getApellidos(),
                    dto.getPersona().getCorreo(),
                    dto.getPersona().getFechanacimiento(),
                    dto.getPersona().getCelular(),
                    dto.getPersona().getTelefono(),
                    ubicacion,
                    genero);
        }
        EstadoOutput estado = null;
        if (dto.getEstado() != null) {
            estado = new EstadoOutput(dto.getEstado().getId(), dto.getEstado().getTipo());
        }
        CargoOutput cargo = null;
        if (dto.getCargo() != null) {
            cargo = new CargoOutput(dto.getCargo().getId(), dto.getCargo().getNombre(),
                    dto.getCargo().getDescripcion());
        }
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
}
