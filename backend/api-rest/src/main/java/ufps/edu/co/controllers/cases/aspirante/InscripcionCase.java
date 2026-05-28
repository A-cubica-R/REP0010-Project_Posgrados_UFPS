package ufps.edu.co.controllers.cases.aspirante;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import ufps.edu.co.processor.crud.*;
import ufps.edu.co.records.output.entity.*;
import ufps.edu.co.rest.dto.*;
import ufps.edu.co.rest.services.*;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.processor.crud.ProgramaProcessor;
import ufps.edu.co.records.output.entity.ProgramaListadoOutput;
import ufps.edu.co.records.output.entity.ProgramaOutput;
import ufps.edu.co.records.output.entity.RequisitoDocumentoOutput;
// import ufps.edu.co.rest.services.TipodocumentoService;

@RestController
@RequestMapping(value = "/inscripciones", produces = MediaType.APPLICATION_JSON_VALUE)
public class InscripcionCase {

        @Autowired
        private CohorteService cohorteService;

        @Autowired
        private EstadocivilService estadocivilService;

        @Autowired
        private GeneroProcessor generoProcessor;

        @Autowired
        private TipodocumentopersonaProcessor tipodocumentopersonaProcessor;

        @Autowired
        private PaisProcessor paisProcessor;

        @Autowired
        private DepartamentoService departamentoService;

        @Autowired
        private MunicipioService municipioService;

        @Autowired
        private CapacidadexepcionalService capacidadexepcionalService;

        @Autowired
        private DiscapacidadService discapacidadService;

        @Autowired
        private TipovinculacionService tipovinculacionService;

        @Autowired
        private GrupoetnicoService grupoetnicoService;

        @Autowired
        private PoblacionindigenaService poblacionindigenaService;

        @Autowired
        private UbicacionService ubicacionService;

        @Autowired
        private DocumentopersonaService documentopersonaService;

        @Autowired
        private PersonaService personaService;

        @Autowired
        private AspiranteService aspiranteService;

        @Autowired
        private EstadoService estadoService;

        @Autowired
        private ClaveService claveService;

        @Autowired
        private UsuarioService usuarioService;

        @Autowired
        private PagoProcessor pagoProcessor;

        // ─── Records de petición ────────────────────────────────────────────────

        public record ExperienciaLaboralItem(
                        String empresa,
                        String cargo,
                        LocalDate fechaInicio,
                        LocalDate fechaFin) {
        }

        public record UbicacionNacimientoRequest(
                        Integer idDeptoNacimiento,
                        Integer idMunicipioNacimiento) {
        }

        public record UbicacionTrabajoRequest(
                        Integer idDptoTrabajo,
                        Integer idMunicipioTrabajo,
                        String direccionTrabajo) {
        }

        public record UbicacionResidenciaRequest(
                        String zonaResidencia,
                        Integer idDeptoResidencia,
                        Integer idMunicipioResidencia,
                        String direccionResidencia) {
        }

        public record FormularioInscripcionRequest(
                        String nombres,
                        String apellidos,
                        Integer idTipoDoc,
                        String numeroDocumento,
                        Integer idEstadoCivil,
                        Integer idGenero,
                        LocalDate fechaNacimiento,
                        LocalDate fechaExpedicionDocumento,
                        Integer idDeptoExpedicionDoc,
                        Integer idMunicipioExpedicionDoc,
                        String titulosPostgrado,
                        String tituloPregrado,
                        String email,
                        String telefonoContacto,
                        BigDecimal promedioPonderadoAcumulado,
                        Integer idGrupoEtnico,
                        Integer idPuebloIndigena,
                        String capacidadExcepcional,
                        Boolean egresadoUfpsCucuta,
                        List<ExperienciaLaboralItem> experienciaLaboral,
                        Integer idDiscapacidad,
                        UbicacionNacimientoRequest ubicacionNacimiento,
                        UbicacionTrabajoRequest ubicacionTrabajo,
                        UbicacionResidenciaRequest ubicacionResidencia,
                        Integer idTipoVinculacion,
                        Integer idCohorte) {
        }

        public record FormularioInscripcionOutput(Integer idPersona, Integer idAspirante) {
        }

        public record RegistrarUsuarioRequest(Integer idPersona, String usuario, String contrasena) {
        }

        public record RegistrarUsuarioOutput(Integer idUsuario, String nombreusuario, Integer idPersona) {
        }
        // ─── Endpoint 14: Registrar Formulario Completo ─────────────────────────

