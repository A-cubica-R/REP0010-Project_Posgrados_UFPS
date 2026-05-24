package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;
import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramacohorteInput.*;
import ufps.edu.co.records.output.entity.DocumentosrequisitoprogramacohorteOutput;
import ufps.edu.co.rest.dto.DocumentosrequisitoprogramacohorteDTO;

@Component
@UniversalMapping(
    create = DOCUMENTOSREQUISITOPROGRAMACOHORTE_CREATE.class,
    update = DOCUMENTOSREQUISITOPROGRAMACOHORTE_UPDATE.class,
    delete = DOCUMENTOSREQUISITOPROGRAMACOHORTE_DELETE.class,
    patch = DOCUMENTOSREQUISITOPROGRAMACOHORTE_PATCH.class,
    find = DOCUMENTOSREQUISITOPROGRAMACOHORTE_FIND.class)
public class DocumentosrequisitoprogramacohorteMap extends UniversalMapper<DocumentosrequisitoprogramacohorteOutput, DocumentosrequisitoprogramacohorteDTO> {

    @Override
    public DocumentosrequisitoprogramacohorteOutput toOutput(DocumentosrequisitoprogramacohorteDTO dto) {
        return DocumentosrequisitoprogramacohorteOutput.builder()
                .id(dto.getId())
                .idDocrequisito(dto.getIdDocrequisito())
                .idCohorte(dto.getIdCohorte())
                .build();
    }
}
