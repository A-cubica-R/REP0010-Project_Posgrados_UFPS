package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.EntrevistaInput.*;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.records.output.entity.EstadoOutput;
import ufps.edu.co.records.output.entity.TipoentrevistaOutput;
import ufps.edu.co.records.output.entity.UbicacionOutput;
import ufps.edu.co.rest.dto.EntrevistaDTO;

@Component
public class EntrevistaMap extends
        GlobalMapper<ENTREVISTA_CREATE, ENTREVISTA_UPDATE, ENTREVISTA_DELETE, ENTREVISTA_PATCH, ENTREVISTA_FIND, EntrevistaOutput, EntrevistaDTO> {


    public EntrevistaMap() {
        super(ENTREVISTA_CREATE.class, ENTREVISTA_UPDATE.class, ENTREVISTA_DELETE.class, ENTREVISTA_PATCH.class,
                ENTREVISTA_FIND.class);
    }

    @Override
    protected EntrevistaDTO toDtoCreate(ENTREVISTA_CREATE input) {
        return EntrevistaDTO.builder()
                .fecha(input.fecha())
                .tiempo(input.tiempo())
                .idAspirante(input.idAspirante())
                .motivocambio(input.motivocambio())
                .build();
    }

    @Override
    protected EntrevistaDTO toDtoUpdate(ENTREVISTA_UPDATE input) {
        return EntrevistaDTO.builder()
                .id(input.id())
                .fecha(input.fecha())
                .tiempo(input.tiempo())
                .idAspirante(input.idAspirante())
                .idEstado(input.idEstado())
                .idTipoentrevista(input.idTipoentrevista())
                .motivocambio(input.motivocambio())
                .build();
    }

    @Override
    protected EntrevistaDTO toDtoDelete(ENTREVISTA_DELETE input) {
        return EntrevistaDTO.builder()
                .id(input.id())
                .build();
    }

    @Override
    protected EntrevistaDTO toDtoPatch(ENTREVISTA_PATCH input) {
        throw new UnsupportedOperationException("Función PATCH no implementada para Entrevista");
    }

    @Override
    protected EntrevistaDTO toDtoFind(ENTREVISTA_FIND input) {
        EntrevistaDTO dto = new EntrevistaDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public EntrevistaOutput toOutput(EntrevistaDTO dto) {
        if (dto == null)
            return null;

        EstadoOutput estadoOutput = dto.getEstado() != null ? EstadoOutput.builder()
                .id(dto.getEstado().getId())
                .tipo(dto.getEstado().getTipo())
                .entidad(dto.getEstado().getEntidad())
                .build() : null;

        TipoentrevistaOutput tipoentrevistaOutput = dto.getTipoentrevista() != null ? TipoentrevistaOutput.builder()
                .id(dto.getTipoentrevista().getId())
                .tipo(dto.getTipoentrevista().getTipo())
                .descripcion(dto.getTipoentrevista().getDescripcion())
                .build() : null;

        UbicacionOutput ubicacionOutput = dto.getUbicacion() != null ? UbicacionOutput.builder()
                .id(dto.getUbicacion().getId())
                .direccion(dto.getUbicacion().getDireccion())
                .build() : null;

        return EntrevistaOutput.builder()
                .id(dto.getId())
                .fecha(dto.getFecha())
                .tiempo(dto.getTiempo())
                .motivocambio(dto.getMotivocambio())
                .idAspirante(dto.getIdAspirante())
                .idEstado(dto.getIdEstado())
                .idTipoentrevista(dto.getIdTipoentrevista())
                .idUbicacion(dto.getIdUbicacion())
                .estado(estadoOutput)
                .tipoentrevista(tipoentrevistaOutput)
                .ubicacion(ubicacionOutput)
                .build();
    }

    public List<EntrevistaOutput> toOutputList(List<EntrevistaDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}