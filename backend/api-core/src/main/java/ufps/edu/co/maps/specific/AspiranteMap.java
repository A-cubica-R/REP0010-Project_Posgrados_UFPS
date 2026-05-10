package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_CREATE;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_DELETE;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_PATCH;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_UPDATE;
import ufps.edu.co.records.output.entity.AspiranteOutput;
import ufps.edu.co.records.output.entity.GeneroOutput;
import ufps.edu.co.records.output.entity.PersonaOutput;
import ufps.edu.co.records.output.entity.UbicacionOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;

@Component
public class AspiranteMap extends
        GlobalMapper<ASPIRANTE_CREATE, ASPIRANTE_UPDATE, ASPIRANTE_DELETE, ASPIRANTE_PATCH, ASPIRANTE_FIND, AspiranteOutput, AspiranteDTO> {

    public AspiranteMap() {
        super(ASPIRANTE_CREATE.class, ASPIRANTE_UPDATE.class, ASPIRANTE_DELETE.class, ASPIRANTE_PATCH.class,
                ASPIRANTE_FIND.class);
    }

    @Override
    protected AspiranteDTO toDtoCreate(ASPIRANTE_CREATE input) {
        AspiranteDTO dto = new AspiranteDTO();
        dto.setIdPersona(input.idPersona());
        return dto;
    }

    @Override
    protected AspiranteDTO toDtoUpdate(ASPIRANTE_UPDATE input) {
        AspiranteDTO dto = new AspiranteDTO();
        dto.setId(input.id());
        dto.setIdPersona(input.idPersona());
        return dto;
    }

    @Override
    protected AspiranteDTO toDtoDelete(ASPIRANTE_DELETE input) {
        AspiranteDTO dto = new AspiranteDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected AspiranteDTO toDtoPatch(ASPIRANTE_PATCH input) {
        AspiranteDTO.AspiranteDTOBuilder builder = AspiranteDTO.builder()
                .id(input.id());

        if (input.idPersona() != null) {
            builder.idPersona(input.idPersona());
        }

        return builder.build();
    }

    @Override
    protected AspiranteDTO toDtoFind(ASPIRANTE_FIND input) {
        AspiranteDTO dto = new AspiranteDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public AspiranteOutput toOutput(AspiranteDTO dto) {
        if (dto == null) {
            return null;
        }
        PersonaOutput persona = null;
        if (dto.getPersona() != null) {
            UbicacionOutput ubicacion = null;
            if (dto.getPersona().getUbicacion() != null) {
                ubicacion = UbicacionOutput.builder()
                        .id(dto.getPersona().getUbicacion().getId())
                        .direccion(dto.getPersona().getUbicacion().getDireccion())
                        .build();
            }

            GeneroOutput genero = null;
            if (dto.getPersona().getGenero() != null) {
                genero = GeneroOutput.builder()
                        .id(dto.getPersona().getGenero().getId())
                        .nombre(dto.getPersona().getGenero().getNombre())
                        .build();
            }

            persona = PersonaOutput.builder()
                    .id(dto.getPersona().getId())
                    .nombres(dto.getPersona().getNombres())
                    .apellidos(dto.getPersona().getApellidos())
                    .correo(dto.getPersona().getCorreo())
                    .fechanacimiento(dto.getPersona().getFechanacimiento())
                    .celular(dto.getPersona().getCelular())
                    .telefono(dto.getPersona().getTelefono())
                    .ubicacion(ubicacion)
                    .genero(genero)
                    .build();
        }
        return AspiranteOutput.builder()
                .id(dto.getId())
                .persona(persona)
                .build();
    }

    public List<AspiranteOutput> toOutputList(List<AspiranteDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
