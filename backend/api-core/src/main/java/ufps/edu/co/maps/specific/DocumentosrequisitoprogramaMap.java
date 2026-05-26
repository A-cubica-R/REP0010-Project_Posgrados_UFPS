package ufps.edu.co.maps.specific;

import org.springframework.stereotype.Component;
import ufps.edu.co.domain.annotations.UniversalMapping;
import ufps.edu.co.maps.UniversalMapper;
import ufps.edu.co.records.input.entity.DocumentosrequisitoprogramaInput.*;
import ufps.edu.co.records.output.entity.DocumentosrequisitoprogramaOutput;
import ufps.edu.co.rest.dto.DocumentosrequisitoprogramaDTO;

@Component
@UniversalMapping(
    create = DOCUMENTOSREQUISITOPROGRAMA_CREATE.class,
    update = DOCUMENTOSREQUISITOPROGRAMA_UPDATE.class,
    delete = DOCUMENTOSREQUISITOPROGRAMA_DELETE.class,
    patch = DOCUMENTOSREQUISITOPROGRAMA_PATCH.class,
    find = DOCUMENTOSREQUISITOPROGRAMA_FIND.class)
public class DocumentosrequisitoprogramaMap extends UniversalMapper<DocumentosrequisitoprogramaOutput, DocumentosrequisitoprogramaDTO> {

    @Override
    public DocumentosrequisitoprogramaOutput toOutput(DocumentosrequisitoprogramaDTO dto) {
        return DocumentosrequisitoprogramaOutput.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .tamanomaximo(dto.getTamanomaximo())
                .urlformato(dto.getUrlformato())
                .id_programa(dto.getId_programa())
                .build();
    }
}
