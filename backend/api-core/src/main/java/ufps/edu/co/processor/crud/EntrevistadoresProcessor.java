package ufps.edu.co.processor.crud;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.EntrevistadoresMap;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_FIND;
import ufps.edu.co.records.input.entity.EntrevistadoresInput.*;
import ufps.edu.co.records.output.entity.EntrevistadoresOutput;
import ufps.edu.co.rest.dto.EntrevistadoresDTO;
import ufps.edu.co.rest.services.EntrevistadoresService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class EntrevistadoresProcessor implements
        GlobalUseCase<ENTREVISTADORES_CREATE, ENTREVISTADORES_UPDATE, ENTREVISTADORES_DELETE, ENTREVISTADORES_PATCH, ENTREVISTADORES_FIND, EntrevistadoresOutput> {

    @Autowired
    private EntrevistadoresService service;

    @Autowired
    private EntrevistadoresMap map;

    @Override
    public EntrevistadoresOutput create(ENTREVISTADORES_CREATE input) {
        try {
            EntrevistadoresDTO dto = map.toDto(input);
            EntrevistadoresDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Entrevistadores: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistadoresOutput update(ENTREVISTADORES_UPDATE input) {
        try {
            EntrevistadoresDTO dto = map.toDto(input);
            EntrevistadoresDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Entrevistadores: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistadoresOutput patch(ENTREVISTADORES_PATCH input) {
        try {
            EntrevistadoresDTO existing = service.findById(input.id());

            if (input.idEntrevista() != null) {
                existing.setIdEntrevista(input.idEntrevista());
            }
            if (input.idAdministrativo() != null) {
                existing.setIdAdministrativo(input.idAdministrativo());
            }

            EntrevistadoresDTO patched = service.update(input.id(), existing);
            return map.toOutput(patched);

        } catch (Exception e) {
            throw new RuntimeException("Error patching Entrevistadores: " + e.getMessage(), e);
        }
    }

    @Override
    public EntrevistadoresOutput findById(ENTREVISTADORES_FIND input) {
        try {
            EntrevistadoresDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Entrevistadores by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<EntrevistadoresOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Entrevistadores: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ENTREVISTADORES_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Entrevistadores by ID: " + e.getMessage(), e);
        }
    }

    public void deleteAllByEntrevistaId(ENTREVISTA_FIND input) {
        try {
            List<EntrevistadoresDTO> list = service.findAll().stream()
            .filter(dto -> Integer.valueOf(dto.getIdEntrevista()).equals(Integer.valueOf(input.id())))
            .toList();
            for (EntrevistadoresDTO dto : list) {
                service.deleteById(dto.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Entrevistadores by Entrevista ID: " + e.getMessage(), e);
        }
    }
}
