package ufps.edu.co.processor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.maps.ModalidadMap;
import ufps.edu.co.records.input.ModalidadInput;
import ufps.edu.co.records.input.ModalidadInput.*;
import ufps.edu.co.records.output.ModalidadOutput;
import ufps.edu.co.rest.dto.ModalidadDTO;
import ufps.edu.co.rest.services.ModalidadService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class ModalidadProcessor implements GlobalUseCase<ModalidadInput.CREATE, ModalidadInput.UPDATE, ModalidadOutput, Integer> {

    @Autowired
    private ModalidadService service;

    @Autowired
    private ModalidadMap map;

    @Override
    public ModalidadOutput create(CREATE input) {
        try {
            ModalidadDTO dto = map.toDtoCreate(input);
            try {
                ModalidadDTO created = service.create(dto);
                return map.toOutput(created);
            } catch (Exception e) {
                throw new RuntimeException("Error creating Modalidad: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating Modalidad: " + e.getMessage(), e);
        }
    }

    @Override
    public ModalidadOutput update(Integer id, UPDATE input) {
        try {
            ModalidadDTO dto = map.toDto(input);
            try {
                ModalidadDTO updated = service.update(id, dto);
                return map.toOutput(updated);
            } catch (Exception e) {
                throw new RuntimeException("Error updating Modalidad: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating Modalidad: " + e.getMessage(), e);
        }
    }

    @Override
    public ModalidadOutput findById(Integer id) {
        try {
            ModalidadDTO dto = service.findById(id);
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Modalidad by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ModalidadOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Modalidades: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Modalidad by ID: " + e.getMessage(), e);
        }
    }
}
