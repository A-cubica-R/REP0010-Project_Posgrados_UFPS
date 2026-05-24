package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.CalificacioncriterioErrorCode;
import ufps.edu.co.maps.specific.CalificacioncriterioMap;
import ufps.edu.co.records.input.entity.CalificacioncriterioInput.*;
import ufps.edu.co.records.output.entity.CalificacionCriterioSimpleOutput;
import ufps.edu.co.records.output.entity.CalificacioncriterioOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.CalificacioncriterioDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.CalificacioncriterioService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class CalificacioncriterioProcessor implements
        GlobalUseCase<CALIFICACIONCRITERIO_CREATE, CALIFICACIONCRITERIO_UPDATE, CALIFICACIONCRITERIO_DELETE, CALIFICACIONCRITERIO_PATCH, CALIFICACIONCRITERIO_FIND, CalificacioncriterioOutput> {

    private static final Logger logger = LoggerFactory.getLogger(CalificacioncriterioProcessor.class);

    @Autowired
    private CalificacioncriterioService service;

    @Autowired
    private CalificacioncriterioMap map;

    @Autowired
    private AspiranteService aspiranteService;

    @Override
    public CalificacioncriterioOutput create(CALIFICACIONCRITERIO_CREATE input) {
        if (service.existsByAspiranteAndCriterio(input.idAspirante(), input.idCriterio())) {
            throw new DomainException(CalificacioncriterioErrorCode.CALIFICACIONCRITERIO_ALREADY_EXISTS,
                    "aspirante=" + input.idAspirante() + ", criterio=" + input.idCriterio());
        }
        try {
            CalificacioncriterioDTO dto = map.toDto(input);
            CalificacioncriterioOutput output = map.toOutput(service.create(dto));
            recalcularPuntuacionAspirante(input.idAspirante());
            actualizarEstadoCalificacion(input.idAspirante());
            return output;
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error creating Calificacioncriterio: " + e.getMessage(), e);
        }
    }

    @Override
    public CalificacioncriterioOutput update(CALIFICACIONCRITERIO_UPDATE input) {
        try {
            CalificacioncriterioDTO dto = map.toDto(input);
            CalificacioncriterioOutput output = map.toOutput(service.update(input.id(), dto));
            recalcularPuntuacionAspirante(input.idAspirante());
            actualizarEstadoCalificacion(input.idAspirante());
            return output;
        } catch (Exception e) {
            throw new DomainException(CalificacioncriterioErrorCode.CALIFICACIONCRITERIO_NOT_FOUND, input.id());
        }
    }

    @Override
    public CalificacioncriterioOutput patch(CALIFICACIONCRITERIO_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Calificacioncriterio");
    }

    @Override
    public CalificacioncriterioOutput findById(CALIFICACIONCRITERIO_FIND input) {
        try {
            return map.toOutput(service.findById(input.id()));
        } catch (Exception e) {
            throw new DomainException(CalificacioncriterioErrorCode.CALIFICACIONCRITERIO_NOT_FOUND, input.id());
        }
    }

    @Override
    public List<CalificacioncriterioOutput> findAll() {
        return service.findAll().stream().map(map::toOutput).toList();
    }

    @Override
    public void deleteById(CALIFICACIONCRITERIO_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new DomainException(CalificacioncriterioErrorCode.CALIFICACIONCRITERIO_NOT_FOUND, input.id());
        }
    }

    public List<CalificacioncriterioOutput> findByIdAspirante(Integer idAspirante) {
        return service.findByIdAspirante(idAspirante).stream().map(map::toOutput).toList();
    }

    public List<CalificacioncriterioOutput> findByIdCriterio(Integer idCriterio) {
        return service.findByIdCriterio(idCriterio).stream().map(map::toOutput).toList();
    }

    public CalificacionCriterioSimpleOutput calificarCriterio(Integer idAspirante, Integer idCriterio,
            BigDecimal puntaje) {
        CalificacioncriterioOutput result = service.findByIdAspiranteAndIdCriterio(idAspirante, idCriterio)
                .map(existing -> {
                    CALIFICACIONCRITERIO_UPDATE updateInput = new CALIFICACIONCRITERIO_UPDATE(
                            existing.getId(), idAspirante, idCriterio, puntaje, null);
                    return update(updateInput);
                })
                .orElseGet(() -> {
                    CALIFICACIONCRITERIO_CREATE createInput = new CALIFICACIONCRITERIO_CREATE(
                            idAspirante, idCriterio, puntaje, null);
                    return create(createInput);
                });
        AspiranteDTO aspirante = aspiranteService.findById(idAspirante);
        return CalificacionCriterioSimpleOutput.builder()
                .idAspirante(result.idAspirante())
                .idCriterio(result.idCriteriocohorte())
                .puntajeObtenido(result.puntuacion())
                .puntajeTotal(aspirante.getPuntuacion())
                .build();
    }

    private void actualizarEstadoCalificacion(Integer idAspirante) {
        // TODO: HAY QUE HACER ESTE MÉTODO DE NUEVO
        logger.warn("No se pudo actualizar estado de calificación del aspirante {}: {}", idAspirante);
    }

    public void actualizarPesoSnapshotYRecalcular(Integer idCriterio, BigDecimal newPeso) {
        List<CalificacioncriterioDTO> calificaciones = service.findByIdCriterio(idCriterio);
        calificaciones.forEach(cal -> {
            service.update(cal.getId(), cal);
        });
        calificaciones.stream()
                .map(CalificacioncriterioDTO::getIdAspirante)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .forEach(this::recalcularPuntuacionAspirante);
    }

    // Recalcula aspirante.puntuacion = sum(puntuacion_i * pesoSnapshot_i / 100)
    private void recalcularPuntuacionAspirante(Integer idAspirante) {
        List<CalificacioncriterioDTO> calificaciones = service.findByIdAspirante(idAspirante);
        BigDecimal total = calificaciones.stream()
                .filter(c -> c.getPuntuacion() != null)
                .map(c -> c.getPuntuacion()
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        AspiranteDTO aspirante = aspiranteService.findById(idAspirante);
        aspirante.setPuntuacion(total);
        aspiranteService.update(idAspirante, aspirante);
    }
}
