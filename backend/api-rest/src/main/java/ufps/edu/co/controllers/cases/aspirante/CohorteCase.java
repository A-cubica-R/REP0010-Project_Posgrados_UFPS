package ufps.edu.co.controllers.cases.aspirante;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.edu.co.processor.crud.AspiranteProcessor;
import ufps.edu.co.records.output.entity.AspiranteCohorteOutput;
import ufps.edu.co.records.output.entity.CohorteResumenOutput;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.dto.UsuarioDTO;
import ufps.edu.co.rest.services.AdministrativoService;
import ufps.edu.co.rest.services.UsuarioService;

@RestController
@RequestMapping(value = "/cohortes", produces = MediaType.APPLICATION_JSON_VALUE)
public class CohorteCase {

    @Autowired
    private AspiranteProcessor aspiranteProcessor;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AdministrativoService administrativoService;

    @GetMapping
    public ResponseEntity<List<CohorteResumenOutput>> getCohortesByPrograma() {
        Integer programaId = resolvePrograma();
        return ResponseEntity.ok(aspiranteProcessor.getCohortesByProgramaResumen(programaId));
    }

    @GetMapping("/{idCohorte}/aspirantes")
    public ResponseEntity<List<AspiranteCohorteOutput>> getAspirantesByCohorte(
            @PathVariable Integer idCohorte) {
        return ResponseEntity.ok(aspiranteProcessor.findByCohorteConResumen(idCohorte));
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
