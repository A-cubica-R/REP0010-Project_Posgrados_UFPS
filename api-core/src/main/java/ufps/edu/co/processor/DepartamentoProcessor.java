package ufps.edu.co.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.maps.DepartamentoMap;
import ufps.edu.co.persistence.repositories.DepartamentoRepository;
import ufps.edu.co.records.input.DepartamentoInput;
import ufps.edu.co.records.input.DepartamentoInput.CREATE;
import ufps.edu.co.records.input.DepartamentoInput.UPDATE;
import ufps.edu.co.records.output.DepartamentoOutput;
import ufps.edu.co.rest.dto.DepartamentoDTO;
import ufps.edu.co.rest.services.DepartamentoService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class DepartamentoProcessor
        implements GlobalUseCase<DepartamentoInput.CREATE, DepartamentoInput.UPDATE, DepartamentoOutput, Integer> {

    @Autowired
    private DepartamentoService service;

    @Autowired
    private DepartamentoMap map;

    @Override
    public DepartamentoOutput create(CREATE input) {
        try {
            DepartamentoDTO dto = map.toDto(input);
            try {
                DepartamentoOutput output = map.toOutput(service.create(dto));
                return output;
            } catch (Exception e) {
                throw new RuntimeException("Error creating Departamento: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error creating Departamento: " + e.getMessage(), e);
        }
    }

    @Override
    public DepartamentoOutput update(Integer id, UPDATE input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public DepartamentoOutput findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<DepartamentoOutput> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
