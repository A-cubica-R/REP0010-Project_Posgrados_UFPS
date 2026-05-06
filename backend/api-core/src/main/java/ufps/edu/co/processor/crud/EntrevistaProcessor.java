package ufps.edu.co.processor.crud;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ufps.edu.co.maps.specific.EntrevistaMap;
import ufps.edu.co.records.input.entity.EntrevistaInput.*;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.rest.dto.EntrevistaDTO;
import ufps.edu.co.rest.services.EntrevistaService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class EntrevistaProcessor implements
        GlobalUseCase<ENTREVISTA_CREATE, ENTREVISTA_UPDATE, ENTREVISTA_DELETE, ENTREVISTA_PATCH, ENTREVISTA_FIND, EntrevistaOutput> {

    @Autowired
    private EntrevistaService service;

    @Autowired
    private EntrevistaMap map;

    @Override
    public EntrevistaOutput create(ENTREVISTA_CREATE input) {
        try {
            EntrevistaDTO dto = map.toDto(input);
            EntrevistaDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Entrevista: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistaOutput update(ENTREVISTA_UPDATE input) {
        try {
            EntrevistaDTO dto = map.toDto(input);
            EntrevistaDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Entrevista: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistaOutput patch(ENTREVISTA_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Entrevista");
    }

    @Override
    @Transactional(readOnly = true)
    public EntrevistaOutput findById(ENTREVISTA_FIND input) {
        try {
            EntrevistaDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Entrevista by ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntrevistaOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Entrevistas: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ENTREVISTA_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Entrevista by ID: " + e.getMessage(), e);
        }
    }

    public EntrevistaOutput rateInterview(ENTREVISTA_RATE input) {
        try {
            EntrevistaDTO updated = service.findById(input.id());
            ENTREVISTA_UPDATE rate = new ENTREVISTA_UPDATE(
                    updated.getId(),
                    updated.getFecha(),
                    updated.getTiempo(),
                    input.calificacion(),
                    updated.getIdTipoentrevista(),
                    updated.getIdEntrevistador(),
                    updated.getIdAspirante(),
                    updated.getIdEstado(),
                    updated.getIdUbicacion()
            );
            updated.setCalificacion(input.calificacion());
            this.update(rate);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error rating Entrevista: " + e.getMessage(), e);
        }
    }
}
