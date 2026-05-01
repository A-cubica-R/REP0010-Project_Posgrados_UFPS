package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.EntrevistadoresInput.*;
import ufps.edu.co.records.output.*;
import ufps.edu.co.rest.dto.EntrevistadoresDTO;

@Component
public class EntrevistadoresMap extends
        GlobalMapper<ENTREVISTADORES_CREATE, ENTREVISTADORES_UPDATE, ENTREVISTADORES_DELETE, ENTREVISTADORES_PATCH, ENTREVISTADORES_FIND, EntrevistadoresOutput, EntrevistadoresDTO> {

    public EntrevistadoresMap() {
        super(ENTREVISTADORES_CREATE.class, ENTREVISTADORES_UPDATE.class, ENTREVISTADORES_DELETE.class, ENTREVISTADORES_PATCH.class,
                ENTREVISTADORES_FIND.class);
    }

    @Override
    protected EntrevistadoresDTO toDtoCreate(ENTREVISTADORES_CREATE input) {
        EntrevistadoresDTO dto = new EntrevistadoresDTO();
        dto.setIdEntrevista(input.idEntrevista());
        dto.setIdAdministrativo(input.idAdministrativo());
        return dto;
    }

    @Override
    protected EntrevistadoresDTO toDtoUpdate(ENTREVISTADORES_UPDATE input) {
        EntrevistadoresDTO dto = new EntrevistadoresDTO();
        dto.setId(input.id());
        dto.setIdEntrevista(input.idEntrevista());
        dto.setIdAdministrativo(input.idAdministrativo());
        return dto;
    }

    @Override
    protected EntrevistadoresDTO toDtoDelete(ENTREVISTADORES_DELETE input) {
        EntrevistadoresDTO dto = new EntrevistadoresDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected EntrevistadoresDTO toDtoPatch(ENTREVISTADORES_PATCH input) {
        EntrevistadoresDTO.EntrevistadoresDTOBuilder builder = EntrevistadoresDTO.builder()
                .id(input.id());

        if (input.idEntrevista() != null) {
            builder.idEntrevista(input.idEntrevista());
        }
        if (input.idAdministrativo() != null) {
            builder.idAdministrativo(input.idAdministrativo());
        }

        return builder.build();
    }

    @Override
    protected EntrevistadoresDTO toDtoFind(ENTREVISTADORES_FIND input) {
        EntrevistadoresDTO dto = new EntrevistadoresDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public EntrevistadoresOutput toOutput(EntrevistadoresDTO dto) {
        if (dto == null) {
            return null;
        }

        EntrevistaOutput entrevista = null;
        if (dto.getEntrevista() != null) {
            entrevista = EntrevistaOutput.builder()
                    .id(dto.getEntrevista().getId())
                    .fecha(dto.getEntrevista().getFecha())
                    .calificacion(dto.getEntrevista().getCalificacion())
                    .build();
        }

        AdministrativoOutput administrativo = null;
        if (dto.getAdministrativo() != null) {
            administrativo = AdministrativoOutput.builder()
                    .id(dto.getAdministrativo().getId())
                    .build();
        }

        return new EntrevistadoresOutput(dto.getId(), entrevista, administrativo);
    }

    public List<EntrevistadoresOutput> toOutputList(List<EntrevistadoresDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
