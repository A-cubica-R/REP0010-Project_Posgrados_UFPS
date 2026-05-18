package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.entity.PlazoInput.*;
import ufps.edu.co.records.output.entity.PlazoOutput;
import ufps.edu.co.records.output.entity.TipoplazoOutput;
import ufps.edu.co.rest.dto.PlazoDTO;

@Component
public class PlazoMap extends
        GlobalMapper<PLAZO_CREATE, PLAZO_UPDATE, PLAZO_DELETE, PLAZO_PATCH, PLAZO_FIND, PlazoOutput, PlazoDTO> {

    public PlazoMap() {
        super(PLAZO_CREATE.class, PLAZO_UPDATE.class, PLAZO_DELETE.class, PLAZO_PATCH.class,
                PLAZO_FIND.class);
    }

    @Override
    protected PlazoDTO toDtoCreate(PLAZO_CREATE input) {
        PlazoDTO dto = PlazoDTO.builder()
                .fechainicio(input.fechainicio())
                .fechafin(input.fechafin())
                .idTipoplazo(input.idTipoplazo())
                .build();
        return dto;
    }

    @Override
    protected PlazoDTO toDtoUpdate(PLAZO_UPDATE input) {
        PlazoDTO dto = new PlazoDTO();
        dto.setId(input.id());
        dto.setFechainicio(input.fechainicio());
        dto.setFechafin(input.fechafin());
        dto.setIdTipoplazo(input.idTipoplazo());
        return dto;
    }

    @Override
    protected PlazoDTO toDtoDelete(PLAZO_DELETE input) {
        PlazoDTO dto = new PlazoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected PlazoDTO toDtoPatch(PLAZO_PATCH input) {
        PlazoDTO.PlazoDTOBuilder builder = PlazoDTO.builder()
                .id(input.id());

        if (input.fechainicio() != null) {
            builder.fechainicio(input.fechainicio());
        }
        if (input.fechafin() != null) {
            builder.fechafin(input.fechafin());
        }
        if (input.idTipoplazo() != null) {
            builder.idTipoplazo(input.idTipoplazo());
        }

        return builder.build();
    }

    @Override
    protected PlazoDTO toDtoFind(PLAZO_FIND input) {
        PlazoDTO dto = new PlazoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public PlazoOutput toOutput(PlazoDTO dto) {
        if (dto != null) {
            return PlazoOutput.builder()
                    .id(dto.getId())
                    .fechainicio(dto.getFechainicio())
                    .fechafin(dto.getFechafin())
                    .idTipoplazo(dto.getIdTipoplazo())
                    .tipoplazo(
                        dto.getTipoplazo() != null ? (
                            TipoplazoOutput.builder()
                                .id(dto.getTipoplazo().getId())
                                .tipo(dto.getTipoplazo().getTipo())
                                .build()
                        ) : null
                    )
                    .build();
        }
        return null;
    }

    public List<PlazoOutput> toOutputList(List<PlazoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
