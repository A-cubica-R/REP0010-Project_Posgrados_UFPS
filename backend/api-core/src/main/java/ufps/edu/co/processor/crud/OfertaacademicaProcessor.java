package ufps.edu.co.processor.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.maps.specific.OfertaacademicaMap;
import ufps.edu.co.records.input.entity.OfertaacademicaInput.*;
import ufps.edu.co.records.output.entity.OfertaacademicaOutput;
import ufps.edu.co.rest.dto.OfertaacademicaDTO;
import ufps.edu.co.rest.dto.PlazoDTO;
import ufps.edu.co.rest.services.OfertaacademicaService;
import ufps.edu.co.rest.services.PlazoService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class OfertaacademicaProcessor implements
        GlobalUseCase<OFERTAACADEMICA_CREATE, OFERTAACADEMICA_UPDATE, OFERTAACADEMICA_DELETE, OFERTAACADEMICA_PATCH, OFERTAACADEMICA_FIND, OfertaacademicaOutput> {

    @Autowired
    private OfertaacademicaService service;

    @Autowired
    private OfertaacademicaMap map;

    @Autowired
    private PlazoService plazoService;

    @Override
    public OfertaacademicaOutput create(OFERTAACADEMICA_CREATE input) {
        try {
            OfertaacademicaDTO dto = map.toDto(input);
            OfertaacademicaDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating OfertaAcademica: " + e.getMessage(), e);
        }
    }

    public OfertaacademicaOutput createWithPlazo(OFERTAACADEMICA_CREATE_WITH_PLAZO input) {
        try {
            OfertaacademicaDTO dto = map.toDto(input);
            PlazoDTO plazoDto = dto.getPlazo();
            PlazoDTO createdPlazo = plazoService.create(plazoDto);
            dto.setIdPlazo(createdPlazo.getId());
            dto.setPlazo(null); // remove transient plazo reference to avoid Hibernate transient property error

            OfertaacademicaDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating OfertaAcademica with Plazo: " + e.getMessage(), e);
        }
    }

    @Override
    public OfertaacademicaOutput update(OFERTAACADEMICA_UPDATE input) {
        try {
            OfertaacademicaDTO dto = map.toDto(input);
            // Preserve idPlazo from existing record to avoid overwriting with default 0
            OfertaacademicaDTO existing = service.findById(input.id());
            if (existing == null) {
                throw new RuntimeException("Ofertaacademica no encontrado con id: " + input.id());
            }
            dto.setIdPlazo(existing.getIdPlazo());
            OfertaacademicaDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating OfertaAcademica: " + e.getMessage(), e);
        }
    }

    @Override
    public OfertaacademicaOutput patch(OFERTAACADEMICA_PATCH input) {
        throw new UnsupportedOperationException("Patch not supported for OfertaAcademica");
    }

    @Override
    public OfertaacademicaOutput findById(OFERTAACADEMICA_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new RuntimeException("Error finding OfertaAcademica: " + e.getMessage(), e);
        }
    }

    @Override
    public List<OfertaacademicaOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all OfertaAcademicas: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(OFERTAACADEMICA_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting OfertaAcademica: " + e.getMessage(), e);
        }
    }
}
