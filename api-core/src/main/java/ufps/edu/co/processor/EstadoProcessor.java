package ufps.edu.co.processor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.maps.EstadoMap;
import ufps.edu.co.records.input.EstadoInput;
import ufps.edu.co.records.input.EstadoInput.CREATE;
import ufps.edu.co.records.input.EstadoInput.UPDATE;
import ufps.edu.co.records.output.EstadoOutput;
import ufps.edu.co.rest.dto.EstadoDTO;
import ufps.edu.co.rest.services.EstadoService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class EstadoProcessor implements GlobalUseCase<EstadoInput.CREATE, EstadoInput.UPDATE, EstadoOutput, Integer> {

    @Autowired
    private EstadoService service;

    @Autowired
    private EstadoMap map;

    @Override
    public EstadoOutput create(CREATE input) {
        try {
            EstadoDTO dto = map.toDto(input);
            try {
                EstadoDTO created = service.create(dto);
                return map.toOutput(created);
            } catch (Exception e) {
                throw new RuntimeException("Error creating Estado: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating Estado: " + e.getMessage(), e);
        }
    }

    @Override
    public EstadoOutput update(Integer id, UPDATE input) {
        try {
            EstadoDTO dto = map.toDto(input);
            try {
                EstadoDTO updated = service.update(id, dto);
                return map.toOutput(updated);
            } catch (Exception e) {
                throw new RuntimeException("Error updating Estado: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating Estado: " + e.getMessage(), e);
        }
    }

    @Override
    public EstadoOutput findById(Integer id) {
        try {
            EstadoDTO dto = service.findById(id);
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Estado by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EstadoOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Estados: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Estado by ID: " + e.getMessage(), e);
        }
    }
}
