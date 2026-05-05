package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.processor.crud.EntrevistadorProcessor;
import ufps.edu.co.processor.crud.PersonaProcessor;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_CREATE;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_DELETE;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_PATCH;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_UPDATE;
import ufps.edu.co.records.input.entity.EntrevistadorInput.ENTREVISTADOR_FIND;
import ufps.edu.co.records.input.entity.PersonaInput.PERSONA_FIND;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.AspiranteOutput;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.records.output.entity.EntrevistadorOutput;
import ufps.edu.co.records.output.entity.EstadoOutput;
import ufps.edu.co.records.output.entity.PersonaOutput;
import ufps.edu.co.records.output.entity.TipoentrevistaOutput;
import ufps.edu.co.rest.dto.EntrevistaDTO;

@Component
public class EntrevistaMap extends
        GlobalMapper<ENTREVISTA_CREATE, ENTREVISTA_UPDATE, ENTREVISTA_DELETE, ENTREVISTA_PATCH, ENTREVISTA_FIND, EntrevistaOutput, EntrevistaDTO> {

    @Autowired
    private PersonaProcessor personaProcessor;

    @Autowired
    private EntrevistadorProcessor entrevistadorProcessor;

    public EntrevistaMap() {
        super(ENTREVISTA_CREATE.class, ENTREVISTA_UPDATE.class, ENTREVISTA_DELETE.class, ENTREVISTA_PATCH.class,
                ENTREVISTA_FIND.class);
    }

    @Override
    protected EntrevistaDTO toDtoCreate(ENTREVISTA_CREATE input) {
        EntrevistaDTO dto = new EntrevistaDTO();
        dto.setFecha(input.fecha());
        dto.setCalificacion(input.calificacion());
        dto.setIdTipoentrevista(input.idTipoentrevista());
        dto.setIdEntrevistador(input.idEntrevistador());
        dto.setIdAspirante(input.idAspirante());
        dto.setIdEstado(input.idEstado());
        return dto;
    }

    @Override
    protected EntrevistaDTO toDtoUpdate(ENTREVISTA_UPDATE input) {
        EntrevistaDTO dto = new EntrevistaDTO();
        dto.setId(input.id());
        dto.setFecha(input.fecha());
        dto.setCalificacion(input.calificacion());
        dto.setIdTipoentrevista(input.idTipoentrevista());
        dto.setIdEntrevistador(input.idEntrevistador());
        dto.setIdAspirante(input.idAspirante());
        dto.setIdEstado(input.idEstado());
        return dto;
    }

    @Override
    protected EntrevistaDTO toDtoDelete(ENTREVISTA_DELETE input) {
        EntrevistaDTO dto = new EntrevistaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected EntrevistaDTO toDtoPatch(ENTREVISTA_PATCH input) {
        EntrevistaDTO.EntrevistaDTOBuilder builder = EntrevistaDTO.builder()
                .id(input.id());

        if (input.fecha() != null) {
            builder.fecha(input.fecha());
        }
        if (input.calificacion() != null) {
            builder.calificacion(input.calificacion());
        }
        if (input.idTipoentrevista() != null) {
            builder.idTipoentrevista(input.idTipoentrevista());
        }
        if (input.idEntrevistador() != null) {
            builder.idEntrevistador(input.idEntrevistador());
        }
        if (input.idAspirante() != null) {
            builder.idAspirante(input.idAspirante());
        }
        if (input.idEstado() != null) {
            builder.idEstado(input.idEstado());
        }

        return builder.build();
    }

    @Override
    protected EntrevistaDTO toDtoFind(ENTREVISTA_FIND input) {
        EntrevistaDTO dto = new EntrevistaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public EntrevistaOutput toOutput(EntrevistaDTO dto) {
        if (dto == null) {
            return null;
        }

        TipoentrevistaOutput tipoentrevista = null;
        if (dto.getTipoentrevista() != null) {
            tipoentrevista = TipoentrevistaOutput.builder()
                    .id(dto.getTipoentrevista().getId())
                    .nombre(dto.getTipoentrevista().getNombre())
                    .descripcion(dto.getTipoentrevista().getDescripcion())
                    .build();
        }

        EntrevistadorOutput entrevistador = null;
        if (dto.getEntrevistador() != null) {
            entrevistador = entrevistadorProcessor.findById(
                    new ENTREVISTADOR_FIND(dto.getEntrevistador().getId()));
        }

        AspiranteOutput aspirante = null;
        if (dto.getAspirante() != null) {
            PersonaOutput persona = personaProcessor.findById(
                    new PERSONA_FIND(dto.getAspirante().getIdPersona()));
            aspirante = AspiranteOutput.builder()
                    .id(dto.getAspirante().getId())
                    .persona(persona) // ✅ poblado
                    .build();
        }

        EstadoOutput estado = null;
        if (dto.getEstado() != null) {
            estado = EstadoOutput.builder()
                    .id(dto.getEstado().getId())
                    .tipo(dto.getEstado().getTipo())
                    .build();
        }

        String nombreAspirante = aspirante != null && aspirante.persona() != null
                ? aspirante.persona().nombres() + " " + aspirante.persona().apellidos()
                : "Sin aspirante";

        List<AdministrativoOutput> entrevistadores = List.of();

        return new EntrevistaOutput(dto.getId(), dto.getFecha(), dto.getCalificacion(), tipoentrevista, entrevistador,
                aspirante, estado, nombreAspirante, entrevistadores);
    }

    public List<EntrevistaOutput> toOutputList(List<EntrevistaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