        @PostMapping(value = "/formulario", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<FormularioInscripcionOutput> registrarFormulario(
                        @RequestBody FormularioInscripcionRequest body) {

                // 1. Validación del promedio ponderado (rango 0.0 – 5.0)
                if (body.promedioPonderadoAcumulado() != null) {
                        BigDecimal promedio = body.promedioPonderadoAcumulado();
                        if (promedio.compareTo(BigDecimal.ZERO) < 0 || promedio.compareTo(new BigDecimal("5.0")) > 0) {
                                throw new RuntimeException(
                                                "El promedio ponderado acumulado debe estar entre 0.0 y 5.0");
                        }
                }

                // 2. Serialización de la experiencia laboral (array → JSON string)
                String experienciaLaboralJson = serializarExperiencia(body.experienciaLaboral());

                // 3. Resolución de capacidad excepcional por nombre
                CapacidadexepcionalDTO capacidad = capacidadexepcionalService.findByNombre(body.capacidadExcepcional());
                if (capacidad == null) {
                        throw new RuntimeException(
                                        "Capacidad excepcional no encontrada: " + body.capacidadExcepcional());
                }

                // 4. Ubicación del lugar de expedición del documento
                UbicacionDTO ubicExpedicion = ubicacionService.create(
                                UbicacionDTO.builder()
                                                .idMunicipio(body.idMunicipioExpedicionDoc())
                                                .direccion("")
                                                .build());

                // 5. Documento de identidad de la persona
                DocumentopersonaDTO docPersona = documentopersonaService.create(
                                DocumentopersonaDTO.builder()
                                                .numerodocumento(Integer.parseInt(body.numeroDocumento()))
                                                .idTipodocumento(body.idTipoDoc())
                                                .idLugarexpedicion(ubicExpedicion.getId())
                                                .build());

                // 6. Ubicación de nacimiento
                UbicacionDTO ubicNacimiento = ubicacionService.create(
                                UbicacionDTO.builder()
                                                .idMunicipio(body.ubicacionNacimiento().idMunicipioNacimiento())
                                                .direccion("")
                                                .build());

                // 7. Ubicación de trabajo (opcional)
                Integer idUbicacionTrabajo = null;
                if (body.ubicacionTrabajo() != null) {
                        String dirTrabajo = body.ubicacionTrabajo().direccionTrabajo() != null
                                        ? body.ubicacionTrabajo().direccionTrabajo()
                                        : "";
                        UbicacionDTO ubicTrabajo = ubicacionService.create(
                                        UbicacionDTO.builder()
                                                        .idMunicipio(body.ubicacionTrabajo().idMunicipioTrabajo())
                                                        .direccion(dirTrabajo)
                                                        .build());
                        idUbicacionTrabajo = ubicTrabajo.getId();
                }

                // 8. Ubicación de residencia
                boolean esUrbana = "Urbana".equalsIgnoreCase(body.ubicacionResidencia().zonaResidencia());
                UbicacionDTO ubicResidencia = ubicacionService.create(
                                UbicacionDTO.builder()
                                                .idMunicipio(body.ubicacionResidencia().idMunicipioResidencia())
                                                .direccion(body.ubicacionResidencia().direccionResidencia())
                                                .zonaurbana(esUrbana)
                                                .build());

                // 9. Persona
                PersonaDTO persona = personaService.create(
                                PersonaDTO.builder()
                                                .nombres(body.nombres())
                                                .apellidos(body.apellidos())
                                                .correo(body.email())
                                                .celular(body.telefonoContacto())
                                                .egresadoufps(body.egresadoUfpsCucuta())
                                                .experiencialaboral(experienciaLaboralJson)
                                                .fechanacimiento(body.fechaNacimiento())
                                                .promediopregrado(body.promedioPonderadoAcumulado())
                                                .titulopregrado(body.tituloPregrado())
                                                .titulosposgrados(body.titulosPostgrado())
                                                .idDocumentopersona(docPersona.getId())
                                                .idCapacidadexepcional(capacidad.getId())
                                                .idDiscapacidad(body.idDiscapacidad())
                                                .idEstadocivil(body.idEstadoCivil())
                                                .idGenero(body.idGenero())
                                                .idGrupoetnico(body.idGrupoEtnico())
                                                .idPoblacionindigena(body.idPuebloIndigena())
                                                .idUbicacionnacimiento(ubicNacimiento.getId())
                                                .idUbicaciontrabajo(idUbicacionTrabajo)
                                                .idUbicacionvivienda(ubicResidencia.getId())
                                                .build());

                // 10. Estado inicial del aspirante
                EstadoDTO estado = estadoService.findByTipoAndEntidad("INSCRITO", "ASPIRANTE");
                if (estado == null) {
                        throw new RuntimeException(
                                        "Estado inicial 'INSCRITO' para ASPIRANTE no encontrado en la base de datos");
                }

                // 11. Aspirante
                AspiranteDTO aspirante = aspiranteService.create(
                                AspiranteDTO.builder()
                                                .idPersona(persona.getId())
                                                .idCohorte(body.idCohorte())
                                                .idEstado(estado.getId())
                                                .idTipovinculacion(body.idTipoVinculacion())
                                                .build());

                // AQUI ES DONDE SE CREAN LOS PAGOS EN ESTADO 'PENDIENTE' DEL ASPIRANTE
                pagoProcessor.ensureInitialPaymentsForAspirante(aspirante.getId());

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new FormularioInscripcionOutput(persona.getId(), aspirante.getId()));
        }

