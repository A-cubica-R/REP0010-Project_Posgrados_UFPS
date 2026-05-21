package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufps.edu.co.maps.specific.AspiranteMap;
import ufps.edu.co.maps.specific.EstadoMap;
import ufps.edu.co.records.input.entity.AspiranteInput.*;
import ufps.edu.co.records.input.entity.CohorteInput.COHORTE_DIRECTOR_CREATE;
import ufps.edu.co.records.input.entity.CohorteInput.COHORTE_DIRECTOR_UPDATE;
import ufps.edu.co.records.output.entity.AspiranteCalificacionOutput;
import ufps.edu.co.records.output.entity.AspiranteCohorteOutput;
import ufps.edu.co.records.output.entity.AspiranteCriteriosOutput;
import ufps.edu.co.records.output.entity.AspiranteOutput;
import ufps.edu.co.records.output.entity.CohorteDetalleOutput;
import ufps.edu.co.records.output.entity.CohorteListadoOutput;
import ufps.edu.co.records.output.entity.CohorteResumenOutput;
import ufps.edu.co.records.output.entity.CriterioFilaOutput;
import ufps.edu.co.records.output.entity.CriteriosCohorteOutput;
import ufps.edu.co.records.output.entity.EstadoOutput;
import ufps.edu.co.records.output.entity.PasoProcesoOutput;
import ufps.edu.co.records.output.entity.ProgramaInicioOutput;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.DocumentoDTO;
import ufps.edu.co.rest.dto.PagoDTO;
import ufps.edu.co.rest.services.DocumentoService;
import ufps.edu.co.rest.services.PagoService;
import ufps.edu.co.rest.dto.CalificacioncriterioDTO;
import ufps.edu.co.rest.dto.CriterioevaluacionDTO;
import ufps.edu.co.rest.dto.EstadoDTO;
import ufps.edu.co.rest.dto.ModalidadDTO;
import ufps.edu.co.rest.dto.PersonaDTO;
import ufps.edu.co.rest.dto.PlazoDTO;
import ufps.edu.co.rest.dto.SemestreDTO;
import ufps.edu.co.rest.dto.TipoplazoDTO;
import ufps.edu.co.rest.services.AdmitidoService;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.CalificacioncriterioService;
import ufps.edu.co.rest.services.CohorteService;
import ufps.edu.co.rest.services.CriterioevaluacionService;
import ufps.edu.co.rest.services.EstadoService;
import ufps.edu.co.rest.services.ModalidadService;
import ufps.edu.co.rest.services.PlazoService;
import ufps.edu.co.rest.services.SemestreService;
import ufps.edu.co.rest.services.TipoplazoService;
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

    @Autowired
    private PlazoService plazoService;

    @Autowired
    private SemestreService semestreService;

    @Autowired
    private ModalidadService modalidadService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private TipoplazoService tipoplazoService;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private PagoService pagoService;

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
                                    ? p.getDocumentopersona().getNumerodocumento().toString()
                                    : null;
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
                    if (a == null)
                        return null;
                    PersonaDTO p = a.getPersona();
                    String nombre = p != null
                            ? ((p.getNombres() != null ? p.getNombres() : "") + " "
                                    + (p.getApellidos() != null ? p.getApellidos() : "")).trim()
                            : "";
                    String cedula = p != null && p.getDocumentopersona() != null
                            && p.getDocumentopersona().getNumerodocumento() != null
                                    ? p.getDocumentopersona().getNumerodocumento().toString()
                                    : null;
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

    public List<PasoProcesoOutput> getPasosProceso(Integer idAspirante) {
        AspiranteDTO aspirante = service.findById(idAspirante);
        String estadoTipo = aspirante.getEstado() != null ? aspirante.getEstado().getTipo() : "";

        // Paso 1 - Inscripción: siempre completado si el aspirante existe
        PasoProcesoOutput inscripcion = PasoProcesoOutput.builder()
                .id(1).name("Inscripción").status("completado").build();

        // Paso 2 - Pago
        List<PagoDTO> pagos = pagoService.findByIdAspirante(idAspirante);
        String statusPago;
        boolean pagoAprobado = pagos.stream()
                .anyMatch(p -> p.getEstado() != null && "APROBADO".equalsIgnoreCase(p.getEstado().getTipo()));
        if (pagoAprobado) {
            statusPago = "completado";
        } else if (!pagos.isEmpty()) {
            statusPago = "En revisión";
        } else {
            statusPago = "Pendiente";
        }
        PasoProcesoOutput pago = PasoProcesoOutput.builder()
                .id(2).name("Pago").status(statusPago).build();

        // Paso 3 - Documentos
        String statusDocs;
        boolean estadoValidado = List.of("VALIDADO_POR_CALIFICAR", "VALIDADO_EN_PROGRESO", "VALIDADO_CALIFICADO")
                .contains(estadoTipo);
        if (estadoValidado) {
            statusDocs = "completado";
        } else {
            List<DocumentoDTO> docs = documentoService.findByIdAspirante(idAspirante);
            long aprobados = docs.stream()
                    .filter(d -> d.getEstadodocumento() != null
                            && "APROBADO".equalsIgnoreCase(d.getEstadodocumento().getEstado()))
                    .count();
            if (aprobados > 0) {
                statusDocs = "En revisión";
            } else if (!docs.isEmpty()) {
                statusDocs = "En revisión";
            } else {
                statusDocs = "Pendiente";
            }
        }
        PasoProcesoOutput documentos = PasoProcesoOutput.builder()
                .id(3).name("Documentos").status(statusDocs).build();

        // Paso 4 - Calificación
        String statusCalificacion;
        if ("VALIDADO_CALIFICADO".equalsIgnoreCase(estadoTipo)) {
            statusCalificacion = "completado";
        } else if ("VALIDADO_EN_PROGRESO".equalsIgnoreCase(estadoTipo)) {
            statusCalificacion = "En progreso";
        } else if ("VALIDADO_POR_CALIFICAR".equalsIgnoreCase(estadoTipo)) {
            statusCalificacion = "Pendiente";
        } else {
            statusCalificacion = "Pendiente";
        }
        PasoProcesoOutput calificacion = PasoProcesoOutput.builder()
                .id(4).name("Calificación").status(statusCalificacion).build();

        // Paso 5 - Resultado
        boolean admitido = admitidoService.findByCohorte(aspirante.getIdCohorte()).stream()
                .anyMatch(a -> idAspirante.equals(a.getIdAspirante()));
        PasoProcesoOutput resultado = PasoProcesoOutput.builder()
                .id(5).name("Resultado").status(admitido ? "completado" : "Pendiente").build();

        return List.of(inscripcion, pago, documentos, calificacion, resultado);
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
                    Integer numerodocumento = persona != null && persona.getDocumentopersona() != null
                            ? persona.getDocumentopersona().getNumerodocumento()
                            : null;
                    return AspiranteCalificacionOutput.builder()
                            .id(aspirante.getId())
                            .nombreCompleto(nombreCompleto)
                            .idEstado(aspirante.getIdEstado())
                            .correo(persona != null ? persona.getCorreo() : null)
                            .puntajeTotal(aspirante.getPuntuacion())
                            .numerodocumento(numerodocumento)
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

    public CohorteListadoOutput createCohorte(Integer programaId, COHORTE_DIRECTOR_CREATE body) {
        LocalDate fechaInicio = body.fechaInicio();
        int year = fechaInicio.getYear();
        int semNum = fechaInicio.getMonthValue() <= 6 ? 1 : 2;
        String nombre = body.nombre();

        List<TipoplazoDTO> tipoplazos = tipoplazoService.findAll();
        if (tipoplazos.isEmpty()) {
            throw new RuntimeException("No hay tipos de plazo configurados");
        }
        Integer tipoplazoId = tipoplazos.get(0).getId();

        PlazoDTO plazoDoc = plazoService.create(PlazoDTO.builder()
                .fechainicio(fechaInicio)
                .fechafin(body.fechaLimiteDocumentos())
                .idTipoplazo(tipoplazoId)
                .build());

        PlazoDTO plazoPago = plazoService.create(PlazoDTO.builder()
                .fechainicio(fechaInicio)
                .fechafin(body.fechaLimitePago())
                .idTipoplazo(tipoplazoId)
                .build());

        EstadoDTO estadoSemestre = estadoService.findAll().stream()
                .filter(e -> "semestre".equalsIgnoreCase(e.getEntidad()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No hay estado configurado para semestre"));

        LocalDate fechaFin = semNum == 1 ? LocalDate.of(year, 6, 30) : LocalDate.of(year, 12, 31);
        SemestreDTO semestre = semestreService.create(SemestreDTO.builder()
                .nombre(year + "-" + semNum)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .idEstado(estadoSemestre.getId())
                .build());

        EstadoDTO estadoCohorte = estadoService.findByTipoAndEntidad("CERRADA", "cohorte");
        if (estadoCohorte == null) {
            throw new RuntimeException("No hay estado CERRADA configurado para cohorte");
        }

        List<ModalidadDTO> modalidades = modalidadService.findAll();
        if (modalidades.isEmpty()) {
            throw new RuntimeException("No hay modalidades configuradas");
        }

        CohorteDTO cohorte = cohorteService.create(CohorteDTO.builder()
                .nombre(nombre)
                .cupos(body.cupos())
                .requiereentrevista(false)
                .requiereprueba(false)
                .idEstado(estadoCohorte.getId())
                .idSemestre(semestre.getId())
                .idModalidad(modalidades.get(0).getId())
                .idPlazodocumentacion(plazoDoc.getId())
                .idPlazoinscripcion(plazoDoc.getId())
                .idPlazopago(plazoPago.getId())
                .idPrograma(programaId)
                .build());

        return CohorteListadoOutput.builder()
                .id(cohorte.getId())
                .nombre(cohorte.getNombre())
                .activa(false)
                .inscritos(0)
                .admitidos(0)
                .cupos(cohorte.getCupos())
                .fechaLimiteDocumentos(body.fechaLimiteDocumentos())
                .fechaLimitePago(body.fechaLimitePago())
                .fechaInicio(fechaInicio)
                .build();
    }

    public List<CohorteResumenOutput> getCohortesByProgramaResumen(Integer programaId) {
        return cohorteService.findByIdPrograma(programaId).stream().map(cohorte -> {
            boolean activa = cohorte.getEstado() != null
                    && "ABIERTA".equalsIgnoreCase(cohorte.getEstado().getTipo());
            long inscritos = service.countByCohorte(cohorte.getId());
            long validados = service.countValidadosByCohorte(cohorte.getId());
            Long admitidos = activa ? null : admitidoService.countByCohorte(cohorte.getId());
            return CohorteResumenOutput.builder()
                    .id(cohorte.getId())
                    .nombre(cohorte.getNombre())
                    .activa(activa)
                    .semestre(cohorte.getSemestre() != null ? cohorte.getSemestre().getNombre() : null)
                    .cupos(cohorte.getCupos())
                    .fechaLimiteDocs(cohorte.getPlazo() != null ? cohorte.getPlazo().getFechafin() : null)
                    .fechaLimiteInscripcion(cohorte.getPlazo2() != null ? cohorte.getPlazo2().getFechafin() : null)
                    .totalInscritos(inscritos)
                    .totalValidados(validados)
                    .totalAdmitidos(admitidos)
                    .build();
        }).toList();
    }

    public List<AspiranteCohorteOutput> findByCohorteConResumen(Integer cohorteId) {
        return service.findByCohorte(cohorteId).stream().map(aspirante -> {
            PersonaDTO p = aspirante.getPersona();
            String nombre = p != null
                    ? ((p.getNombres() != null ? p.getNombres() : "") + " "
                            + (p.getApellidos() != null ? p.getApellidos() : "")).trim()
                    : "";
            String cedula = p != null && p.getDocumentopersona() != null
                    && p.getDocumentopersona().getNumerodocumento() != null
                            ? p.getDocumentopersona().getNumerodocumento().toString()
                            : null;

            List<DocumentoDTO> docs = documentoService.findByIdAspirante(aspirante.getId());
            long total = docs.size();
            long validados = docs.stream()
                    .filter(d -> d.getEstadodocumento() != null
                            && "APROBADO".equalsIgnoreCase(d.getEstadodocumento().getEstado()))
                    .count();

            String estadoGeneral;
            if (total > 0 && validados == total) {
                estadoGeneral = "validados";
            } else if (validados > 0) {
                estadoGeneral = "en progreso";
            } else {
                estadoGeneral = "pendiente";
            }

            return AspiranteCohorteOutput.builder()
                    .id(aspirante.getId())
                    .nombre(nombre)
                    .cedula(cedula)
                    .correo(p != null ? p.getCorreo() : null)
                    .documentosValidados(validados)
                    .totalDocumentos(total)
                    .estadoGeneral(estadoGeneral)
                    .build();
        }).toList();
    }

    public CohorteListadoOutput updateCohorte(Integer cohorteId, COHORTE_DIRECTOR_UPDATE body) {
        CohorteDTO cohorte = cohorteService.findById(cohorteId);
        if (cohorte == null) {
            throw new RuntimeException("Cohorte no encontrada: " + cohorteId);
        }

        if (body.cupos() != null) {
            cohorte.setCupos(body.cupos());
        }

        LocalDate fechaInicio = cohorte.getSemestre() != null ? cohorte.getSemestre().getFechaInicio() : null;
        if (body.fechaInicio() != null && cohorte.getSemestre() != null) {
            SemestreDTO semestre = cohorte.getSemestre();
            semestre.setFechaInicio(body.fechaInicio());
            semestreService.update(semestre.getId(), semestre);
            fechaInicio = body.fechaInicio();
        }

        LocalDate fechaLimiteDocumentos = cohorte.getPlazo() != null ? cohorte.getPlazo().getFechafin() : null;
        if (body.fechaLimiteDocumentos() != null && cohorte.getPlazo() != null) {
            PlazoDTO plazo = cohorte.getPlazo();
            plazo.setFechafin(body.fechaLimiteDocumentos());
            plazoService.update(plazo.getId(), plazo);
            fechaLimiteDocumentos = body.fechaLimiteDocumentos();
        }

        LocalDate fechaLimitePago = cohorte.getPlazo3() != null ? cohorte.getPlazo3().getFechafin() : null;
        if (body.fechaLimitePago() != null && cohorte.getPlazo3() != null) {
            PlazoDTO plazo3 = cohorte.getPlazo3();
            plazo3.setFechafin(body.fechaLimitePago());
            plazoService.update(plazo3.getId(), plazo3);
            fechaLimitePago = body.fechaLimitePago();
        }

        cohorteService.update(cohorteId, cohorte);

        boolean activa = cohorte.getEstado() != null && "ABIERTA".equalsIgnoreCase(cohorte.getEstado().getTipo());

        return CohorteListadoOutput.builder()
                .id(cohorteId)
                .nombre(cohorte.getNombre())
                .activa(activa)
                .inscritos(service.countByCohorte(cohorteId))
                .admitidos(admitidoService.countByCohorte(cohorteId))
                .cupos(cohorte.getCupos())
                .fechaLimiteDocumentos(fechaLimiteDocumentos)
                .fechaLimitePago(fechaLimitePago)
                .fechaInicio(fechaInicio)
                .build();
    }
}
