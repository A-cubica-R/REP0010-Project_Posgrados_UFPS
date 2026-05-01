package ufps.edu.co.processor.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.maps.specific.JornadaMap;
import ufps.edu.co.records.input.entity.JornadaInput.*;
import ufps.edu.co.records.output.entity.JornadaOutput;
import ufps.edu.co.rest.dto.JornadaDTO;
import ufps.edu.co.rest.services.JornadaService;
import ufps.edu.co.usecase.GlobalUseCase;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JornadaProcessor implements GlobalUseCase<JORNADA_CREATE, JORNADA_UPDATE, JORNADA_DELETE, JORNADA_PATCH, JORNADA_FIND, JornadaOutput> {

    @Autowired
    private JornadaService service;

    @Autowired
    private JornadaMap map;

    @Override
    public JornadaOutput create(JORNADA_CREATE input) {
        try {
            JornadaDTO dto = map.toDtoCreate(input);
            try {
                JornadaDTO created = service.create(dto);
                return map.toOutput(created);
            } catch (Exception e) {
                throw new RuntimeException("Error creating Jornada: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating Jornada: " + e.getMessage(), e);
        }
    }

    @Override
    public JornadaOutput update(JORNADA_UPDATE input) {
        try {
            JornadaDTO dto = map.toDtoUpdate(input);
            try {
                JornadaDTO updated = service.update(input.id(), dto);
                return map.toOutput(updated);
            } catch (Exception e) {
                throw new RuntimeException("Error updating Jornada: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating Jornada: " + e.getMessage(), e);
        }
    }

    @Override
    public JornadaOutput findById(JORNADA_FIND input) {
        try {
            JornadaDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Jornada by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<JornadaOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Jornadas: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(JORNADA_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Jornada by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public JornadaOutput patch(JORNADA_PATCH input) {
        return null;
    }

}
