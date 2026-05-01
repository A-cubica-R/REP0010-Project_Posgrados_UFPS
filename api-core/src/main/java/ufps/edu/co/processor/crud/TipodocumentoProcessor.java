package ufps.edu.co.processor.crud;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.TipodocumentoMap;
import ufps.edu.co.records.input.entity.TipodocumentoInput.*;
import ufps.edu.co.records.output.entity.TipodocumentoOutput;
import ufps.edu.co.rest.dto.TipodocumentoDTO;
import ufps.edu.co.rest.services.TipodocumentoService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class TipodocumentoProcessor implements
        GlobalUseCase<TIPODOCUMENTO_CREATE, TIPODOCUMENTO_UPDATE, TIPODOCUMENTO_DELETE, TIPODOCUMENTO_PATCH, TIPODOCUMENTO_FIND, TipodocumentoOutput> {

    @Autowired
    private TipodocumentoService service;

    @Autowired
    private TipodocumentoMap map;

    @Override
    public TipodocumentoOutput create(TIPODOCUMENTO_CREATE input) {
        try {
            TipodocumentoDTO dto = map.toDto(input);
            TipodocumentoDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Tipodocumento: " + e.getMessage(), e);
        }
    }

    @Override
    public TipodocumentoOutput update(TIPODOCUMENTO_UPDATE input) {
        try {
            TipodocumentoDTO dto = map.toDto(input);
            TipodocumentoDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Tipodocumento: " + e.getMessage(), e);
        }
    }

    @Override
    public TipodocumentoOutput patch(TIPODOCUMENTO_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Tipodocumento");
    }

    @Override
    public TipodocumentoOutput findById(TIPODOCUMENTO_FIND input) {
        try {
            TipodocumentoDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Tipodocumento by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<TipodocumentoOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Tipodocumentos: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(TIPODOCUMENTO_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Tipodocumento by ID: " + e.getMessage(), e);
        }
    }
}
