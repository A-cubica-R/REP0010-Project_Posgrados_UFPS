package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.CriterioevaluacionErrorCode;
import ufps.edu.co.maps.specific.CriterioevaluacionMap;
import ufps.edu.co.records.input.entity.CriterioevaluacionInput.*;
import ufps.edu.co.records.output.entity.CriterioevaluacionOutput;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.rest.dto.CriterioevaluacionDTO;
import ufps.edu.co.rest.dto.EstadoDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.CalificacioncriterioService;
import ufps.edu.co.rest.services.CohorteService;
import ufps.edu.co.rest.services.CriterioevaluacionService;
import ufps.edu.co.rest.services.EstadoService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class CriterioevaluacionProcessor implements
        GlobalUseCase<CRITERIOEVALUACION_CREATE, CRITERIOEVALUACION_UPDATE, CRITERIOEVALUACION_DELETE, CRITERIOEVALUACION_PATCH, CRITERIOEVALUACION_FIND, CriterioevaluacionOutput> {

    @Autowired
    private CriterioevaluacionService service;

    @Autowired
    private CriterioevaluacionMap map;

    @Autowired
    private CohorteService cohorteService;

    @Autowired
    private CalificacioncriterioService calificacioncriterioService;

    @Autowired
    private CalificacioncriterioProcessor calificacioncriterioProcessor;

    @Autowired
    private AspiranteService aspiranteService;

    @Autowired
    private EstadoService estadoService;

    @Override
    public CriterioevaluacionOutput create(CRITERIOEVALUACION_CREATE input) {
        validatePesoSum(input.idCohorte(), input.peso(), null);
        CriterioevaluacionDTO dto = map.toDto(input);
        CriterioevaluacionOutput output = map.toOutput(service.create(dto));
        marcarAspirantesEnProgreso(input.idCohorte());
        return output;
    }

    @Override
    public CriterioevaluacionOutput update(CRITERIOEVALUACION_UPDATE input) {
        validatePesoSum(input.idCohorte(), input.peso(), input.id());
        try {
            CriterioevaluacionDTO old = service.findById(input.id());
            BigDecimal oldPeso = old != null ? old.getPeso() : null;
            CriterioevaluacionDTO dto = map.toDto(input);
            CriterioevaluacionOutput output = map.toOutput(service.update(input.id(), dto));
            if (input.peso() != null && oldPeso != null && input.peso().compareTo(oldPeso) != 0) {
                calificacioncriterioProcessor.actualizarPesoSnapshotYRecalcular(input.id(), input.peso());
            }
            return output;
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
        if (calificacioncriterioService.existsByCriterio(input.id())) {
            throw new DomainException(CriterioevaluacionErrorCode.CRITERIO_CON_ASPIRANTES_CALIFICADOS, input.id());
        }
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new DomainException(CriterioevaluacionErrorCode.CRITERIOEVALUACION_NOT_FOUND, input.id());
        }
    }

    public CriterioevaluacionOutput createForCohorte(Integer programaId, Integer cohorteId,
            CRITERIO_CREATE_BODY body) {
        validateCohortePerteneceProg(programaId, cohorteId);
        return create(new CRITERIOEVALUACION_CREATE(body.nombre(), body.descripcion(), body.peso(), cohorteId));
    }

    public CriterioevaluacionOutput updateForCohorte(Integer programaId, Integer cohorteId,
            Integer criterioId, CRITERIO_UPDATE_BODY body) {
        validateCohortePerteneceProg(programaId, cohorteId);
        validateCriterioPertenece(criterioId, cohorteId);
        return update(new CRITERIOEVALUACION_UPDATE(criterioId, body.nombre(), body.descripcion(), body.peso(), cohorteId));
    }

    public void deleteForCohorte(Integer programaId, Integer cohorteId, Integer criterioId) {
        validateCohortePerteneceProg(programaId, cohorteId);
        validateCriterioPertenece(criterioId, cohorteId);
        deleteById(new CRITERIOEVALUACION_DELETE(criterioId));
    }

    public void bulkSaveForCohorte(Integer programaId, Integer cohorteId, CRITERIO_BULK_SAVE input) {
        validateCohortePerteneceProg(programaId, cohorteId);

        List<CriterioevaluacionDTO> existing = service.findByIdCohorte(cohorteId);
        Set<Integer> existingIds = existing.stream()
                .map(CriterioevaluacionDTO::getId)
                .collect(Collectors.toSet());

        // Validate all provided ids belong to this cohorte before saving anything
        for (CRITERIO_BULK_ITEM item : input.criterios()) {
            if (item.id() != null && !existingIds.contains(item.id())) {
                throw new DomainException(CriterioevaluacionErrorCode.CRITERIO_COHORTE_MISMATCH, item.id());
            }
        }

        // Compute projected total peso
        Set<Integer> bulkIds = input.criterios().stream()
                .map(CRITERIO_BULK_ITEM::id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        BigDecimal pesoNoTocados = existing.stream()
                .filter(c -> !bulkIds.contains(c.getId()))
                .map(CriterioevaluacionDTO::getPeso)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pesoBulk = input.criterios().stream()
                .map(CRITERIO_BULK_ITEM::peso)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (pesoNoTocados.add(pesoBulk).compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new DomainException(CriterioevaluacionErrorCode.PESO_EXCEDE_LIMITE, cohorteId);
        }

        for (CRITERIO_BULK_ITEM item : input.criterios()) {
            CriterioevaluacionDTO dto = CriterioevaluacionDTO.builder()
                    .nombre(item.nombre())
                    .descripcion(item.descripcion())
                    .peso(item.peso())
                    .idCohorte(cohorteId)
                    .build();
            if (item.id() != null) {
                CriterioevaluacionDTO old = service.findById(item.id());
                BigDecimal oldPeso = old != null ? old.getPeso() : null;
                service.update(item.id(), dto);
                if (item.peso() != null && oldPeso != null && item.peso().compareTo(oldPeso) != 0) {
                    calificacioncriterioProcessor.actualizarPesoSnapshotYRecalcular(item.id(), item.peso());
                }
            } else {
                service.create(dto);
            }
        }
    }

    private void validateCohortePerteneceProg(Integer programaId, Integer cohorteId) {
        CohorteDTO cohorte = cohorteService.findById(cohorteId);
        if (cohorte == null) {
            throw new DomainException(CriterioevaluacionErrorCode.COHORTE_PROGRAMA_MISMATCH, cohorteId);
        }
        if (!programaId.equals(cohorte.getIdPrograma())) {
            throw new DomainException(CriterioevaluacionErrorCode.COHORTE_PROGRAMA_MISMATCH, cohorteId);
        }
    }

    private void validateCriterioPertenece(Integer criterioId, Integer cohorteId) {
        CriterioevaluacionDTO criterio = service.findById(criterioId);
        if (criterio == null || !cohorteId.equals(criterio.getIdCohorte())) {
            throw new DomainException(CriterioevaluacionErrorCode.CRITERIO_COHORTE_MISMATCH, criterioId);
        }
    }

    private void marcarAspirantesEnProgreso(Integer idCohorte) {
        EstadoDTO estadoEnProgreso = estadoService.findByTipoAndEntidad("VALIDADO_EN_PROGRESO", "aspirante");
        if (estadoEnProgreso == null) return;
        aspiranteService.findByCohorte(idCohorte).stream()
                .filter(a -> a.getEstado() != null
                        && "VALIDADO_CALIFICADO".equalsIgnoreCase(a.getEstado().getTipo()))
                .forEach(a -> aspiranteService.updateEstado(a.getId(), estadoEnProgreso.getId()));
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
