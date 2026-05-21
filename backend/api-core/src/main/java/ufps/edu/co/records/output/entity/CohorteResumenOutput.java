package ufps.edu.co.records.output.entity;

import java.time.LocalDate;

import lombok.Builder;
import ufps.edu.co.records.OutputResponse;

@Builder
public record CohorteResumenOutput(
        Integer id,
        String nombre,
        boolean activa,
        String semestre,
        int cupos,
        LocalDate fechaLimiteDocs,
        LocalDate fechaLimiteInscripcion,
        long totalInscritos,
        long totalValidados,
        Long totalAdmitidos
) implements OutputResponse {}
