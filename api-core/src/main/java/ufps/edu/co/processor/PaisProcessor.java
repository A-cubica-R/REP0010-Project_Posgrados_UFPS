package ufps.edu.co.processor;

import java.util.List;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public PaisOutput findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<PaisOutput> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
}
