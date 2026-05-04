package ufps.edu.co.processor.crud;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ufps.edu.co.maps.specific.EntrevistadorMap;
import ufps.edu.co.records.input.entity.EntrevistadorInput.*;
import ufps.edu.co.records.output.entity.EntrevistadorOutput;
import ufps.edu.co.rest.dto.EntrevistadorDTO;
import ufps.edu.co.rest.services.EntrevistadorService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class EntrevistadorProcessor implements
        GlobalUseCase<ENTREVISTADOR_CREATE, ENTREVISTADOR_UPDATE, ENTREVISTADOR_DELETE, ENTREVISTADOR_PATCH, ENTREVISTADOR_FIND, EntrevistadorOutput> {

    @Autowired
    private EntrevistadorService service;

    @Autowired
    private EntrevistadorMap map;

    @Override
    public EntrevistadorOutput create(ENTREVISTADOR_CREATE input) {
        try {
            EntrevistadorDTO dto = map.toDto(input);
            EntrevistadorDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Entrevistador: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistadorOutput update(ENTREVISTADOR_UPDATE input) {
        try {
            EntrevistadorDTO dto = map.toDto(input);
            EntrevistadorDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Entrevistador: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistadorOutput patch(ENTREVISTADOR_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Entrevistador");
    }

    @Override
    @Transactional(readOnly = true)
    public EntrevistadorOutput findById(ENTREVISTADOR_FIND input) {
        try {
            EntrevistadorDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Entrevistador by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EntrevistadorOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Entrevistadores: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ENTREVISTADOR_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Entrevistador by ID: " + e.getMessage(), e);
        }
    }
}
