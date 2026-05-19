package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.CohorteErrorCode;
import ufps.edu.co.maps.specific.ProgramaMap;
import ufps.edu.co.records.input.entity.OtrosvaloresInput.OTROSVALORES_CREATE;
import ufps.edu.co.records.input.entity.ProgramaInput.*;
import ufps.edu.co.records.output.entity.ProgramaOutput;
import ufps.edu.co.rest.dto.OtrosvaloresDTO;
import ufps.edu.co.rest.dto.ProgramaDTO;
import ufps.edu.co.rest.dto.SedeDTO;
import ufps.edu.co.rest.dto.TiporegistroDTO;
import ufps.edu.co.rest.services.OtrosvaloresService;
import ufps.edu.co.rest.services.ProgramaService;
import ufps.edu.co.rest.services.SedeService;
import ufps.edu.co.rest.services.TiporegistroService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class ProgramaProcessor implements
        GlobalUseCase<PROGRAMA_CREATE, PROGRAMA_UPDATE, PROGRAMA_DELETE, PROGRAMA_PATCH, PROGRAMA_FIND, ProgramaOutput> {

    @Autowired
    private ProgramaService service;

    @Autowired
    private ProgramaMap map;

    @Autowired
    private CohorteProcessor cohorteProcessor;

    @Autowired
    private TiporegistroService tiporegistroService;

    @Autowired
    private OtrosvaloresService otrosvaloresService;

    @Autowired
    private SedeService sedeService;

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

    // #region PERSONALIZADOS

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

    public ProgramaOutput createWithRelations(PROGRAMA_CREATE_WITH_RELATIONS input, Integer idFacultad) {
        try {
            ProgramaDTO dto = buildDtoFromRelations(
                    input.codigo(),
                    input.nombre(),
                    input.semestres(),
                    input.correo(),
                    input.registrosnies(),
                    input.nivelformacion(),
                    input.titulo(),
                    input.rcmineducacion(),
                    input.creditos(),
                    input.periodicidad(),
                    input.valormatricula(),
                    input.sedeNombre(),
                    input.tiporegistroTipo(),
                    input.otrosvalores(),
                    idFacultad);
            ProgramaDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Programa with relations: " + e.getMessage(), e);
        }
    }

    public ProgramaOutput updateWithRelations(PROGRAMA_UPDATE_WITH_RELATIONS input, Integer idFacultad) {
        try {
            ProgramaDTO dto = buildDtoFromRelations(
                    input.codigo(),
                    input.nombre(),
                    input.semestres(),
                    input.correo(),
                    input.registrosnies(),
                    input.nivelformacion(),
                    input.titulo(),
                    input.rcmineducacion(),
                    input.creditos(),
                    input.periodicidad(),
                    input.valormatricula(),
                    input.sedeNombre(),
                    input.tiporegistroTipo(),
                    input.otrosvalores(),
                    idFacultad);
            ProgramaDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Programa with relations: " + e.getMessage(), e);
        }
    }

    private ProgramaDTO buildDtoFromRelations(
            Integer codigo,
            String nombre,
            Integer semestres,
            String correo,
            String registrosnies,
            String nivelformacion,
            String titulo,
            String rcmineducacion,
            Integer creditos,
            String periodicidad,
            BigDecimal valormatricula,
            String sedeNombre,
            String tiporegistroTipo,
            OTROSVALORES_CREATE otrosvalores,
            Integer idFacultad) {

        if (idFacultad == null) {
            throw new RuntimeException("Id de facultad es requerido");
        }
        if (sedeNombre == null || sedeNombre.isBlank()) {
            throw new RuntimeException("Nombre de sede es requerido");
        }
        if (tiporegistroTipo == null || tiporegistroTipo.isBlank()) {
            throw new RuntimeException("Tipo de registro es requerido");
        }
        if (otrosvalores == null || otrosvalores.carnet() == null
                || otrosvalores.estampilla() == null || otrosvalores.seguro() == null) {
            throw new RuntimeException("Otros valores son requeridos");
        }

        SedeDTO sede = sedeService.findFirstByNombre(sedeNombre.trim());
        if (sede == null || sede.getId() == null) {
            throw new RuntimeException("Sede no encontrada con nombre: " + sedeNombre);
        }

        TiporegistroDTO tiporegistro = tiporegistroService.findByTipo(tiporegistroTipo.trim());
        if (tiporegistro == null || tiporegistro.getId() == null) {
            throw new RuntimeException("Tiporegistro no encontrado con tipo: " + tiporegistroTipo);
        }

        OtrosvaloresDTO otros = otrosvaloresService.findByValores(
                otrosvalores.carnet(),
                otrosvalores.estampilla(),
                otrosvalores.seguro());
        if (otros == null || otros.getId() == null) {
            throw new RuntimeException("Otros valores no encontrados para los flags enviados");
        }

        return ProgramaDTO.builder()
                .codigo(codigo)
                .nombre(nombre)
                .duracion(semestres)
                .correo(correo)
                .registrosnies(registrosnies)
                .nivelformacion(nivelformacion)
                .titulo(titulo)
                .rcmineducacion(rcmineducacion)
                .creditos(creditos)
                .periodicidad(periodicidad)
                .valormatricula(valormatricula)
                .idSede(sede.getId())
                .idFacultad(idFacultad)
                .idOtros(otros.getId())
                .idTiporegistro(tiporegistro.getId())
                .build();
    }

    // #endregion
}
