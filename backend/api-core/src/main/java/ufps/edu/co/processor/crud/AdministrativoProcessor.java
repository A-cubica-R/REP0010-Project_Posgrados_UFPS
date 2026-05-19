package ufps.edu.co.processor.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ufps.edu.co.domain.utilities.PrinterObjects;
import ufps.edu.co.maps.specific.AdministrativoMap;
import ufps.edu.co.processor.abstracts.contract.CrudProcessor;
import ufps.edu.co.records.input.entity.AdministrativoInput.*;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.ProgramaOutput;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.services.AdministrativoService;

@Service
public class AdministrativoProcessor implements
        CrudProcessor<ADMINISTRATIVO_CREATE, ADMINISTRATIVO_UPDATE, ADMINISTRATIVO_DELETE, ADMINISTRATIVO_PATCH, ADMINISTRATIVO_FIND, AdministrativoOutput> {

    @Autowired
    private AdministrativoService service;

    @Autowired
    private AdministrativoMap map;

    @Autowired
    private ProgramaProcessor programaProcessor;

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
    @Transactional(readOnly = true)
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
    public void delete(ADMINISTRATIVO_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Administrativo: " + e.getMessage(), e);
        }
    }

    // #region PERSONALIZADOS

    public List<AdministrativoOutput> findPosiblesDirectores() {
        try {
            return service.findPosiblesDirectores().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding posibles directores: " + e.getMessage(), e);
        }
    }

    public List<ProgramaOutput> findProgramasFacultad(ADMINISTRATIVO_FIND input) {
        try {
            if (input != null && input.id() != null) {
                AdministrativoDTO admin = service.findById(input.id());
                PrinterObjects.printNotorious(admin);
                if (admin != null && admin.getCargo() != null && admin.getCargo().getIdFacultad() != null) {
                    return programaProcessor.findByIdFacultad(admin.getCargo().getIdFacultad());
                }
                throw new RuntimeException("Administrativo no tiene cargo asignado o cargo no tiene facultad asociada");
            }
            throw new RuntimeException("Input inválido: id de administrativo es requerido");
        } catch (Exception e) {
            throw new RuntimeException("Error finding programas de facultad: " + e.getMessage(), e);
        }
    }

    // #endregion

}
