package ufps.edu.co.processor.crud;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.DocumentosrequisitoconcejocohorteMap;
import ufps.edu.co.records.input.entity.DocumentosrequisitoconcejocohorteInput.*;
import ufps.edu.co.records.output.entity.DocumentosrequisitoconcejocohorteOutput;
import ufps.edu.co.rest.dto.DocumentosrequisitoconcejocohorteDTO;
import ufps.edu.co.rest.services.DocumentosrequisitoconcejocohorteService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class DocumentosrequisitoconcejocohorteProcessor implements
        GlobalUseCase<DOCUMENTOSREQUISITOCONCEJOCOHORTE_CREATE, DOCUMENTOSREQUISITOCONCEJOCOHORTE_UPDATE, DOCUMENTOSREQUISITOCONCEJOCOHORTE_DELETE, DOCUMENTOSREQUISITOCONCEJOCOHORTE_PATCH, DOCUMENTOSREQUISITOCONCEJOCOHORTE_FIND, DocumentosrequisitoconcejocohorteOutput> {

    @Autowired
    private DocumentosrequisitoconcejocohorteService service;

    @Autowired
    private DocumentosrequisitoconcejocohorteMap map;

    @Override
    public DocumentosrequisitoconcejocohorteOutput create(DOCUMENTOSREQUISITOCONCEJOCOHORTE_CREATE input) {
        try {
            DocumentosrequisitoconcejocohorteDTO dto = map.toDto(input);
            return map.toOutput(service.create(dto));
        } catch (Exception e) {
            throw new RuntimeException("Error creating Documentosrequisitoconcejocohorte: " + e.getMessage(), e);
        }
    }

    @Override
    public DocumentosrequisitoconcejocohorteOutput update(DOCUMENTOSREQUISITOCONCEJOCOHORTE_UPDATE input) {
        try {
            DocumentosrequisitoconcejocohorteDTO dto = map.toDto(input);
            return map.toOutput(service.update(input.id(), dto));
        } catch (Exception e) {
            throw new RuntimeException("Error updating Documentosrequisitoconcejocohorte: " + e.getMessage(), e);
        }
    }

    @Override
    public DocumentosrequisitoconcejocohorteOutput patch(DOCUMENTOSREQUISITOCONCEJOCOHORTE_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Documentosrequisitoconcejocohorte");
    }

    @Override
    public DocumentosrequisitoconcejocohorteOutput findById(DOCUMENTOSREQUISITOCONCEJOCOHORTE_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new RuntimeException("Error finding Documentosrequisitoconcejocohorte by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentosrequisitoconcejocohorteOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Documentosrequisitoconcejocohorte: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(DOCUMENTOSREQUISITOCONCEJOCOHORTE_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Documentosrequisitoconcejocohorte by ID: " + e.getMessage(), e);
        }
    }

    public List<DocumentosrequisitoconcejocohorteOutput> findByIdCohorte(Integer idCohorte) {
        try {
            return service.findByIdCohorte(idCohorte).stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Documentosrequisitoconcejocohorte by cohorte: " + e.getMessage(), e);
        }
    }
}
