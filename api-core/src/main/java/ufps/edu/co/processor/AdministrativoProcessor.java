package ufps.edu.co.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.maps.specific.AdministrativoMap;
import ufps.edu.co.records.input.AdministrativoInput.*;
import ufps.edu.co.records.output.AdministrativoOutput;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.services.AdministrativoService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class AdministrativoProcessor implements GlobalUseCase<ADMINISTRATIVO_CREATE, ADMINISTRATIVO_UPDATE, ADMINISTRATIVO_DELETE, ADMINISTRATIVO_PATCH, ADMINISTRATIVO_FIND, AdministrativoOutput> {

    @Autowired
    private AdministrativoService service;

    @Autowired
    private AdministrativoMap map;

    @Override
    public AdministrativoOutput create(ADMINISTRATIVO_CREATE input) {
        try {
            AdministrativoDTO dto = map.toDto(input);
            AdministrativoDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Administrativo: " + e.getMessage(), e);
        }
    }

    @Override
    public AdministrativoOutput update(ADMINISTRATIVO_UPDATE input) {
        try {
            AdministrativoDTO dto = map.toDto(input);
            AdministrativoDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Administrativo: " + e.getMessage(), e);
        }
    }

    @Override
    public AdministrativoOutput patch(ADMINISTRATIVO_PATCH input) {
        throw new UnsupportedOperationException("Patch not supported for Administrativo");
    }

    @Override
    public AdministrativoOutput findById(ADMINISTRATIVO_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new RuntimeException("Error finding Administrativo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AdministrativoOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Administrativos: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ADMINISTRATIVO_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Administrativo: " + e.getMessage(), e);
        }
    }
}
