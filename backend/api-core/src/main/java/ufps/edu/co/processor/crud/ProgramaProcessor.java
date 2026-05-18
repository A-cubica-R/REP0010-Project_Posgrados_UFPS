package ufps.edu.co.processor.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.CohorteErrorCode;
import ufps.edu.co.maps.specific.ProgramaMap;
import ufps.edu.co.records.input.entity.ProgramaInput.*;
import ufps.edu.co.records.output.entity.ProgramaOutput;
import ufps.edu.co.rest.dto.ProgramaDTO;
import ufps.edu.co.rest.services.ProgramaService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class ProgramaProcessor implements GlobalUseCase<PROGRAMA_CREATE, PROGRAMA_UPDATE, PROGRAMA_DELETE, PROGRAMA_PATCH, PROGRAMA_FIND, ProgramaOutput> {

    @Autowired
    private ProgramaService service;

    @Autowired
    private ProgramaMap map;

    @Autowired
    private CohorteProcessor cohorteProcessor;

    @Override
    public ProgramaOutput create(PROGRAMA_CREATE input) {
        try {
            ProgramaDTO dto = map.toDto(input);
            ProgramaDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Programa: " + e.getMessage(), e);
        }
    }

    @Override
    public ProgramaOutput update(PROGRAMA_UPDATE input) {
        try {
            ProgramaDTO dto = map.toDto(input);
            ProgramaDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Programa: " + e.getMessage(), e);
        }
    }

    @Override
    public ProgramaOutput patch(PROGRAMA_PATCH input) {
        throw new UnsupportedOperationException("Patch not supported for Programa");
    }

    @Override
    public ProgramaOutput findById(PROGRAMA_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new RuntimeException("Error finding Programa: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ProgramaOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Programas: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(PROGRAMA_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Programa: " + e.getMessage(), e);
        }
    }

    public List<ProgramaOutput> findByIdFacultad(Integer idFacultad) {
        try {
            return map.toOutputList(service.findByIdFacultad(idFacultad));
        } catch (Exception e) {
            throw new RuntimeException("Error finding Programas by Facultad ID: " + e.getMessage(), e);
        }
    }

    public long countAspirantesEnProcesoEnCohorteAbierta(Integer cohorteId) {
        try {
            return cohorteProcessor.countAspirantesEnProcesoEnCohorteAbierta(cohorteId);
        } catch (Exception e) {
            throw new DomainException(CohorteErrorCode.COHORTE_NOT_FOUND, cohorteId);
        }
    }
}
