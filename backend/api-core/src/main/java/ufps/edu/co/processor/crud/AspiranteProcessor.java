package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.AspiranteMap;
import ufps.edu.co.maps.specific.EstadoMap;
import ufps.edu.co.records.input.entity.AspiranteInput.*;
import ufps.edu.co.records.output.entity.AspiranteCalificacionOutput;
import ufps.edu.co.records.output.entity.AspiranteCriteriosOutput;
import ufps.edu.co.records.output.entity.AspiranteOutput;
import ufps.edu.co.records.output.entity.CriterioFilaOutput;
import ufps.edu.co.records.output.entity.EstadoOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.CalificacioncriterioDTO;
import ufps.edu.co.rest.dto.CriterioevaluacionDTO;
import ufps.edu.co.rest.dto.PersonaDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.CalificacioncriterioService;
import ufps.edu.co.rest.services.CriterioevaluacionService;
import ufps.edu.co.usecase.GlobalUseCase;

@Service
public class AspiranteProcessor implements
        GlobalUseCase<ASPIRANTE_CREATE, ASPIRANTE_UPDATE, ASPIRANTE_DELETE, ASPIRANTE_PATCH, ASPIRANTE_FIND, AspiranteOutput> {

    @Autowired
    private AspiranteService service;

    @Autowired
    private AspiranteMap map;

    @Autowired
    private EstadoMap estadoMap;

    @Autowired
    private CriterioevaluacionService criterioevaluacionService;

    @Autowired
    private CalificacioncriterioService calificacioncriterioService;

    @Override
    public AspiranteOutput create(ASPIRANTE_CREATE input) {
        try {
            AspiranteDTO dto = map.toDto(input);
            AspiranteDTO created = service.create(dto);
            return map.toOutput(created);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Aspirante: " + e.getMessage(), e);
        }
    }

    @Override
    public AspiranteOutput update(ASPIRANTE_UPDATE input) {
        try {
            AspiranteDTO dto = map.toDto(input);
            AspiranteDTO updated = service.update(input.id(), dto);
            return map.toOutput(updated);
        } catch (Exception e) {
            throw new RuntimeException("Error updating Aspirante: " + e.getMessage(), e);
        }
    }

    @Override
    public AspiranteOutput patch(ASPIRANTE_PATCH input) {
        throw new UnsupportedOperationException("Patch operation is not supported for Aspirante");
    }

    @Override
    public AspiranteOutput findById(ASPIRANTE_FIND input) {
        try {
            AspiranteDTO dto = service.findById(input.id());
            return map.toOutput(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error finding Aspirante by ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AspiranteOutput> findAll() {
        try {
            return service.findAll().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all Aspirantes: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ASPIRANTE_DELETE input) {
        try {
            service.deleteById(input.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting Aspirante by ID: " + e.getMessage(), e);
        }
    }

    public List<AspiranteOutput> findWithDocuments() {
        try {
            return service.findWithDocuments().stream().map(map::toOutput).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding Aspirantes with documents: " + e.getMessage(), e);
        }
    }

    public long countValidados() {
        try {
            return service.countValidados();
        } catch (Exception e) {
            throw new RuntimeException("Error counting validados: " + e.getMessage(), e);
        }
    }

    public long countPorCalificar() {
        try {
            return service.countPorCalificar();
        } catch (Exception e) {
            throw new RuntimeException("Error counting por calificar: " + e.getMessage(), e);
        }
    }

    public long countCalificados() {
        try {
            return service.countCalificados();
        } catch (Exception e) {
            throw new RuntimeException("Error counting calificados: " + e.getMessage(), e);
        }
    }

    public List<AspiranteCalificacionOutput> findAllValidadosCalificacion() {
        List<AspiranteDTO> validados = service.findWithDocuments();

        Set<Integer> calificadosIds = service.findCalificados().stream()
                .map(AspiranteDTO::getId).collect(Collectors.toSet());
        Set<Integer> porCalificarIds = service.findPorCalificar().stream()
                .map(AspiranteDTO::getId).collect(Collectors.toSet());

        return validados.stream().map(aspirante -> {
            PersonaDTO persona = aspirante.getPersona();

            String nombreCompleto = persona != null
                    ? ((persona.getNombres() != null ? persona.getNombres() : "") + " "
                            + (persona.getApellidos() != null ? persona.getApellidos() : "")).trim()
                    : "";

            String estado;
            if (calificadosIds.contains(aspirante.getId())) {
                estado = "Calificado";
            } else if (porCalificarIds.contains(aspirante.getId())) {
                estado = "Por calificar";
            } else {
                estado = "En progreso";
            }

            return AspiranteCalificacionOutput.builder()
                    .id(aspirante.getId())
                    .nombreCompleto(nombreCompleto)
                    .estadoCalificacion(estado)
                    .correo(persona != null ? persona.getCorreo() : null)
                    .puntajeTotal(aspirante.getPuntuacion())
                    .build();
        }).toList();
    }

    public AspiranteCriteriosOutput findCriteriosCalificacion(ASPIRANTE_FIND input) {
        try {
            AspiranteDTO aspirante = service.findById(input.id());

            List<CriterioevaluacionDTO> criterios = criterioevaluacionService
                    .findByIdCohorte(aspirante.getIdCohorte());

            Map<Integer, BigDecimal> puntuacionPorCriterio = calificacioncriterioService
                    .findByIdAspirante(input.id()).stream()
                    .filter(c -> c.getIdCriterio() != null && c.getPuntuacion() != null)
                    .collect(Collectors.toMap(
                            CalificacioncriterioDTO::getIdCriterio,
                            CalificacioncriterioDTO::getPuntuacion,
                            (a, b) -> a));

            List<CriterioFilaOutput> filas = criterios.stream()
                    .map(c -> CriterioFilaOutput.builder()
                            .nombreCriterio(c.getNombre())
                            .puntajeObtenido(puntuacionPorCriterio.get(c.getId()))
                            .build())
                    .toList();

            return AspiranteCriteriosOutput.builder()
                    .criterios(filas)
                    .puntajeTotal(aspirante.getPuntuacion())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error finding criterios for Aspirante: " + e.getMessage(), e);
        }
    }

    public EstadoOutput findEstadoById(ASPIRANTE_FIND input) {
        try {
            AspiranteDTO dto = service.findById(input.id());
            if (dto == null || dto.getEstado() == null) {
                return null;
            }
            return estadoMap.toOutput(dto.getEstado());
        } catch (Exception e) {
            throw new RuntimeException("Error finding estado for Aspirante: " + e.getMessage(), e);
        }
    }
}
