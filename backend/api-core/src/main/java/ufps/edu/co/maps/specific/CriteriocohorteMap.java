package ufps.edu.co.maps.specific;

import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.CriteriocohorteInput.*;
import ufps.edu.co.records.output.entity.CriteriocohorteOutput;
import ufps.edu.co.rest.dto.CriteriocohorteDTO;

@UniversalMapping(
    create = CRITERIOCOHORTE_CREATE.class,
    update = CRITERIOCOHORTE_UPDATE.class,
    delete = CRITERIOCOHORTE_DELETE.class,
    find = CRITERIOCOHORTE_FIND.class,
    patch = CRITERIOCOHORTE_PATCH.class
)
public class CriteriocohorteMap extends UniversalMapper<CriteriocohorteOutput, CriteriocohorteDTO> {
    
    @Override
    public CriteriocohorteOutput toOutput(CriteriocohorteDTO dto) {
        if (dto != null) {
            return CriteriocohorteOutput.builder()
                    .id(dto.getId())
                    .pesoSnapshot(dto.getPesoSnapshot())
                    .idCohorte(dto.getIdCohorte())
                    .idCriterio(dto.getIdCriterio())
                    .build();
        }
        return null;
    }
}
