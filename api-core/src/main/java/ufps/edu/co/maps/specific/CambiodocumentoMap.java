package ufps.edu.co.maps.specific;

import java.util.List;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.records.input.CambiodocumentoInput.*;
import ufps.edu.co.records.output.CambiodocumentoOutput;
import ufps.edu.co.rest.dto.CambiodocumentoDTO;

@Component
public class CambiodocumentoMap extends
        GlobalMapper<CAMBIODOCUMENTO_CREATE, CAMBIODOCUMENTO_UPDATE, CAMBIODOCUMENTO_DELETE, CAMBIODOCUMENTO_PATCH, CAMBIODOCUMENTO_FIND, CambiodocumentoOutput, CambiodocumentoDTO> {

    public CambiodocumentoMap() {
        super(CAMBIODOCUMENTO_CREATE.class, CAMBIODOCUMENTO_UPDATE.class, CAMBIODOCUMENTO_DELETE.class, CAMBIODOCUMENTO_PATCH.class,
                CAMBIODOCUMENTO_FIND.class);
    }

    @Override
    protected CambiodocumentoDTO toDtoCreate(CAMBIODOCUMENTO_CREATE input) {
        CambiodocumentoDTO dto = new CambiodocumentoDTO();
        dto.setIdDocumentoanterior(input.idDocumentoanterior());
        dto.setIdDocumentoactual(input.idDocumentoactual());
        return dto;
    }

    @Override
    protected CambiodocumentoDTO toDtoUpdate(CAMBIODOCUMENTO_UPDATE input) {
        CambiodocumentoDTO dto = new CambiodocumentoDTO();
        dto.setId(input.id());
        dto.setIdDocumentoanterior(input.idDocumentoanterior());
        dto.setIdDocumentoactual(input.idDocumentoactual());
        return dto;
    }

    @Override
    protected CambiodocumentoDTO toDtoDelete(CAMBIODOCUMENTO_DELETE input) {
        CambiodocumentoDTO dto = new CambiodocumentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected CambiodocumentoDTO toDtoPatch(CAMBIODOCUMENTO_PATCH input) {
        CambiodocumentoDTO.CambiodocumentoDTOBuilder builder = CambiodocumentoDTO.builder()
                .id(input.id());

        if (input.idDocumentoanterior() != null) {
            builder.idDocumentoanterior(input.idDocumentoanterior());
        }
        if (input.idDocumentoactual() != null) {
            builder.idDocumentoactual(input.idDocumentoactual());
        }

        return builder.build();
    }

    @Override
    protected CambiodocumentoDTO toDtoFind(CAMBIODOCUMENTO_FIND input) {
        CambiodocumentoDTO dto = new CambiodocumentoDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public CambiodocumentoOutput toOutput(CambiodocumentoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new CambiodocumentoOutput(dto.getId(), null, null);
    }

    public List<CambiodocumentoOutput> toOutputList(List<CambiodocumentoDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
