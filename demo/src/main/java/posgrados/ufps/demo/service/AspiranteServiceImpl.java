package posgrados.ufps.demo.service;

import lombok.RequiredArgsConstructor;
import posgrados.ufps.demo.dto.Aspirante.AspiranteRequestDTO;
import posgrados.ufps.demo.dto.Aspirante.AspiranteResponseDTO;
import posgrados.ufps.demo.entity.AspiranteEntity;
import posgrados.ufps.demo.repository.AspiranteRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AspiranteServiceImpl implements AspiranteService {

    private final AspiranteRepository repository;

    @Override
    public AspiranteResponseDTO crear(AspiranteRequestDTO dto) {

        AspiranteEntity aspirante = toEntity(dto);

        AspiranteEntity guardado = repository.save(aspirante);

        return toResponseDTO(guardado);
    }

    @Override
    public List<AspiranteResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AspiranteResponseDTO obtenerPorId(Long id) {
        AspiranteEntity aspirante = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aspirante no encontrado"));

        return toResponseDTO(aspirante);
    }

    @Override
    public AspiranteResponseDTO actualizar(Long id, AspiranteRequestDTO dto) {

        AspiranteEntity aspirante = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aspirante no encontrado"));

        // actualizar campos
        aspirante.setPrimerNombre(dto.getPrimerNombre());
        aspirante.setPrimerApellido(dto.getPrimerApellido());
        aspirante.setNumeroDocumento(dto.getNumeroDocumento());
        aspirante.setCorreoElectronico(dto.getCorreoElectronico());
        aspirante.setCelular(dto.getCelular());

        AspiranteEntity actualizado = repository.save(aspirante);

        return toResponseDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private AspiranteEntity toEntity(AspiranteRequestDTO dto) {
        AspiranteEntity a = new AspiranteEntity();
        a.setTipoDocumento(dto.getTipoDocumento());
        a.setNumeroDocumento(dto.getNumeroDocumento());
        a.setPrimerNombre(dto.getPrimerNombre());
        a.setSegundoNombre(dto.getSegundoNombre());
        a.setPrimerApellido(dto.getPrimerApellido());
        a.setSegundoApellido(dto.getSegundoApellido());
        a.setTituloPregrado(dto.getTituloPregrado());
        a.setUniversidadEgreso(dto.getUniversidadEgreso());
        a.setFechaGraduacion(dto.getFechaGraduacion());
        a.setTelefono(dto.getTelefono());
        a.setCelular(dto.getCelular());
        a.setDireccion(dto.getDireccion());
        a.setCorreoElectronico(dto.getCorreoElectronico());
        a.setFechaNacimiento(dto.getFechaNacimiento());
        a.setPromedioAcumulado(dto.getPromedioAcumulado());
        a.setOfertaAcademica(dto.getOfertaAcademica());
        a.setEstadoAspirante(dto.getEstadoAspirante());
        return a;
    }

    private AspiranteResponseDTO toResponseDTO(AspiranteEntity a) {
        return AspiranteResponseDTO.builder()
                .id(a.getId() != null ? a.getId().longValue() : null)
                .numeroDocumento(a.getNumeroDocumento())
                .nombreCompleto(a.getPrimerNombre() + " " + a.getPrimerApellido())
                .tituloPregrado(a.getTituloPregrado())
                .universidadEgreso(a.getUniversidadEgreso())
                .fechaGraduacion(a.getFechaGraduacion())
                .celular(a.getCelular())
                .correoElectronico(a.getCorreoElectronico())
                .promedioAcumulado(a.getPromedioAcumulado())
                .build();
    }
}
