package ufps.edu.co.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.maps.JornadaMap;
import ufps.edu.co.records.input.JornadaInput;
import ufps.edu.co.records.input.JornadaInput.*;
import ufps.edu.co.records.output.JornadaOutput;
import ufps.edu.co.rest.dto.JornadaDTO;
import ufps.edu.co.rest.services.JornadaService;
import ufps.edu.co.usecase.GlobalUseCase;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JornadaProcessor
    implements GlobalUseCase<JornadaInput.CREATE, JornadaInput.UPDATE, JornadaOutput, Integer> {

    @Autowired
    private JornadaService service;

    @Autowired
    private JornadaMap map;

    @Override
    public JornadaOutput create(CREATE input) {
        try {
            JornadaDTO dto = map.toDto(input);
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
    public JornadaOutput update(Integer id, UPDATE input) {
        try {
            JornadaDTO dto = map.toDto(input);
            try {
                JornadaDTO updated = service.update(id, dto);
                return map.toOutput(updated);
            } catch (Exception e) {
                throw new RuntimeException("Error updating Jornada: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating Jornada: " + e.getMessage(), e);
        }
    }

    @Override
    public JornadaOutput findById(Integer id) {
        try {
            JornadaDTO dto = service.findById(id);
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
    public void deleteById(Integer id) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Jornada by ID: " + e.getMessage(), e);
        }
    }


}
