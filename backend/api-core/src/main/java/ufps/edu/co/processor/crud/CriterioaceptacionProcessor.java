package ufps.edu.co.processor.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.maps.specific.CriterioaceptacionMap;
import ufps.edu.co.records.input.entity.CriterioaceptacionInput.*;
import ufps.edu.co.records.output.entity.CriterioaceptacionOutput;
import ufps.edu.co.rest.dto.CriterioaceptacionDTO;
import ufps.edu.co.rest.services.CriterioaceptacionService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class CriterioaceptacionProcessor implements
        GlobalUseCase<CRITERIOACEPTACION_CREATE, CRITERIOACEPTACION_UPDATE, CRITERIOACEPTACION_DELETE, CRITERIOACEPTACION_PATCH, CRITERIOACEPTACION_FIND, CriterioaceptacionOutput> {

    @Autowired
    private CriterioaceptacionService service;

    @Autowired
    private CriterioaceptacionMap map;

    @Override
    public CriterioaceptacionOutput create(CRITERIOACEPTACION_CREATE input) {
        try {
            CriterioaceptacionDTO dto = map.toDto(input);
            CriterioaceptacionDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Criterioaceptacion: " + e.getMessage(), e);
        }
    }

    @Override
    public CriterioaceptacionOutput update(CRITERIOACEPTACION_UPDATE input) {
        try {
            CriterioaceptacionDTO dto = map.toDto(input);
            CriterioaceptacionDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Criterioaceptacion: " + e.getMessage(), e);
        }
    }

    @Override
    public CriterioaceptacionOutput patch(CRITERIOACEPTACION_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Criterioaceptacion");
    }

    @Override
    public CriterioaceptacionOutput findById(CRITERIOACEPTACION_FIND input) {
        try {
            CriterioaceptacionDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Criterioaceptacion by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CriterioaceptacionOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Criterioaceptaciones: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(CRITERIOACEPTACION_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Criterioaceptacion by ID: " + e.getMessage(), e);
        }
    }
}
