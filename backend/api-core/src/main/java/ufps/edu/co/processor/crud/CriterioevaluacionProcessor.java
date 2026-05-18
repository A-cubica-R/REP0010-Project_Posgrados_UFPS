package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.CriterioevaluacionErrorCode;
import ufps.edu.co.maps.specific.CriterioevaluacionMap;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.*;
import ufps.edu.co.records.output.entity.CriterioevaluacionOutput;
import ufps.edu.co.rest.dto.CriterioevaluacionDTO;
import ufps.edu.co.rest.services.CriterioevaluacionService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class CriterioevaluacionProcessor implements
        GlobalUseCase<CRITERIOEVALUACION_CREATE, CRITERIOEVALUACION_UPDATE, CRITERIOEVALUACION_DELETE, CRITERIOEVALUACION_PATCH, CRITERIOEVALUACION_FIND, CriterioevaluacionOutput> {

    @Autowired
    private CriterioevaluacionService service;

    @Autowired
    private CriterioevaluacionMap map;

    @Override
    public CriterioevaluacionOutput create(CRITERIOEVALUACION_CREATE input) {
        validatePesoSum(input.idCohorte(), input.peso(), null);
            CriterioevaluacionDTO dto = map.toDto(input);
            return map.toOutput(service.create(dto));
    }

    @Override
    public CriterioevaluacionOutput update(CRITERIOEVALUACION_UPDATE input) {
        validatePesoSum(input.idCohorte(), input.peso(), input.id());
        try {
            CriterioevaluacionDTO dto = map.toDto(input);
            return map.toOutput(service.update(input.id(), dto));
        } catch (Exception e) {
            throw new DomainException(CriterioevaluacionErrorCode.CRITERIOEVALUACION_NOT_FOUND, input.id());
        }
    }

    @Override
    public CriterioevaluacionOutput patch(CRITERIOEVALUACION_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Criterioaceptacion");
    }

    @Override
    public CriterioevaluacionOutput findById(CRITERIOEVALUACION_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new DomainException(CriterioevaluacionErrorCode.CRITERIOEVALUACION_NOT_FOUND, input.id());
        }
    }

    @Override
    public List<CriterioevaluacionOutput> findAll() {
        return service.findAll().stream().map(map::toOutput).toList();
    }

    @Override
    public void deleteById(CRITERIOEVALUACION_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new DomainException(CriterioevaluacionErrorCode.CRITERIOEVALUACION_NOT_FOUND, input.id());
        }
    }

    private void validatePesoSum(Integer idCohorte, BigDecimal newPeso, Integer excludeId) {
        List<CriterioevaluacionDTO> existing = service.findByIdCohorte(idCohorte);
        BigDecimal sum = existing.stream()
                .filter(dto -> !Objects.equals(dto.getId(), excludeId))
                .map(CriterioevaluacionDTO::getPeso)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.add(newPeso).compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new DomainException(CriterioevaluacionErrorCode.PESO_EXCEDE_LIMITE, idCohorte);
        }
    }
}
