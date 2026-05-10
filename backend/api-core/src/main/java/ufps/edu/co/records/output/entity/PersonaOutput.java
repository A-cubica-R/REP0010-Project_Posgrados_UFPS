package ufps.edu.co.records.output.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;


@Builder
public record PersonaOutput(
        Integer id,
        Integer idGenero,
        Integer idUbicacion,
        String nombres,
        String apellidos,
        String correo,
        LocalDate fechanacimiento,
        String celular,
        String telefono,
        GeneroOutput genero,
        UbicacionOutput ubicacion,
        List<AdministrativoOutput> administrativoList,
        List<AspiranteOutput> aspiranteList,
        List<UsuarioOutput> usuarioList
) implements OutputResponse {
}
