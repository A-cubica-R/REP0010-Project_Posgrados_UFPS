package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.CriterioaceptacionErrorCode;
import ufps.edu.co.maps.specific.CriterioaceptacionMap;
import ufps.edu.co.records.input.entity.CriterioaceptacionInput.*;
import ufps.edu.co.records.output.entity.CriterioaceptacionOutput;
import ufps.edu.co.rest.dto.CriterioaceptacionDTO;
import ufps.edu.co.rest.services.CriterioaceptacionService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class CriterioaceptacionProcessor implements
        GlobalUseCase<CRITERIOACEPTACION_CREATE, CRITERIOACEPTACION_UPDATE, CRITERIOACEPTACION_DELETE, CRITERIOACEPTACION_PATCH, CRITERIOACEPTACION_FIND, CriterioaceptacionOutput> {

    @Autowired
    private CriterioaceptacionService service;

    @Autowired
    private CriterioaceptacionMap map;

    @Override
    public CriterioaceptacionOutput create(CRITERIOACEPTACION_CREATE input) {
        validatePesoSum(input.idCohorte(), input.peso(), null);
        try {
            CriterioaceptacionDTO dto = map.toDto(input);
            return map.toOutput(service.create(dto));
        } catch (Exception e) {
            throw new DomainException(CriterioaceptacionErrorCode.CRITERIOACEPTACION_NOT_FOUND, input.id());
        }
    }

    @Override
    public CriterioaceptacionOutput update(CRITERIOACEPTACION_UPDATE input) {
        validatePesoSum(input.idCohorte(), input.peso(), input.id());
        try {
            CriterioaceptacionDTO dto = map.toDto(input);
            return map.toOutput(service.update(input.id(), dto));
        } catch (Exception e) {
            throw new DomainException(CriterioaceptacionErrorCode.CRITERIOACEPTACION_NOT_FOUND, input.id());
        }
    }

    @Override
    public CriterioaceptacionOutput patch(CRITERIOACEPTACION_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Criterioaceptacion");
    }

    @Override
    public CriterioaceptacionOutput findById(CRITERIOACEPTACION_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new DomainException(CriterioaceptacionErrorCode.CRITERIOACEPTACION_NOT_FOUND, input.id());
        }
    }

    @Override
    public List<CriterioaceptacionOutput> findAll() {
        return service.findAll().stream().map(map::toOutput).toList();
    }

    @Override
    public void deleteById(CRITERIOACEPTACION_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new DomainException(CriterioaceptacionErrorCode.CRITERIOACEPTACION_NOT_FOUND, input.id());
        }
    }

    private void validatePesoSum(Integer idCohorte, BigDecimal newPeso, Integer excludeId) {
        List<CriterioaceptacionDTO> existing = service.findByIdCohorte(idCohorte);
        BigDecimal sum = existing.stream()
                .filter(dto -> !Objects.equals(dto.getId(), excludeId))
                .map(CriterioaceptacionDTO::getPeso)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (sum.add(newPeso).compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new DomainException(CriterioaceptacionErrorCode.PESO_EXCEDE_LIMITE, idCohorte);
        }
    }
}
