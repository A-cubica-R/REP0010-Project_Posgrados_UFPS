package posgrados.ufps.demo.controller;

import lombok.RequiredArgsConstructor;
import posgrados.ufps.demo.dto.Aspirante.AspiranteRequestDTO;
import posgrados.ufps.demo.dto.Aspirante.AspiranteResponseDTO;
import posgrados.ufps.demo.service.AspiranteService;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/aspirantes")
@RequiredArgsConstructor
public class AspiranteController {

    private final AspiranteService service;

    @PostMapping
    public AspiranteResponseDTO crear(@Valid @RequestBody AspiranteRequestDTO dto) {
        return service.crear(dto);
    }

    @GetMapping
    public List<AspiranteResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AspiranteResponseDTO obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public AspiranteResponseDTO actualizar(@PathVariable Long id,
                                           @Valid @RequestBody AspiranteRequestDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
