package ufps.edu.co.processor.crud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.PruebaErrorCode;
import ufps.edu.co.maps.specific.PruebaMap;
import ufps.edu.co.records.input.entity.PruebaInput.*;
import ufps.edu.co.records.output.entity.PruebaOutput;
import ufps.edu.co.rest.services.PruebaService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class PruebaProcessor implements
        GlobalUseCase<PRUEBA_CREATE, PRUEBA_UPDATE, PRUEBA_DELETE, PRUEBA_PATCH, PRUEBA_FIND, PruebaOutput> {

    @Autowired private PruebaService service;
    @Autowired private PruebaMap map;

    @Override
    public PruebaOutput create(PRUEBA_CREATE input) {
        return map.toOutput(service.create(map.toDto(input)));
    }

    @Override
    public PruebaOutput update(PRUEBA_UPDATE input) {
        try {
            return map.toOutput(service.update(input.id(), map.toDto(input)));
        } catch (Exception e) {
            throw new DomainException(PruebaErrorCode.PRUEBA_NOT_FOUND, input.id());
        }
    }

    @Override
    public PruebaOutput patch(PRUEBA_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Prueba");
    }

    @Override
    public PruebaOutput findById(PRUEBA_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new DomainException(PruebaErrorCode.PRUEBA_NOT_FOUND, input.id());
        }
    }

    @Override
    public List<PruebaOutput> findAll() {
        return service.findAll().stream().map(map::toOutput).toList();
    }

    @Override
    public void deleteById(PRUEBA_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new DomainException(PruebaErrorCode.PRUEBA_NOT_FOUND, input.id());
        }
    }
}
