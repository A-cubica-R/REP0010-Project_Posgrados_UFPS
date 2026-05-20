package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.AspiranteMap;
import ufps.edu.co.maps.specific.EstadoMap;
import ufps.edu.co.records.input.entity.AspiranteInput.*;
import ufps.edu.co.records.output.entity.AspiranteCalificacionOutput;
import ufps.edu.co.records.output.entity.AspiranteCriteriosOutput;
import ufps.edu.co.records.output.entity.AspiranteOutput;
import ufps.edu.co.records.output.entity.CohorteDetalleOutput;
import ufps.edu.co.records.output.entity.CohorteListadoOutput;
import ufps.edu.co.records.output.entity.CriterioFilaOutput;
import ufps.edu.co.records.output.entity.CriteriosCohorteOutput;
import ufps.edu.co.records.output.entity.EstadoOutput;
import ufps.edu.co.records.output.entity.ProgramaInicioOutput;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.CalificacioncriterioDTO;
import ufps.edu.co.rest.dto.CriterioevaluacionDTO;
import ufps.edu.co.rest.dto.PersonaDTO;
import ufps.edu.co.rest.services.AdmitidoService;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.CalificacioncriterioService;
import ufps.edu.co.rest.services.CohorteService;
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

    @Autowired
    private CohorteService cohorteService;

    @Autowired
    private AdmitidoService admitidoService;

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

        return validados.stream().map(aspirante -> {
            PersonaDTO persona = aspirante.getPersona();

            String nombreCompleto = persona != null
                    ? ((persona.getNombres() != null ? persona.getNombres() : "") + " "
                            + (persona.getApellidos() != null ? persona.getApellidos() : "")).trim()
                    : "";

            return AspiranteCalificacionOutput.builder()
                    .id(aspirante.getId())
                    .nombreCompleto(nombreCompleto)
                    .idEstado(aspirante.getIdEstado())
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
                            .id(c.getId())
                            .nombreCriterio(c.getNombre())
                            .peso(c.getPeso())
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

    public CriteriosCohorteOutput getCriteriosByPrograma(Integer programaId) {
        CohorteDTO cohorte = cohorteService.findActiveByIdPrograma(programaId);
        if (cohorte == null) {
            throw new RuntimeException("No hay cohorte activa para el programa: " + programaId);
        }
        boolean activa = cohorte.getEstado() != null
                && "ABIERTA".equalsIgnoreCase(cohorte.getEstado().getTipo());
        List<CriteriosCohorteOutput.CriterioInfo> criterios = criterioevaluacionService
                .findByIdCohorte(cohorte.getId()).stream()
                .map(c -> CriteriosCohorteOutput.CriterioInfo.builder()
                        .id(c.getId())
                        .nombre(c.getNombre())
                        .descripcion(c.getDescripcion())
                        .peso(c.getPeso())
                        .build())
                .toList();
        return CriteriosCohorteOutput.builder()
                .cohorteActual(CriteriosCohorteOutput.CohorteInfo.builder()
                        .id(cohorte.getId())
                        .nombre(cohorte.getNombre())
                        .activa(activa)
                        .build())
                .criterios(criterios)
                .build();
    }

    public List<CohorteListadoOutput> getCohortesByPrograma(Integer programaId) {
        return cohorteService.findByIdPrograma(programaId).stream().map(cohorte -> {
            boolean activa = cohorte.getEstado() != null
                    && "ABIERTA".equalsIgnoreCase(cohorte.getEstado().getTipo());
            long inscritos = service.countByCohorte(cohorte.getId());
            long admitidos = admitidoService.countByCohorte(cohorte.getId());
            return CohorteListadoOutput.builder()
                    .id(cohorte.getId())
                    .nombre(cohorte.getNombre())
                    .activa(activa)
                    .inscritos(inscritos)
                    .admitidos(admitidos)
                    .cupos(cohorte.getCupos())
                    .fechaLimiteDocumentos(cohorte.getPlazo() != null ? cohorte.getPlazo().getFechafin() : null)
                    .fechaLimitePago(cohorte.getPlazo3() != null ? cohorte.getPlazo3().getFechafin() : null)
                    .fechaInicio(cohorte.getSemestre() != null ? cohorte.getSemestre().getFechaInicio() : null)
                    .build();
        }).toList();
    }

    public ProgramaInicioOutput getProgramaInicio(Integer programaId) {
        CohorteDTO cohorte = cohorteService.findActiveByIdPrograma(programaId);
        if (cohorte == null) {
            throw new RuntimeException("No hay cohorte activa para el programa: " + programaId);
        }

        long totalInscritos = service.countByCohorte(cohorte.getId());
        long validados = service.countValidadosByCohorte(cohorte.getId());
        long calificados = service.countCalificadosByCohorte(cohorte.getId());

        return ProgramaInicioOutput.builder()
                .cohorteActual(ProgramaInicioOutput.CohorteResumen.builder()
                        .id(cohorte.getId())
                        .nombre(cohorte.getNombre())
                        .activa(true)
                        .fechaLimiteDocumentos(cohorte.getPlazo() != null ? cohorte.getPlazo().getFechafin() : null)
                        .fechaLimitePago(cohorte.getPlazo3() != null ? cohorte.getPlazo3().getFechafin() : null)
                        .build())
                .validacion(ProgramaInicioOutput.ValidacionResumen.builder()
                        .totalInscritos(totalInscritos)
                        .aspirantesValidados(validados)
                        .build())
                .calificacion(ProgramaInicioOutput.CalificacionResumen.builder()
                        .totalValidados(validados)
                        .aspirantesCalificados(calificados)
                        .build())
                .build();
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

    public CohorteDetalleOutput getCohorteDetalle(Integer cohorteId) {
        CohorteDTO cohorte = cohorteService.findById(cohorteId);
        if (cohorte == null) {
            throw new RuntimeException("Cohorte no encontrada: " + cohorteId);
        }
        boolean activa = cohorte.getEstado() != null
                && "ABIERTA".equalsIgnoreCase(cohorte.getEstado().getTipo());

        List<CohorteDetalleOutput.CriterioInfo> criterios = criterioevaluacionService
                .findByIdCohorte(cohorteId).stream()
                .map(c -> CohorteDetalleOutput.CriterioInfo.builder()
                        .nombre(c.getNombre())
                        .peso(c.getPeso())
                        .build())
                .toList();

        List<AspiranteDTO> aspirantes = service.findByCohorte(cohorteId);

        List<CohorteDetalleOutput.AspiranteInfo> inscritosData = aspirantes.stream()
                .map(a -> {
                    PersonaDTO p = a.getPersona();
                    String nombre = p != null
                            ? ((p.getNombres() != null ? p.getNombres() : "") + " "
                                    + (p.getApellidos() != null ? p.getApellidos() : "")).trim()
                            : "";
                    String cedula = p != null && p.getDocumentopersona() != null
                            && p.getDocumentopersona().getNumerodocumento() != null
                            ? p.getDocumentopersona().getNumerodocumento().toString() : null;
                    return CohorteDetalleOutput.AspiranteInfo.builder()
                            .id(a.getId())
                            .nombre(nombre)
                            .cedula(cedula)
                            .correo(p != null ? p.getCorreo() : null)
                            .build();
                }).toList();

        List<CohorteDetalleOutput.AspiranteInfo> admitidosData = admitidoService
                .findByCohorte(cohorteId).stream()
                .map(admitido -> {
                    AspiranteDTO a = admitido.getAspirante();
                    if (a == null) return null;
                    PersonaDTO p = a.getPersona();
                    String nombre = p != null
                            ? ((p.getNombres() != null ? p.getNombres() : "") + " "
                                    + (p.getApellidos() != null ? p.getApellidos() : "")).trim()
                            : "";
                    String cedula = p != null && p.getDocumentopersona() != null
                            && p.getDocumentopersona().getNumerodocumento() != null
                            ? p.getDocumentopersona().getNumerodocumento().toString() : null;
                    return CohorteDetalleOutput.AspiranteInfo.builder()
                            .id(a.getId())
                            .nombre(nombre)
                            .cedula(cedula)
                            .correo(p != null ? p.getCorreo() : null)
                            .build();
                })
                .filter(java.util.Objects::nonNull)
                .toList();

        return CohorteDetalleOutput.builder()
                .id(cohorte.getId())
                .nombre(cohorte.getNombre())
                .activa(activa)
                .inscritos(aspirantes.size())
                .admitidos(admitidosData.size())
                .cupos(cohorte.getCupos())
                .fechaLimiteDocumentos(cohorte.getPlazo() != null ? cohorte.getPlazo().getFechafin() : null)
                .fechaLimitePago(cohorte.getPlazo3() != null ? cohorte.getPlazo3().getFechafin() : null)
                .fechaInicio(cohorte.getSemestre() != null ? cohorte.getSemestre().getFechaInicio() : null)
                .criterios(criterios)
                .inscritosData(inscritosData)
                .admitidosData(admitidosData)
                .build();
    }

    public List<AspiranteOutput> findPazYSalvoByCohorte(Integer cohorteId) {
        try {
            return service.findByCohorte(cohorteId).stream()
                    .filter(a -> a.getEstado() != null
                            && "PAZ Y SALVO".equalsIgnoreCase(a.getEstado().getTipo()))
                    .map(map::toOutput)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding aspirantes PAZ Y SALVO: " + e.getMessage(), e);
        }
    }

    private List<AspiranteDTO> findAspirantesByCohorteActiva(Integer programaId) {
        CohorteDTO cohorte = cohorteService.findActiveByIdPrograma(programaId);
        if (cohorte == null) {
            throw new RuntimeException("No hay cohorte activa para el programa: " + programaId);
        }
        return service.findByCohorte(cohorte.getId());
    }

    public List<AspiranteCalificacionOutput> findAllValidadosCalificacion(Integer programaId) {
        List<String> estados = List.of("VALIDADO_POR_CALIFICAR", "VALIDADO_EN_PROGRESO", "VALIDADO_CALIFICADO");
        return findAspirantesByCohorteActiva(programaId).stream()
                .filter(a -> a.getEstado() != null && estados.contains(a.getEstado().getTipo()))
                .map(aspirante -> {
                    PersonaDTO persona = aspirante.getPersona();
                    String nombreCompleto = persona != null
                            ? ((persona.getNombres() != null ? persona.getNombres() : "") + " "
                                    + (persona.getApellidos() != null ? persona.getApellidos() : "")).trim()
                            : "";
                    return AspiranteCalificacionOutput.builder()
                            .id(aspirante.getId())
                            .nombreCompleto(nombreCompleto)
                            .idEstado(aspirante.getIdEstado())
                            .correo(persona != null ? persona.getCorreo() : null)
                            .puntajeTotal(aspirante.getPuntuacion())
                            .build();
                }).toList();
    }

    public long countValidados(Integer programaId) {
        List<String> estados = List.of("VALIDADO_POR_CALIFICAR", "VALIDADO_EN_PROGRESO", "VALIDADO_CALIFICADO");
        return findAspirantesByCohorteActiva(programaId).stream()
                .filter(a -> a.getEstado() != null && estados.contains(a.getEstado().getTipo()))
                .count();
    }

    public long countPorCalificar(Integer programaId) {
        List<String> estados = List.of("VALIDADO_POR_CALIFICAR", "VALIDADO_EN_PROGRESO");
        return findAspirantesByCohorteActiva(programaId).stream()
                .filter(a -> a.getEstado() != null && estados.contains(a.getEstado().getTipo()))
                .count();
    }

    public long countCalificados(Integer programaId) {
        return findAspirantesByCohorteActiva(programaId).stream()
                .filter(a -> a.getEstado() != null
                        && "VALIDADO_CALIFICADO".equalsIgnoreCase(a.getEstado().getTipo()))
                .count();
    }
}
