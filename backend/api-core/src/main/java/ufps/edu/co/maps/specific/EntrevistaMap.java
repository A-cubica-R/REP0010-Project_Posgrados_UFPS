package ufps.edu.co.maps.specific;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_CREATE;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_DELETE;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_PATCH;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_UPDATE;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.AspiranteOutput;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.records.output.entity.EntrevistadorOutput;
import ufps.edu.co.records.output.entity.UbicacionOutput;
import ufps.edu.co.rest.dto.EntrevistaDTO;

@Component
public class EntrevistaMap extends
        GlobalMapper<ENTREVISTA_CREATE, ENTREVISTA_UPDATE, ENTREVISTA_DELETE, ENTREVISTA_PATCH, ENTREVISTA_FIND, EntrevistaOutput, EntrevistaDTO> {

    @Autowired
    private TipoentrevistaMap tipoentrevistaMap;

    @Autowired
    private EntrevistadorMap entrevistadorMap;

    @Autowired
    private AspiranteMap aspiranteMap;

    @Autowired
    private EstadoMap estadoMap;

    @Autowired
    private UbicacionMap ubicacionMap;

    @Autowired
    private AdministrativoMap administrativoMap;

    public EntrevistaMap() {
        super(ENTREVISTA_CREATE.class, ENTREVISTA_UPDATE.class, ENTREVISTA_DELETE.class, ENTREVISTA_PATCH.class,
                ENTREVISTA_FIND.class);
    }

    @Override
    protected EntrevistaDTO toDtoCreate(ENTREVISTA_CREATE input) {
        EntrevistaDTO dto = new EntrevistaDTO();
        dto.setFecha(input.fecha());
        dto.setCalificacion(0.0f); // Valor predeterminado para calificación
        dto.setIdTipoentrevista(input.idTipoentrevista());
        dto.setIdEntrevistador(input.idEntrevistador());
        dto.setIdAspirante(input.idAspirante());
        dto.setIdEstado(input.idEstado());
        dto.setIdUbicacion(input.idUbicacion());
        dto.setTiempo(input.tiempo());
        return dto;
    }

    @Override
    protected EntrevistaDTO toDtoUpdate(ENTREVISTA_UPDATE input) {
        EntrevistaDTO dto = new EntrevistaDTO();
        dto.setId(input.id());
        dto.setFecha(input.fecha());
        dto.setTiempo(input.tiempo());
        dto.setCalificacion(input.calificacion());
        dto.setIdTipoentrevista(input.idTipoentrevista());
        dto.setIdEntrevistador(input.idEntrevistador());
        dto.setIdEstado(input.idEstado());
        dto.setIdUbicacion(input.idUbicacion());
        dto.setIdAspirante(input.idAspirante());
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

        if (input.fecha() != null)
            builder.fecha(input.fecha());
        if (input.calificacion() != null)
            builder.calificacion(input.calificacion());
        if (input.idTipoentrevista() != null)
            builder.idTipoentrevista(input.idTipoentrevista());
        if (input.idEntrevistador() != null)
            builder.idEntrevistador(input.idEntrevistador());
        if (input.tiempo() != null)
            builder.tiempo(input.tiempo());
        if (input.idEstado() != null)
            builder.idEstado(input.idEstado());
        if (input.idUbicacion() != null)
            builder.idUbicacion(input.idUbicacion());

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

        EntrevistadorOutput entrevistador = entrevistadorMap.toOutput(dto.getEntrevistador());
        AspiranteOutput aspirante = aspiranteMap.toOutput(dto.getAspirante());
        UbicacionOutput ubicacion = ubicacionMap.toOutput(dto.getUbicacion());

        String nombreAspirante = aspirante != null && aspirante.persona() != null
                ? aspirante.persona().nombres() + " " + aspirante.persona().apellidos()
                : "Sin aspirante";

        List<AdministrativoOutput> entrevistadores = dto.getEntrevistadores() != null
                ? dto.getEntrevistadores().stream()
                        .map(entre -> administrativoMap.toOutput(entre.getAdministrativo()))
                        .collect(Collectors.toList())
                : List.of();

        return EntrevistaOutput.builder()
                .id(dto.getId())
                .fecha(dto.getFecha())
                .calificacion(dto.getCalificacion())
                .tipoentrevista(tipoentrevistaMap.toOutput(dto.getTipoentrevista()))
                .entrevistador(entrevistador)
                .aspirante(aspirante)
                .estado(estadoMap.toOutput(dto.getEstado()))
                .nombreAspirante(nombreAspirante)
                .ubicacion(ubicacion)
                .entrevistadores(entrevistadores)
                .build();
    }

    public List<EntrevistaOutput> toOutputList(List<EntrevistaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}