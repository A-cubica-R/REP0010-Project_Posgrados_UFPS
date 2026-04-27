package ufps.edu.co.processor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.PaisMap;
import ufps.edu.co.records.input.PaisInput;
import ufps.edu.co.records.input.PaisInput.*;
import ufps.edu.co.records.output.PaisOutput;
import ufps.edu.co.rest.dto.PaisDTO;
import ufps.edu.co.rest.services.PaisService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class PaisProcessor implements GlobalUseCase<PaisInput.CREATE, PaisInput.UPDATE, PaisOutput, Integer> {

    @Autowired
    private PaisService service;

    @Autowired
    private PaisMap map;

    @Override
    public PaisOutput create(CREATE input) {
        try {
            PaisDTO dto = map.toDto(input);
            try {
                PaisOutput output = map.toOutput(service.create(dto));
                return output;
            } catch (Exception e) {
                throw new RuntimeException("Error creating Pais: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating Pais: " + e.getMessage(), e);
        }
    }

    @Override
    public PaisOutput update(Integer id, UPDATE input) {
        try {
            PaisDTO dto = map.toDto(input, id);
            try {
                PaisOutput output = map.toOutput(service.update(id, dto));
                return output;
            } catch (Exception e) {
                throw new RuntimeException("Error updating Pais: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating Pais: " + e.getMessage(), e);
        }
    }

    @Override
    public PaisOutput findById(Integer id) {
        try {
            PaisOutput output = map.toOutput(service.findById(id));
            return output;
        } catch (Exception e) {
            throw new RuntimeException("Error finding Pais by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PaisOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Paises: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            service.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Pais by ID: " + e.getMessage(), e);
        }
    }
}
