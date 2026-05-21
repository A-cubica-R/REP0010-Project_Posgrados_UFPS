package ufps.edu.co.records.output.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
        Boolean egresadoufps,
        String empresa,
        String experiencialaboral,
        BigDecimal promediopregrado,
        String titulopregrado,
        String titulosposgrados,
        Integer idGenero,
        Integer idEstadocivil,
        Integer idGrupoetnico,
        Integer idPoblacionindigena,
        Integer idDiscapacidad,
        Integer idCapacidadexepcional,
        Integer idDocumentopersona,
        Integer idUbicacionvivienda,
        Integer idUbicacionnacimiento,
        Integer idUbicaciontrabajo,
        GeneroOutput genero,
        EstadocivilOutput estadocivil,
        GrupoetnicoOutput grupoetnico,
        PoblacionindigenaOutput poblacionindigena,
        DiscapacidadOutput discapacidad,
        CapacidadexepcionalOutput capacidadexepcional,
        UbicacionOutput ubicacionVivienda,
        UbicacionOutput ubicacionNacimiento,
        UbicacionOutput ubicacionTrabajo,
        List<AdministrativoOutput> administrativoList,
        List<AspiranteOutput> aspiranteList,
        List<UsuarioOutput> usuarioList
) implements OutputResponse {
}
