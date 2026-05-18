package ufps.edu.co.records.output.entity;

import java.time.LocalDate;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record ListaadmitidosOutput(
        Integer id,
        LocalDate fechageneracion,
        Integer idCohorte,
        Integer idAspirante,
        AspiranteOutput aspirante,
        CohorteOutput cohorte
) implements OutputResponse {}
