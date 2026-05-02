package ufps.edu.co.records.output.entity;

import java.time.LocalDate;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;


@Builder
public record PersonaOutput(
        Integer id,
        String nombres,
        String apellidos,
        String correo,
        LocalDate fechanacimiento,
        String celular,
        String telefono,
        UbicacionOutput ubicacion,
        GeneroOutput genero) implements OutputResponse {
}
