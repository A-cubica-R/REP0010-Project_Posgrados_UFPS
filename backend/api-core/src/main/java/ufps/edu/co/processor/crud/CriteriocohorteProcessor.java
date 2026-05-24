package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.CriteriocohorteErrorCode;
import ufps.edu.co.maps.specific.CriteriocohorteMap;
import ufps.edu.co.records.input.entity.CriteriocohorteInput.*;
import ufps.edu.co.records.output.entity.CriteriocohorteOutput;
import ufps.edu.co.rest.dto.CriteriocohorteDTO;
import ufps.edu.co.rest.services.CriteriocohorteService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class CriteriocohorteProcessor implements
        GlobalUseCase<CRITERIOCOHORTE_CREATE, CRITERIOCOHORTE_UPDATE, CRITERIOCOHORTE_DELETE, CRITERIOCOHORTE_PATCH, CRITERIOCOHORTE_FIND, CriteriocohorteOutput> {

    @Autowired
    private CriteriocohorteService service;

    @Autowired
    private CriteriocohorteMap map;

    @Override
    public CriteriocohorteOutput create(CRITERIOCOHORTE_CREATE input) {
        try {
            CriteriocohorteDTO dto = map.toDto(input);
            return map.toOutput(service.create(dto));
        } catch (Exception e) {
            throw new DomainException(CriteriocohorteErrorCode.CRITERIOCOHORTE_DUPLICADO, input);
        }
    }

    @Override
    public CriteriocohorteOutput update(CRITERIOCOHORTE_UPDATE input) {
        try {
            CriteriocohorteDTO dto = map.toDto(input);
            return map.toOutput(service.update(input.id(), dto));
        } catch (Exception e) {
            throw new DomainException(CriteriocohorteErrorCode.CRITERIOCOHORTE_NOT_FOUND, input.id());
        }
    }

    @Override
    public CriteriocohorteOutput patch(CRITERIOCOHORTE_PATCH input) {
        return null;
    }

    @Override
    public CriteriocohorteOutput findById(CRITERIOCOHORTE_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new DomainException(CriteriocohorteErrorCode.CRITERIOCOHORTE_NOT_FOUND, input.id());
        }
    }

    @Override
    public List<CriteriocohorteOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new DomainException(CriteriocohorteErrorCode.CRITERIOCOHORTE_NOT_FOUND, "findAll");
        }
    }

    @Override
    public void deleteById(CRITERIOCOHORTE_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new DomainException(CriteriocohorteErrorCode.CRITERIOCOHORTE_NOT_FOUND, input.id());
        }
    }

    public List<CriteriocohorteOutput> findByIdCohorte(Integer idCohorte) {
        return service.findByIdCohorte(idCohorte).stream().map(map::toOutput).toList();
    }

    public CriteriocohorteOutput assign(Integer idCohorte, Integer idCriterio, BigDecimal pesoSnapshot) {
        return create(CRITERIOCOHORTE_CREATE.builder()
                .idCohorte(idCohorte)
                .idCriterio(idCriterio)
                .pesoSnapshot(pesoSnapshot)
                .build());
    }

    public CriteriocohorteOutput updatePeso(Integer id, BigDecimal pesoSnapshot) {
        CriteriocohorteOutput existing = findById(new CRITERIOCOHORTE_FIND(id));
        return update(CRITERIOCOHORTE_UPDATE.builder()
                .id(id)
                .idCohorte(existing.idCohorte())
                .idCriterio(existing.idCriterio())
                .pesoSnapshot(pesoSnapshot)
                .build());
    }
}