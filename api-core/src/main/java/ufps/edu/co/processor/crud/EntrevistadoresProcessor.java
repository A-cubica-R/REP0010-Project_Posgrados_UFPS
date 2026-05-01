package ufps.edu.co.processor.crud;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.EntrevistadoresMap;
import ufps.edu.co.records.input.entity.EntrevistadoresInput.*;
import ufps.edu.co.records.output.entity.EntrevistadoresOutput;
import ufps.edu.co.rest.dto.EntrevistadoresDTO;
import ufps.edu.co.rest.services.EntrevistadoresService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class EntrevistadoresProcessor implements
        GlobalUseCase<ENTREVISTADORES_CREATE, ENTREVISTADORES_UPDATE, ENTREVISTADORES_DELETE, ENTREVISTADORES_PATCH, ENTREVISTADORES_FIND, EntrevistadoresOutput> {

    @Autowired
    private EntrevistadoresService service;

    @Autowired
    private EntrevistadoresMap map;

    @Override
    public EntrevistadoresOutput create(ENTREVISTADORES_CREATE input) {
        try {
            EntrevistadoresDTO dto = map.toDto(input);
            EntrevistadoresDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Entrevistadores: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistadoresOutput update(ENTREVISTADORES_UPDATE input) {
        try {
            EntrevistadoresDTO dto = map.toDto(input);
            EntrevistadoresDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Entrevistadores: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistadoresOutput patch(ENTREVISTADORES_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Entrevistadores");
    }

    @Override
    public EntrevistadoresOutput findById(ENTREVISTADORES_FIND input) {
        try {
            EntrevistadoresDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Entrevistadores by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EntrevistadoresOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Entrevistadores: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ENTREVISTADORES_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Entrevistadores by ID: " + e.getMessage(), e);
        }
    }
}
