package posgrados.ufps.demo.service;

import java.util.List;

import posgrados.ufps.demo.dto.Aspirante.AspiranteRequestDTO;
import posgrados.ufps.demo.dto.Aspirante.AspiranteResponseDTO;

public interface AspiranteService {

    AspiranteResponseDTO crear(AspiranteRequestDTO dto);

    List<AspiranteResponseDTO> listar();

    AspiranteResponseDTO obtenerPorId(Long id);

    AspiranteResponseDTO actualizar(Long id, AspiranteRequestDTO dto);

    void eliminar(Long id);
}
