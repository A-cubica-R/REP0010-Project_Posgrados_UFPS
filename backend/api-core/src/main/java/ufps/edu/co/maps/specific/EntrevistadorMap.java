package ufps.edu.co.maps.specific;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ufps.edu.co.maps.GlobalMapper;
import ufps.edu.co.processor.crud.AdministrativoProcessor;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_FIND;
import ufps.edu.co.records.input.entity.EntrevistadorInput.*;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.EntrevistadorOutput;
import ufps.edu.co.rest.dto.EntrevistadorDTO;

@Component
public class EntrevistadorMap extends
        GlobalMapper<ENTREVISTADOR_CREATE, ENTREVISTADOR_UPDATE, ENTREVISTADOR_DELETE, ENTREVISTADOR_PATCH, ENTREVISTADOR_FIND, EntrevistadorOutput, EntrevistadorDTO> {

    @Autowired
    private AdministrativoProcessor administrativoProcessor;

    public EntrevistadorMap() {
        super(ENTREVISTADOR_CREATE.class, ENTREVISTADOR_UPDATE.class, ENTREVISTADOR_DELETE.class,
                ENTREVISTADOR_PATCH.class,
                ENTREVISTADOR_FIND.class);
    }

    @Override
    protected EntrevistadorDTO toDtoCreate(ENTREVISTADOR_CREATE input) {
        EntrevistadorDTO dto = new EntrevistadorDTO();
        dto.setIdAdministrativo(input.idAdministrativo());
        dto.setObservaciones(input.observaciones());
        return dto;
    }

    @Override
    protected EntrevistadorDTO toDtoUpdate(ENTREVISTADOR_UPDATE input) {
        EntrevistadorDTO dto = new EntrevistadorDTO();
        dto.setId(input.id());
        dto.setIdAdministrativo(input.idAdministrativo());
        dto.setObservaciones(input.observaciones());
        return dto;
    }

    @Override
    protected EntrevistadorDTO toDtoDelete(ENTREVISTADOR_DELETE input) {
        EntrevistadorDTO dto = new EntrevistadorDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    protected EntrevistadorDTO toDtoPatch(ENTREVISTADOR_PATCH input) {
        EntrevistadorDTO.EntrevistadorDTOBuilder builder = EntrevistadorDTO.builder()
                .id(input.id());

        if (input.idAdministrativo() != null) {
            builder.idAdministrativo(input.idAdministrativo());
        }
        if (input.observaciones() != null) {
            builder.observaciones(input.observaciones());
        }

        return builder.build();
    }

    @Override
    protected EntrevistadorDTO toDtoFind(ENTREVISTADOR_FIND input) {
        EntrevistadorDTO dto = new EntrevistadorDTO();
        dto.setId(input.id());
        return dto;
    }

    @Override
    public EntrevistadorOutput toOutput(EntrevistadorDTO dto) {
        if (dto == null) {
            return null;
        }

        AdministrativoOutput administrativo = null;
        if (dto.getIdAdministrativo() != 0) { // ← usar idAdministrativo directo
            administrativo = administrativoProcessor.findById(
                    new ADMINISTRATIVO_FIND(dto.getIdAdministrativo()));
        }

        return EntrevistadorOutput.builder()
                .id(dto.getId())
                .observaciones(dto.getObservaciones())
                .administrativo(administrativo) // ✅ poblado
                .build();
    }

    public List<EntrevistadorOutput> toOutputList(List<EntrevistadorDTO> dtoList) {
        return dtoList.stream().map(this::toOutput).toList();
    }
}
