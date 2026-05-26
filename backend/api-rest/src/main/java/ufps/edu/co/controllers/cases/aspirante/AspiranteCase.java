package ufps.edu.co.controllers.cases.aspirante;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ufps.edu.co.processor.crud.AspiranteProcessor;
import ufps.edu.co.processor.crud.DocumentoProcessor;
import ufps.edu.co.processor.crud.EntrevistaProcessor;
import ufps.edu.co.processor.crud.PruebaProcessor;
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_CANCELAR_REQUEST;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_REQUEST_CHANGE;
import ufps.edu.co.records.input.entity.PruebaInput.PRUEBA_CANCELAR_REQUEST;
import ufps.edu.co.records.output.entity.AspiranteCriteriosOutput;
import ufps.edu.co.records.output.entity.AspiranteDocumentosOutput;
import ufps.edu.co.records.output.entity.AspiranteIdOutput;
import ufps.edu.co.records.output.entity.DocumentoAspiranteOutput;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.records.output.entity.EntrevistaResumenOutput;
import ufps.edu.co.records.output.entity.EntrevistaSimpleOutput;
import ufps.edu.co.records.output.entity.PasoProcesoOutput;
import ufps.edu.co.records.output.entity.PruebaResumenOutput;
import ufps.edu.co.records.output.entity.PruebaSimpleOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.rest.dto.DocumentopersonaDTO;
import ufps.edu.co.rest.dto.EstadoDTO;
import ufps.edu.co.rest.dto.GeneroDTO;
import ufps.edu.co.rest.dto.PersonaDTO;
import ufps.edu.co.rest.dto.TipovinculacionDTO;
import ufps.edu.co.rest.dto.UbicacionDTO;
import ufps.edu.co.rest.dto.DocumentoDTO;
import ufps.edu.co.rest.dto.EstadodocumentoDTO;
import ufps.edu.co.rest.dto.UsuarioDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.CohorteService;
import ufps.edu.co.rest.services.CapacidadexepcionalService;
import ufps.edu.co.rest.services.DocumentopersonaService;
import ufps.edu.co.rest.services.DocumentoService;
import ufps.edu.co.rest.services.EstadoService;
import ufps.edu.co.rest.services.EstadodocumentoService;
import ufps.edu.co.rest.services.GeneroService;
import ufps.edu.co.rest.services.PersonaService;
import ufps.edu.co.rest.services.TipovinculacionService;
import ufps.edu.co.rest.services.UsuarioService;
import ufps.edu.co.rest.services.UbicacionService;
import ufps.edu.co.services.S3Service;

