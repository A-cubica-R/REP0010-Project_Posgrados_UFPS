package ufps.edu.co.controllers.cases.aspirante;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import ufps.edu.co.records.input.entity.AspiranteInput.ASPIRANTE_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_CANCELAR_REQUEST;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_FIND;
import ufps.edu.co.records.input.entity.EntrevistaInput.ENTREVISTA_REQUEST_CHANGE;
import ufps.edu.co.records.output.entity.AspiranteDocumentosOutput;
import ufps.edu.co.records.output.entity.DocumentoAspiranteOutput;
import ufps.edu.co.records.output.entity.EntrevistaOutput;
import ufps.edu.co.records.output.entity.EntrevistaResumenOutput;
import ufps.edu.co.records.output.entity.EntrevistaSimpleOutput;
import ufps.edu.co.records.output.entity.PasoProcesoOutput;
import ufps.edu.co.rest.dto.AspiranteDTO;
import ufps.edu.co.rest.dto.CohorteDTO;
import ufps.edu.co.rest.dto.DocumentoDTO;
import ufps.edu.co.rest.dto.EstadodocumentoDTO;
import ufps.edu.co.rest.dto.TipodocumentoDTO;
import ufps.edu.co.rest.dto.UsuarioDTO;
import ufps.edu.co.rest.services.AspiranteService;
import ufps.edu.co.rest.services.CohorteService;
import ufps.edu.co.rest.services.DocumentoService;
import ufps.edu.co.rest.services.EstadodocumentoService;
import ufps.edu.co.rest.services.TipodocumentoService;
import ufps.edu.co.rest.services.UsuarioService;
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

    @Autowired
    private TipodocumentoService tipodocumentoService;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private EstadodocumentoService estadodocumentoService;

    @Autowired
    private CohorteService cohorteService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private EntrevistaProcessor entrevistaProcessor;

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

        boolean isNew = documentoService
                .findByIdAspiranteAndIdTipodocumento(idAspirante, idRequisito)
                .isEmpty();

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
                    .idTipodocumento(idRequisito)
                    .idEstadodocumento(estadoPendiente.getId())
                    .idPlazo(cohorte.getIdPlazodocumentacion())
                    .build();
            documentoService.create(nuevo);
        } else {
            DocumentoDTO doc = documentoService
                    .findByIdAspiranteAndIdTipodocumento(idAspirante, idRequisito)
                    .orElseThrow();
            doc.setEnlaceurl(upload.enlaceurl());
            doc.setKeyfile(upload.keyfile());
            doc.setFechacargue(LocalDate.now());
            doc.setIdEstadodocumento(estadoPendiente.getId());
            doc.setEstadodocumento(estadoPendiente);
            doc.setObservaciones(null);
            documentoService.update(doc.getId(), doc);
        }

        TipodocumentoDTO tipo = tipodocumentoService.findById(idRequisito);
        DocumentoDTO saved = documentoService
                .findByIdAspiranteAndIdTipodocumento(idAspirante, idRequisito)
                .orElseThrow();

        return ResponseEntity.status(isNew ? HttpStatus.CREATED : HttpStatus.OK)
                .body(toDocumentoOutput(saved, tipo));
    }

    private AspiranteDTO resolveAspirante() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDTO usuario = usuarioService.findByNombreusuario(username);
        return aspiranteService.findByIdPersona(usuario.getIdPersona());
    }

    private List<DocumentoAspiranteOutput> buildDocumentosResponse(Integer idAspirante) {
        List<TipodocumentoDTO> tipos = tipodocumentoService.findAll();
        Map<Integer, DocumentoDTO> docsPorTipo = documentoService.findByIdAspirante(idAspirante)
                .stream()
                .filter(d -> d.getIdTipodocumento() != null)
                .collect(Collectors.toMap(DocumentoDTO::getIdTipodocumento, Function.identity(), (a, b) -> a));

        return tipos.stream().map(tipo -> {
            DocumentoDTO doc = docsPorTipo.get(tipo.getId());
            if (doc == null) {
                return DocumentoAspiranteOutput.builder()
                        .idDocumento(null)
                        .idRequisito(tipo.getId())
                        .nombre(tipo.getDescripcion())
                        .status("pending")
                        .nombreArchivo(null)
                        .rejectionReason(null)
                        .build();
            }
            return toDocumentoOutput(doc, tipo);
        }).toList();
    }

    private DocumentoAspiranteOutput toDocumentoOutput(DocumentoDTO doc, TipodocumentoDTO tipo) {
        return DocumentoAspiranteOutput.builder()
                .idDocumento(doc.getId())
                .idRequisito(tipo.getId())
                .nombre(tipo.getDescripcion())
                .status(mapStatus(doc))
                .nombreArchivo(extractNombre(doc.getKeyfile()))
                .rejectionReason(doc.getObservaciones())
                .build();
    }

    private String mapStatus(DocumentoDTO doc) {
        if (doc.getEstadodocumento() == null) return "pending";
        return switch (doc.getEstadodocumento().getEstado().toUpperCase()) {
            case "APROBADO" -> "approved";
            case "RECHAZADO" -> "rejected";
            default -> "pending";
        };
    }

    private String extractNombre(String keyfile) {
        if (keyfile == null) return null;
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
}
