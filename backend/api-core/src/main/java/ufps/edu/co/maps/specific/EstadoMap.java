package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.stereotype.Component;

import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.EstadoInput.*;
import ufps.edu.co.records.output.entity.EstadoOutput;
import ufps.edu.co.rest.dto.EstadoDTO;

@Component
public class EstadoMap extends
        GlobalMapper<ESTADO_CREATE, ESTADO_UPDATE, ESTADO_DELETE, ESTADO_PATCH, ESTADO_FIND, EstadoOutput, EstadoDTO> {

    public EstadoMap() {
        super(ESTADO_CREATE.class, ESTADO_UPDATE.class,
                ESTADO_DELETE.class, ESTADO_PATCH.class, ESTADO_FIND.class);
    }

    @Override
    public EstadoDTO toDtoCreate(ESTADO_CREATE input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setTipo(input.tipo());
        return dto;
    }

    @Override
    public EstadoDTO toDtoUpdate(ESTADO_UPDATE input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    @Override
    public EstadoDTO toDtoPatch(ESTADO_PATCH input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        dto.setTipo(input.tipo());
        return dto;
    }

    @Override
    public EstadoDTO toDtoDelete(ESTADO_DELETE input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public EstadoDTO toDtoFind(ESTADO_FIND input) {
        EstadoDTO dto = new EstadoDTO();
        dto.setId(input.id());
        return dto;
    }

    /**
     * private Integer id ;
     * //--- OTHER DATA FIELDS
     * private String entidad ;
     * private String tipo ;
     * //--- LINKS ( RELATIONSHIPS )
     * private List<AdministrativoDTO> administrativoList ;
     * private List<AspiranteDTO> aspiranteList ;
     * private List<CohorteDTO> cohorteList ;
     * private List<EntrevistaDTO> entrevistaList ;
     * private List<PagoDTO> pagoList ;
     * private List<SemestreDTO> semestreList ;
     */
    public EstadoOutput toOutput(EstadoDTO dto) {
        if (dto != null) {
            return EstadoOutput.builder()
                .id(dto.getId())
                .entidad(dto.getEntidad())
                .tipo(dto.getTipo())
                .administrativoList(
                    dto.getAdministrativoList() != null ? (
                        !dto.getAdministrativoList().isEmpty() ? (
                            dto.getAdministrativoList()
                                .stream().map(administrativoDTO -> {
                                    return new AdministrativoMap().toOutput(administrativoDTO);
                                }
                            ).toList()
                        ) : null
                    ) : null)
                .aspiranteList(
                    dto.getAspiranteList() != null ? (
                        !dto.getAspiranteList().isEmpty() ? (
                            dto.getAspiranteList()
                                .stream().map(aspiranteDTO -> {
                                    return new AspiranteMap().toOutput(aspiranteDTO);
                                }
                            ).toList()
                        ) : null
                    ) : null)
                .cohorteList(
                    dto.getCohorteList() != null ? (
                        !dto.getCohorteList().isEmpty() ? (
                            dto.getCohorteList()
                                .stream().map(cohorteDTO -> {
                                    return new CohorteMap().toOutput(cohorteDTO);
                                }
                            ).toList()
                        ) : null
                    ) : null)
                .entrevistaList(
                    dto.getEntrevistaList() != null ? (
                        !dto.getEntrevistaList().isEmpty() ? (
                            dto.getEntrevistaList()
                                .stream().map(entrevistaDTO -> {
                                    return new EntrevistaMap().toOutput(entrevistaDTO);
                                }
                            ).toList()
                        ) : null
                    ) : null)
                .pagoList(
                    dto.getPagoList() != null ? (
                        !dto.getPagoList().isEmpty() ? (
                            dto.getPagoList()
                                .stream().map(pagoDTO -> {
                                    return new PagoMap().toOutput(pagoDTO);
                                }
                            ).toList()
                        ) : null
                    ) : null)
                .build();
        }
        return null;
    }

    public List<EstadoOutput> toOutputList(List<EstadoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