        // ─── Endpoint 15: Registrar Usuario del Aspirante ───────────────────────

        @PostMapping(value = "/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RegistrarUsuarioOutput> registrarUsuario(
                        @RequestBody RegistrarUsuarioRequest body) {

                ClaveDTO clave = claveService.create(ClaveDTO.builder()
                                .valor(body.contrasena())
                                .build());

                UsuarioDTO usuario = usuarioService.create(UsuarioDTO.builder()
                                .idPersona(body.idPersona())
                                .idClave(clave.getId())
                                .nombreusuario(body.usuario())
                                .build());

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new RegistrarUsuarioOutput(usuario.getId(), usuario.getNombreusuario(),
                                                usuario.getIdPersona()));
        }

        // ─── Endpoints GET existentes ────────────────────────────────────────────

        @GetMapping("/tipos-vinculacion")
        public ResponseEntity<List<TipovinculacionOutput>> getTiposVinculacion() {
                List<TipovinculacionOutput> tipos = tipovinculacionService.findAll().stream()
                                .map(dto -> TipovinculacionOutput.builder()
                                                .id(dto.getId())
                                                .nombre(dto.getTipo())
                                                .build())
                                .toList();
                return ResponseEntity.ok(tipos);
        }

        @GetMapping("/discapacidades")
        public ResponseEntity<List<DiscapacidadOutput>> getDiscapacidades() {
                List<DiscapacidadOutput> discapacidades = discapacidadService.findAll().stream()
                                .map(dto -> DiscapacidadOutput.builder()
                                                .id(dto.getId())
                                                .nombre(dto.getTipodiscapacidad())
                                                .build())
                                .toList();
                return ResponseEntity.ok(discapacidades);
        }

        @GetMapping("/capacidades-excepcionales")
        public ResponseEntity<List<CapacidadexepcionalOutput>> getCapacidadesExcepcionales() {
                List<CapacidadexepcionalOutput> capacidades = capacidadexepcionalService.findAll().stream()
                                .map(dto -> CapacidadexepcionalOutput.builder()
                                                .id(dto.getId())
                                                .nombre(dto.getTipocapacidad())
                                                .build())
                                .toList();
                return ResponseEntity.ok(capacidades);
        }

        @GetMapping("/pueblos-indigenas")
        public ResponseEntity<List<PoblacionindigenaOutput>> getPueblosIndigenas() {
                List<PoblacionindigenaOutput> pueblos = poblacionindigenaService.findAll().stream()
                                .map(dto -> PoblacionindigenaOutput.builder()
                                                .id(dto.getId())
                                                .nombre(dto.getPoblacion())
                                                .build())
                                .toList();
                return ResponseEntity.ok(pueblos);
        }

        @GetMapping("/grupos-etnicos")
        public ResponseEntity<List<GrupoetnicoOutput>> getGruposEtnicos() {
                List<GrupoetnicoOutput> grupos = grupoetnicoService.findAll().stream()
                                .map(dto -> GrupoetnicoOutput.builder()
                                                .id(dto.getId())
                                                .nombre(dto.getGrupo())
                                                .build())
                                .toList();
                return ResponseEntity.ok(grupos);
        }

        @GetMapping("/zonas-residencia")
        public ResponseEntity<List<Map<String, Object>>> getZonasResidencia() {
                return ResponseEntity.ok(List.of(
                                Map.of("id", 1, "nombre", "Urbana"),
                                Map.of("id", 2, "nombre", "Rural")));
        }

        @GetMapping("/tipos-documento")
        public ResponseEntity<List<TipodocumentopersonaOutput>> getTiposDocumento() {
                return ResponseEntity.ok(tipodocumentopersonaProcessor.findAll());
        }

        @GetMapping("/estados-civiles")
        public ResponseEntity<List<EstadocivilOutput>> getEstadosCiviles() {
                List<EstadocivilOutput> list = estadocivilService.findAll().stream()
                                .map(dto -> EstadocivilOutput.builder()
                                                .id(dto.getId())
                                                .tipo(dto.getEstado())
                                                .build())
                                .toList();
                return ResponseEntity.ok(list);
        }

        @GetMapping("/generos")
        public ResponseEntity<List<GeneroOutput>> getGeneros() {
                return ResponseEntity.ok(generoProcessor.findAll());
        }

        @GetMapping("/paises")
        public ResponseEntity<List<PaisOutput>> getPaises() {
                return ResponseEntity.ok(paisProcessor.findAll());
        }

        @GetMapping("/paises/{idPais}/departamentos")
        public ResponseEntity<List<DepartamentoOutput>> getDepartamentosByPais(@PathVariable Integer idPais) {
                List<DepartamentoOutput> departamentos = departamentoService.findByIdPais(idPais).stream()
                                .map(d -> DepartamentoOutput.builder()
                                                .id(d.getId())
                                                .nombre(d.getNombre())
                                                .build())
                                .toList();
                return ResponseEntity.ok(departamentos);
        }

        @GetMapping("/departamentos/{idDepartamento}/municipios")
        public ResponseEntity<List<MunicipioOutput>> getMunicipiosByDepartamento(@PathVariable Integer idDepartamento) {
                List<MunicipioOutput> municipios = municipioService.findByIdDepartamento(idDepartamento).stream()
                                .map(m -> MunicipioOutput.builder()
                                                .id(m.getId())
                                                .nombre(m.getNombre())
                                                .build())
                                .toList();
                return ResponseEntity.ok(municipios);
        }

        @GetMapping("/programa/{programaId}/cohortes")
        public ResponseEntity<List<CohorteActivaOutput>> getCohortesActivas(@PathVariable Integer programaId) {
                List<CohorteActivaOutput> cohortes = cohorteService.findActivasByIdPrograma(programaId).stream()
                                .map(c -> CohorteActivaOutput.builder()
                                                .id(c.getId())
                                                .nombre(c.getNombre())
                                                .build())
                                .toList();
                return ResponseEntity.ok(cohortes);
        }

        @Autowired
        private ProgramaProcessor programaProcessor;

        @GetMapping("/programas")
        public ResponseEntity<List<ProgramaListadoOutput>> getProgramas() {
                List<ProgramaListadoOutput> programas = programaProcessor.findAll().stream()
                                .map(this::toProgramaListadoOutput)
                                .toList();
                return ResponseEntity.ok(programas);
        }

        @GetMapping("/requisitos")
        public ResponseEntity<List<RequisitoDocumentoOutput>> getRequisitos() {
                throw new UnsupportedOperationException("Operación no soportada: reimplementación pendiente.");
        }

        // ─── Helpers privados ────────────────────────────────────────────────────

        private String serializarExperiencia(List<ExperienciaLaboralItem> items) {
                if (items == null || items.isEmpty())
                        return "[]";
                StringBuilder sb = new StringBuilder("[");
                for (int i = 0; i < items.size(); i++) {
                        ExperienciaLaboralItem item = items.get(i);
                        sb.append("{")
                                        .append("\"empresa\":").append(jsonString(item.empresa())).append(",")
                                        .append("\"cargo\":").append(jsonString(item.cargo())).append(",")
                                        .append("\"fechaInicio\":")
                                        .append(jsonString(item.fechaInicio() != null ? item.fechaInicio().toString()
                                                        : null))
                                        .append(",")
                                        .append("\"fechaFin\":")
                                        .append(jsonString(item.fechaFin() != null ? item.fechaFin().toString() : null))
                                        .append("}");
                        if (i < items.size() - 1)
                                sb.append(",");
                }
                return sb.append("]").toString();
        }

        private String jsonString(String valor) {
                if (valor == null)
                        return "null";
                return "\"" + valor.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
        }

        private ProgramaListadoOutput toProgramaListadoOutput(ProgramaOutput programa) {
                return ProgramaListadoOutput.builder()
                                .id(programa.id())
                                .nombre(programa.nombre())
                                .build();
        }

}