@RestController
@RequestMapping(value = "/aspirantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class AspiranteCase {

    @Autowired
    private AspiranteProcessor processor;

    @Autowired
    private DocumentoProcessor documentoProcessor;

    @Autowired
    private AspiranteService aspiranteService;

    @Autowired
    private UsuarioService usuarioService;

    // @Autowired
    // private TipodocumentoService tipodocumentoService;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private EstadodocumentoService estadodocumentoService;

    @Autowired
    private CohorteService cohorteService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private UbicacionService ubicacionService;

    @Autowired
    private DocumentopersonaService documentopersonaService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private GeneroService generoService;

    @Autowired
    private TipovinculacionService tipovinculacionService;

    @Autowired
    private CapacidadexepcionalService capacidadexepcionalService;

    @Autowired
    private EntrevistaProcessor entrevistaProcessor;

    @Autowired
    private PruebaProcessor pruebaProcessor;

    @GetMapping("/aspirante/{idUsuario}")
    public ResponseEntity<AspiranteIdOutput> getIdAspiranteByUsuario(@PathVariable Integer idUsuario) {
        UsuarioDTO usuario = usuarioService.findById(idUsuario);
        if (usuario == null || usuario.getIdPersona() == null) {
            return ResponseEntity.notFound().build();
        }
        Integer idAspirante = aspiranteService.findIdByIdPersona(usuario.getIdPersona());
        if (idAspirante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(AspiranteIdOutput.builder().idAspirante(idAspirante).build());
    }

    @GetMapping("/{idAspirante}/criterios")
    public ResponseEntity<AspiranteCriteriosOutput> getCriteriosAspirante(@PathVariable Integer idAspirante) {
        return ResponseEntity.ok(processor.getCriteriosAspirante(idAspirante));
    }

    @GetMapping("/{idAspirante}/estado-proceso")
    public ResponseEntity<List<PasoProcesoOutput>> getEstadoProceso(@PathVariable Integer idAspirante) {
        List<PasoProcesoOutput> pasos = processor.getPasosProceso(idAspirante);
        return ResponseEntity.ok(pasos);
    }

    @GetMapping("/{idAspirante}/documentos")
    public ResponseEntity<AspiranteDocumentosOutput> getDocumentosDeAspirante(
            @PathVariable Integer idAspirante) {
        return ResponseEntity.ok(documentoProcessor.getDocumentosDeAspirante(idAspirante));
    }

    @GetMapping("/documentos")
    public ResponseEntity<List<DocumentoAspiranteOutput>> getDocumentosAspirante() {
        AspiranteDTO aspirante = resolveAspirante();
        return ResponseEntity.ok(buildDocumentosResponse(aspirante.getId()));
    }

    @PostMapping(value = "/{idAspirante}/documentos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentoAspiranteOutput> uploadDocumento(
            @PathVariable Integer idAspirante,
            @RequestParam("idRequisito") Integer idRequisito,
            @RequestParam("file") MultipartFile file) {

        // TODO:Hay que conciderar hacer la logica de los documentos desde 0, ya no hay
        // compatibilidad con el modelo anterior
        boolean isNew = false;
        // documentoService
        // .findByIdAspiranteAndIdTipodocumento(idAspirante, idRequisito)
        // .isEmpty();

        S3Service.UploadResult upload = s3Service.uploadFile(file);

        AspiranteDTO aspirante = aspiranteService.findById(idAspirante);
        CohorteDTO cohorte = cohorteService.findById(aspirante.getIdCohorte());
        EstadodocumentoDTO estadoPendiente = estadodocumentoService.findByEstado("PENDIENTE");

        if (isNew) {
            DocumentoDTO nuevo = DocumentoDTO.builder()
                    .enlaceurl(upload.enlaceurl())
                    .keyfile(upload.keyfile())
                    .fechacargue(LocalDate.now())
                    .idAspirante(idAspirante)
                    .idEstadodocumento(estadoPendiente.getId())
                    .idPlazo(cohorte.getIdPlazodocumentacion())
                    .build();
            documentoService.create(nuevo);
        } else {
            DocumentoDTO doc = DocumentoDTO.builder()
                    .enlaceurl(upload.enlaceurl())
                    .keyfile(upload.keyfile())
                    .fechacargue(LocalDate.now())
                    .idAspirante(idAspirante)
                    .idEstadodocumento(estadoPendiente.getId())
                    .estadodocumento(estadoPendiente)
                    .build();
            documentoService.update(doc.getId(), doc);
        }

        // TipodocumentoDTO tipo = null;
        // TipodocumentoDTO tipo = tipodocumentoService.findById(idRequisito);
        DocumentoDTO saved = null;
        // saved =documentoService
        //         .findByIdAspiranteAndIdTipodocumento(idAspirante, idRequisito)
        //         .orElseThrow();

        return ResponseEntity.status(isNew ? HttpStatus.CREATED : HttpStatus.OK)
                .body(toDocumentoOutput(saved));
    }

    private AspiranteDTO resolveAspirante() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer idPersona = usuarioService.findIdPersonaByNombreusuario(username);
        if (idPersona == null) {
            throw new RuntimeException("No se pudo derivar el aspirante desde el usuario autenticado");
        }
        Integer idAspirante = aspiranteService.findIdByIdPersona(idPersona);
        if (idAspirante == null) {
            throw new RuntimeException("El usuario autenticado no tiene un aspirante asignado");
        }
        return aspiranteService.findById(idAspirante);
    }

    private List<DocumentoAspiranteOutput> buildDocumentosResponse(Integer idAspirante) {
        // List<TipodocumentoDTO> tipos = tipodocumentoService.findAll();
        // Map<Integer, DocumentoDTO> docsPorTipo = documentoService.findByIdAspirante(idAspirante)
        //         .stream()
        //         .filter(d -> d.getIdTipodocumento() != null)
        //         .collect(Collectors.toMap(DocumentoDTO::getIdTipodocumento, Function.identity(), (a, b) -> a));

        // return tipos.stream().map(tipo -> {
        //     DocumentoDTO doc = docsPorTipo.get(tipo.getId());
        //     if (doc == null) {
        //         return DocumentoAspiranteOutput.builder()
        //                 .idDocumento(null)
        //                 .idRequisito(tipo.getId())
        //                 .nombre(tipo.getDescripcion())
        //                 .status("pending")
        //                 .nombreArchivo(null)
        //                 .rejectionReason(null)
        //                 .build();
        //     }
        //     return toDocumentoOutput(doc, tipo);
        // }).toList();
        // TODO Este método ya no es funcional con el nuevo modelo, debe ser reescrito
        throw new UnsupportedOperationException("No se ha implementado la lógica de documentos para el nuevo modelo");
    }

    private DocumentoAspiranteOutput toDocumentoOutput(DocumentoDTO doc) {
        return DocumentoAspiranteOutput.builder()
                .idDocumento(doc.getId())
                // .idRequisito(tipo.getId())
                // .nombre(tipo.getDescripcion())
                .status(mapStatus(doc))
                .nombreArchivo(extractNombre(doc.getKeyfile()))
                .rejectionReason(doc.getObservaciones())
                .build();
    }

    private String mapStatus(DocumentoDTO doc) {
        if (doc.getEstadodocumento() == null)
            return "pending";
        return switch (doc.getEstadodocumento().getEstado().toUpperCase()) {
            case "APROBADO" -> "approved";
            case "RECHAZADO" -> "rejected";
            default -> "pending";
        };
    }

    private String extractNombre(String keyfile) {
        if (keyfile == null)
            return null;
        int idx = keyfile.indexOf('-');
        return idx >= 0 && idx < keyfile.length() - 1 ? keyfile.substring(idx + 1) : keyfile;
    }

    @GetMapping("/{idAspirante}/entrevistas")
    public ResponseEntity<List<EntrevistaResumenOutput>> getEntrevistasByAspirante(
            @PathVariable Integer idAspirante) {
        return ResponseEntity.ok(entrevistaProcessor.findByIdAspirante(new ASPIRANTE_FIND(idAspirante)));
    }

    @PatchMapping("/entrevistas/{idEntrevista}/aceptar")
    public ResponseEntity<EntrevistaSimpleOutput> aceptarEntrevista(
            @PathVariable Integer idEntrevista) {
        EntrevistaOutput o = entrevistaProcessor.confirmInterview(new ENTREVISTA_FIND(idEntrevista));
        return ResponseEntity.ok(toSimpleEntrevista(o));
    }

    @PatchMapping(value = "/entrevistas/{idEntrevista}/solicitar-cambio", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaSimpleOutput> solicitarCambioEntrevista(
            @PathVariable Integer idEntrevista,
            @RequestBody ENTREVISTA_REQUEST_CHANGE body) {
        EntrevistaOutput o = entrevistaProcessor.requestChangeInterview(idEntrevista, body.motivocambio());
        return ResponseEntity.ok(EntrevistaSimpleOutput.builder()
                .idEntrevista(o.id())
                .idAspirante(o.idAspirante())
                .estado(o.estado() != null ? o.estado().tipo() : null)
                .motivocambio(o.motivocambio())
                .build());
    }

    @PatchMapping(value = "/entrevistas/{idEntrevista}/cancelar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntrevistaSimpleOutput> cancelarEntrevista(
            @PathVariable Integer idEntrevista,
            @RequestBody ENTREVISTA_CANCELAR_REQUEST body) {
        EntrevistaOutput o = entrevistaProcessor.cancelInterview(idEntrevista, body.motivocambio());
        return ResponseEntity.ok(EntrevistaSimpleOutput.builder()
                .idEntrevista(o.id())
                .idAspirante(o.idAspirante())
                .estado(o.estado() != null ? o.estado().tipo() : null)
                .motivocambio(o.motivocambio())
                .build());
    }

    private EntrevistaSimpleOutput toSimpleEntrevista(EntrevistaOutput o) {
        return EntrevistaSimpleOutput.builder()
                .idEntrevista(o.id())
                .idAspirante(o.idAspirante())
                .estado(o.estado() != null ? o.estado().tipo() : null)
                .motivocambio(null)
                .build();
    }

    @GetMapping("/{idAspirante}/pruebas")
    public ResponseEntity<List<PruebaResumenOutput>> getPruebasByAspirante(
            @PathVariable Integer idAspirante) {
        return ResponseEntity.ok(pruebaProcessor.findByIdAspirante(new ASPIRANTE_FIND(idAspirante)));
    }

    @PatchMapping("/pruebas/{idPrueba}/aceptar")
    public ResponseEntity<PruebaSimpleOutput> aceptarPrueba(@PathVariable Integer idPrueba) {
        return ResponseEntity.ok(pruebaProcessor.confirmarPrueba(idPrueba));
    }

    @PatchMapping(value = "/pruebas/{idPrueba}/solicitar-cambio", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PruebaSimpleOutput> solicitarCambioPrueba(
            @PathVariable Integer idPrueba,
            @RequestBody PRUEBA_CANCELAR_REQUEST body) {
        return ResponseEntity.ok(pruebaProcessor.solicitarCambioPrueba(idPrueba, body.motivocambio()));
    }

        @PatchMapping(value = "/pruebas/{idPrueba}/cancelar", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<PruebaSimpleOutput> cancelarPrueba(
            @PathVariable Integer idPrueba,
            @RequestBody PRUEBA_CANCELAR_REQUEST body) {
        return ResponseEntity.ok(pruebaProcessor.cancelarPrueba(idPrueba, body.motivocambio()));
        }

        @PostMapping(value = "/inscripciones", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<AspiranteDTO> registrarFormularioCompleto(@RequestBody InscripcionRequest body) {
        if (body == null || body.datosPersonales() == null || body.contactoResidencia() == null
            || body.poblacionEspecial() == null || body.datosLaborales() == null || body.datosAcademicos() == null) {
            throw new IllegalArgumentException("La inscripción completa requiere todos los bloques del formulario.");
        }

        Integer idVivienda = crearUbicacion(body.contactoResidencia().direccionResidencia(),
            body.contactoResidencia().idMunicipioResidencia(),
            body.contactoResidencia().zonaResidencia());
        Integer idTrabajo = crearUbicacion(body.datosLaborales().direccionTrabajo(),
            body.datosLaborales().idMunicipioTrabajo(),
            null);
        Integer idNacimiento = crearUbicacion("SIN DIRECCION DE NACIMIENTO",
            body.datosPersonales().idMunicipioNacimiento(),
            null);
        Integer idExpedicion = crearUbicacion("SIN DIRECCION DE EXPEDICION",
            body.datosPersonales().idMunicipioExpedicionDoc(),
            null);

        DocumentopersonaDTO documentopersona = DocumentopersonaDTO.builder()
            .numerodocumento(parseNumeroDocumento(body.datosPersonales().numeroDocumento()))
            .idTipodocumento(body.datosPersonales().idTipoDoc())
            .idLugarexpedicion(idExpedicion)
            .build();
        Integer idDocumentopersona = documentopersonaService.create(documentopersona).getId();

        Integer idGenero = resolveGeneroId(body.datosPersonales().idGenero());
        Integer idTipovinculacion = resolveTipovinculacionId(body);
        Integer idCohorte = body.idCohorte() != null ? body.idCohorte() : resolveDefaultCohorteId();
        EstadoDTO estadoInscrito = estadoService.findByTipoAndEntidad("INSCRITO", "aspirante");
        if (estadoInscrito == null) {
            throw new IllegalStateException("No se encontró el estado INSCRITO para aspirante.");
        }

        PersonaDTO persona = PersonaDTO.builder()
            .nombres(body.datosPersonales().nombres())
            .apellidos(body.datosPersonales().apellidos())
            .correo(body.contactoResidencia().email())
            .fechanacimiento(LocalDate.parse(body.datosPersonales().fechaNacimiento()))
            .celular(body.contactoResidencia().telefonoContacto())
            .telefono(body.contactoResidencia().telefonoContacto())
            .egresadoufps(Boolean.TRUE.equals(body.datosAcademicos().egresadoUfpsCucuta()))
            .empresa(body.datosLaborales().empresa())
            .experiencialaboral(body.datosLaborales().experienciaLaboral() != null
                ? body.datosLaborales().experienciaLaboral().stream()
                    .map(ExperienciaLaboral::experiencia)
                    .reduce((a, b) -> a + "; " + b)
                    .orElse(null)
                : null)
            .promediopregrado(body.datosAcademicos().promedioPonderadoAcumulado() != null
                ? java.math.BigDecimal.valueOf(body.datosAcademicos().promedioPonderadoAcumulado())
                : null)
            .titulopregrado(body.datosAcademicos().tituloPregrado())
            .titulosposgrados(body.datosAcademicos().titulosPostgrado())
            .idUbicacionvivienda(idVivienda)
            .idUbicacionnacimiento(idNacimiento)
            .idUbicaciontrabajo(idTrabajo)
            .idGenero(idGenero)
            .idEstadocivil(body.datosPersonales().idEstadoCivil())
            .idGrupoetnico(body.poblacionEspecial().idGrupoEtnico())
            .idPoblacionindigena(body.poblacionEspecial().idPuebloIndigena())
            .idDiscapacidad(body.poblacionEspecial().idTipoDiscapacidad())
                .idCapacidadexepcional(resolveCapacidadExcepcionalId(body.poblacionEspecial().capacidadExcepcional()))
            .idDocumentopersona(idDocumentopersona)
            .build();

        PersonaDTO savedPersona = personaService.create(persona);

        AspiranteDTO aspirante = AspiranteDTO.builder()
            .idPersona(savedPersona.getId())
            .idCohorte(idCohorte)
            .idEstado(estadoInscrito.getId())
            .idTipovinculacion(idTipovinculacion)
            .puntuacion(java.math.BigDecimal.ZERO)
            .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(aspiranteService.create(aspirante));
        }

        private Integer crearUbicacion(String direccion, Integer idMunicipio, String zonaResidencia) {
        UbicacionDTO ubicacion = UbicacionDTO.builder()
            .direccion(direccion != null && !direccion.isBlank() ? direccion : "SIN DIRECCION")
            .idMunicipio(idMunicipio)
            .zonaurbana(zonaResidencia == null ? null : "urbana".equalsIgnoreCase(zonaResidencia))
            .build();
        return ubicacionService.create(ubicacion).getId();
        }

        private Integer parseNumeroDocumento(String numeroDocumento) {
        try {
            return Integer.valueOf(numeroDocumento);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El numeroDocumento debe ser numérico para documentopersona.", ex);
        }
        }

        private Integer resolveGeneroId(Integer idGenero) {
            if (idGenero == null) {
                throw new IllegalArgumentException("idGenero es obligatorio.");
            }
            GeneroDTO genero = generoService.findById(idGenero);
            if (genero == null) {
                throw new IllegalArgumentException("No se encontró un género con id: " + idGenero);
            }
            return genero.getId();
        }

        private Integer resolveTipovinculacionId(InscripcionRequest body) {
        Integer id = body.datosAcademicos().idTipovinculacion();
        if (id != null) {
            TipovinculacionDTO t = tipovinculacionService.findById(id);
            if (t == null) {
                throw new IllegalArgumentException("No se encontró tipovinculacion con id: " + id);
            }
            return t.getId();
        }
        return tipovinculacionService.findAll().stream()
            .findFirst()
            .map(TipovinculacionDTO::getId)
            .orElseThrow(() -> new IllegalStateException("No hay tipovinculaciones registradas."));
        }

        private Integer resolveCapacidadExcepcionalId(String capacidadExcepcional) {
        if (capacidadExcepcional != null && !capacidadExcepcional.isBlank()) {
            return capacidadexepcionalService.findAll().stream()
                .filter(c -> c.getTipocapacidad() != null && c.getTipocapacidad().equalsIgnoreCase(capacidadExcepcional))
                .map(ufps.edu.co.rest.dto.CapacidadexepcionalDTO::getId)
                .findFirst()
                .orElseGet(() -> capacidadexepcionalService.findAll().stream()
                    .findFirst()
                    .map(ufps.edu.co.rest.dto.CapacidadexepcionalDTO::getId)
                    .orElseThrow(() -> new IllegalStateException("No hay capacidades excepcionales registradas.")));
        }
        return capacidadexepcionalService.findAll().stream()
            .findFirst()
            .map(ufps.edu.co.rest.dto.CapacidadexepcionalDTO::getId)
            .orElseThrow(() -> new IllegalStateException("No hay capacidades excepcionales registradas."));
        }

        private Integer resolveDefaultCohorteId() {
        return cohorteService.findAll().stream()
            .findFirst()
            .map(CohorteDTO::getId)
            .orElseThrow(() -> new IllegalStateException("No hay cohortes registradas para asignar al aspirante."));
        }

        public static record InscripcionRequest(
            DatosPersonales datosPersonales,
            ContactoResidencia contactoResidencia,
            PoblacionEspecial poblacionEspecial,
            DatosLaborales datosLaborales,
            DatosAcademicos datosAcademicos,
            Integer idCohorte) {
        }

        public static record DatosPersonales(
            String nombres,
            String apellidos,
            Integer idTipoDoc,
            String numeroDocumento,
            Integer idEstadoCivil,
            Integer idGenero,
            String fechaNacimiento,
            Integer idDeptoNacimiento,
            Integer idMunicipioNacimiento,
            String fechaExpedicionDocumento,
            Integer idDeptoExpedicionDoc,
            Integer idMunicipioExpedicionDoc) {
        }

        public static record ContactoResidencia(
            String zonaResidencia,
            Integer idDeptoResidencia,
            Integer idMunicipioResidencia,
            String direccionResidencia,
            String email,
            String telefonoContacto) {
        }

        public static record PoblacionEspecial(
            Integer idGrupoEtnico,
            Integer idPuebloIndigena,
            Boolean tieneDiscapacidad,
            Integer idTipoDiscapacidad,
            String capacidadExcepcional) {
        }

        public static record DatosLaborales(
            String empresa,
            Integer idDptoTrabajo,
            Integer idMunicipioTrabajo,
            String direccionTrabajo,
            List<ExperienciaLaboral> experienciaLaboral) {
        }

        public static record ExperienciaLaboral(String experiencia) {
        }

        public static record DatosAcademicos(
            Integer idTipovinculacion,
            String tituloPregrado,
            Double promedioPonderadoAcumulado,
            String titulosPostgrado,
            Boolean egresadoUfpsCucuta) {
        }

    }
