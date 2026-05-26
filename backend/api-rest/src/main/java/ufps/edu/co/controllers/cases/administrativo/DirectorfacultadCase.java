package ufps.edu.co.controllers.cases.administrativo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import ufps.edu.co.processor.crud.AdministrativoProcessor;
import ufps.edu.co.processor.crud.AspiranteProcessor;
import ufps.edu.co.processor.crud.DocumentoProcessor;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_FIND;
import ufps.edu.co.records.input.entity.DocumentoInput.DOCUMENTO_ESTADO_UPDATE;
import ufps.edu.co.records.input.entity.ProgramaInput.PROGRAMA_CREATE_WITH_RELATIONS;
import ufps.edu.co.records.input.entity.ProgramaInput.PROGRAMA_UPDATE_WITH_RELATIONS;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.AspiranteCohorteOutput;
import ufps.edu.co.records.output.entity.AspiranteDocumentosOutput;
import ufps.edu.co.records.output.entity.CohorteResumenOutput;
import ufps.edu.co.records.output.entity.DocumentoEstadoOutput;
import ufps.edu.co.records.output.entity.ProgramaOutput;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.dto.UsuarioDTO;
import ufps.edu.co.rest.services.AdministrativoService;
import ufps.edu.co.rest.services.UsuarioService;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectorfacultadCase {

    @Autowired
    private AdministrativoProcessor processor;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AdministrativoService administrativoService;

    @Autowired
    private AspiranteProcessor aspiranteProcessor;

    @Autowired
    private DocumentoProcessor documentoProcessor;

    @GetMapping("/listPosibleDirector")
    public ResponseEntity<List<AdministrativoOutput>> findPosiblesDirectores() {
        List<AdministrativoOutput> list = processor.findPosiblesDirectores();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/ListProgramas")
    public ResponseEntity<List<ProgramaOutput>> findProgramasFacultad(@RequestBody ADMINISTRATIVO_FIND input) {
        try {
            ADMINISTRATIVO_FIND useInput = resolveAdministrativoFind(input);
            List<ProgramaOutput> list = processor.findProgramasFacultad(useInput);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error handling request: " + e.getMessage(), e);
        }
    }

    @PostMapping(value = "/programa/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgramaOutput> createPrograma(@RequestBody PROGRAMA_CREATE_WITH_RELATIONS input) {
        ADMINISTRATIVO_FIND useInput = resolveAdministrativoFind(null);
        ProgramaOutput output = processor.createProgramaFacultad(useInput, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping(value = "/programa/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProgramaOutput> updatePrograma(@RequestBody PROGRAMA_UPDATE_WITH_RELATIONS input) {
        ADMINISTRATIVO_FIND useInput = resolveAdministrativoFind(null);
        ProgramaOutput output = processor.updateProgramaFacultad(useInput, input);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/cohortes")
    public ResponseEntity<List<CohorteResumenOutput>> getCohortesByPrograma() {
        try {
            Integer programaId = resolvePrograma();
            return ResponseEntity.ok(aspiranteProcessor.getCohortesByProgramaResumen(programaId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/cohortes/{idCohorte}/aspirantes")
    public ResponseEntity<List<AspiranteCohorteOutput>> getAspirantesByCohorte(
            @PathVariable Integer idCohorte) {
        try {
            return ResponseEntity.ok(aspiranteProcessor.findByCohorteConResumen(idCohorte));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/aspirantes/{idAspirante}/documentos")
    public ResponseEntity<AspiranteDocumentosOutput> getDocumentosByAspirante(
            @PathVariable Integer idAspirante) {
        try {
            return ResponseEntity.ok(documentoProcessor.getDocumentosDeAspirante(idAspirante));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(value = "/documentos/{idDoc}/estado", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentoEstadoOutput> updateEstadoDocumento(
            @PathVariable Integer idDoc,
            @RequestBody DOCUMENTO_ESTADO_UPDATE body) {
        try {
            return ResponseEntity.ok(documentoProcessor.updateEstadoDocumento(idDoc, body));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private ADMINISTRATIVO_FIND resolveAdministrativoFind(ADMINISTRATIVO_FIND input) {
        if (input != null && input.id() != null) {
            return input;
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDTO usuario = usuarioService.findByNombreusuario(username);
        if (usuario == null || usuario.getIdPersona() == null) {
            throw new RuntimeException("No se pudo derivar el administrativo desde el usuario autenticado");
        }
        AdministrativoDTO admin = administrativoService.findByIdPersona(usuario.getIdPersona());
        if (admin == null || admin.getId() == null) {
            throw new RuntimeException("No existe un administrativo asociado a la persona del usuario autenticado");
        }
        return new ADMINISTRATIVO_FIND(admin.getId());
    }

    private Integer resolvePrograma() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioDTO usuario = usuarioService.findByNombreusuario(username);
        if (usuario == null || usuario.getIdPersona() == null) {
            throw new RuntimeException("No se pudo derivar el administrativo desde el usuario autenticado");
        }
        AdministrativoDTO admin = administrativoService.findByIdPersona(usuario.getIdPersona());
        if (admin == null || admin.getCargo() == null || admin.getCargo().getIdPrograma() == null) {
            throw new RuntimeException("El usuario autenticado no tiene un programa asignado");
        }
        return admin.getCargo().getIdPrograma();
    }

}
