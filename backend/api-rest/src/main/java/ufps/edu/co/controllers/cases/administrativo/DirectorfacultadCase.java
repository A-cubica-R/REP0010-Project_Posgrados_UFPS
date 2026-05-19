package ufps.edu.co.controllers.cases.administrativo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ufps.edu.co.processor.crud.AdministrativoProcessor;
import ufps.edu.co.rest.dto.UsuarioDTO;
import ufps.edu.co.rest.dto.AdministrativoDTO;
import ufps.edu.co.rest.services.UsuarioService;
import ufps.edu.co.rest.services.AdministrativoService;
import org.springframework.security.core.context.SecurityContextHolder;
import ufps.edu.co.records.input.entity.AdministrativoInput.ADMINISTRATIVO_FIND;
import ufps.edu.co.records.output.entity.AdministrativoOutput;
import ufps.edu.co.records.output.entity.ProgramaOutput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/Directorfacultad", produces = MediaType.APPLICATION_JSON_VALUE)
public class DirectorfacultadCase {

    @Autowired
    private AdministrativoProcessor processor;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AdministrativoService administrativoService;

    @GetMapping("/listPosibleDirector")
    public ResponseEntity<List<AdministrativoOutput>> findPosiblesDirectores() {
        List<AdministrativoOutput> list = processor.findPosiblesDirectores();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/ListProgramas")
    public ResponseEntity<List<ProgramaOutput>> findProgramasFacultad(@RequestBody ADMINISTRATIVO_FIND input) {
        ADMINISTRATIVO_FIND useInput = input;
        try {
            if (useInput == null || useInput.id() == null) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                UsuarioDTO usuario = usuarioService.findByNombreusuario(username);
                if (usuario == null || usuario.getIdPersona() == null) {
                    throw new RuntimeException("No se pudo derivar el administrativo desde el usuario autenticado");
                }
                AdministrativoDTO admin = administrativoService.findByIdPersona(usuario.getIdPersona());
                if (admin == null || admin.getId() == null) {
                    throw new RuntimeException("No existe un administrativo asociado a la persona del usuario autenticado");
                }
                useInput = new ADMINISTRATIVO_FIND(admin.getId());
            }
            List<ProgramaOutput> list = processor.findProgramasFacultad(useInput);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error handling request: " + e.getMessage(), e);
        }
    }

}
