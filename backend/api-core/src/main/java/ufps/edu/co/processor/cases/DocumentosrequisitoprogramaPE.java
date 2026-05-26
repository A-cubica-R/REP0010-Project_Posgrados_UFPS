package ufps.edu.co.processor.cases;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.maps.specific.DocumentosrequisitoprogramaMap;
import ufps.edu.co.processor.crud.DocumentosrequisitoprogramaProcessor;
import ufps.edu.co.records.output.entity.DocumentosrequisitoprogramaOutput;
import ufps.edu.co.rest.dto.DocumentosrequisitoconsejoDTO;
import ufps.edu.co.rest.services.DocumentosrequisitoprogramaService;
import ufps.edu.co.rest.services.DocumentosrequisitoconsejoService;

@Service
public class DocumentosrequisitoprogramaPE extends DocumentosrequisitoprogramaProcessor {
    
    @Autowired
    private DocumentosrequisitoprogramaService service;

    @Autowired
    private DocumentosrequisitoprogramaMap map;

    @Autowired
    private DocumentosrequisitoconsejoService consejoService;

    public List<DocumentosrequisitoprogramaOutput> findByIdPrograma(Integer idPrograma) {
        try {
            return service.findByIdPrograma(idPrograma).stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Documentosrequisitoprograma by programa: " + e.getMessage(), e);
        }
    }
    
    public List<DocumentosrequisitoprogramaOutput> findByIdProgramaAndConsejo(Integer idPrograma) {
        try {
            List<DocumentosrequisitoprogramaOutput> programaDocs = findByIdPrograma(idPrograma);
            List<DocumentosrequisitoprogramaOutput> consejoDocs = consejoService.findByIdPrograma(idPrograma)
                    .stream()
                    .map(doc -> toProgramaOutput(doc, idPrograma))
                    .toList();

            return Stream.concat(programaDocs.stream(), consejoDocs.stream()).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Documentosrequisitoprograma by programa y consejo: " + e.getMessage(), e);
        }
    }

    private DocumentosrequisitoprogramaOutput toProgramaOutput(DocumentosrequisitoconsejoDTO consejoDoc, Integer idPrograma) {
        return DocumentosrequisitoprogramaOutput.builder()
                .id(consejoDoc.getId())
                .nombre(consejoDoc.getNombre())
                .tamanomaximo(consejoDoc.getTamanomaximo())
                .urlformato(consejoDoc.getUrlformato())
                .id_programa(idPrograma)
                .build();
    }
}
