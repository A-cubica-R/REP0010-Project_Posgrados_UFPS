package ufps.edu.co.processor;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.DepartamentoMap;
import ufps.edu.co.records.input.DepartamentoInput.*;
import ufps.edu.co.records.output.DepartamentoOutput;
import ufps.edu.co.rest.dto.DepartamentoDTO;
import ufps.edu.co.rest.services.DepartamentoService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class DepartamentoProcessor
        implements GlobalUseCase<DEPARTAMENTO_CREATE, DEPARTAMENTO_UPDATE, DEPARTAMENTO_DELETE, DEPARTAMENTO_PATCH, DEPARTAMENTO_FIND, DepartamentoOutput> {

    @Autowired
    private DepartamentoService service;

    @Autowired
    private DepartamentoMap map;

    @Override
    public DepartamentoOutput create(DEPARTAMENTO_CREATE input) {
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
    public DepartamentoOutput update(DEPARTAMENTO_UPDATE input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public DepartamentoOutput findById(DEPARTAMENTO_FIND input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<DepartamentoOutput> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void deleteById(DEPARTAMENTO_DELETE input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public DepartamentoOutput patch(DEPARTAMENTO_PATCH input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patch'");
    }
}
