package ufps.edu.co.records.input.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;
import ufps.edu.co.records.input.entity.DocumentocohorteInput.*;

public enum CohorteInput {
        ;

        public record COHORTE_CREATE(
                        String nombre,
                        @NotBlank Integer cupos,
                        @NotNull Boolean requiereentrevista,
                        @NotNull Boolean requiereprueba,
                        @NotNull Integer idEstado,
                        @NotNull Integer idSemestre,
                        @NotNull Integer idModalidad,
                        @NotNull Integer idPlazodocumentacion,
                        @NotNull Integer idPlazoinscripcion,
                        @NotNull Integer idPlazopago,
                        @NotNull Integer idPrograma) implements CreateType {
        }

        public record COHORTE_UPDATE(
                        @NotNull Integer id,
                        @NotBlank String nombre,
                        @NotBlank Integer cupos,
                        @NotNull Boolean requiereentrevista,
                        @NotNull Boolean requiereprueba,
                        @NotNull Integer idEstado,
                        @NotNull Integer idSemestre,
                        @NotNull Integer idModalidad,
                        @NotNull Integer idPlazodocumentacion,
                        @NotNull Integer idPlazoinscripcion,
                        @NotNull Integer idPlazopago,
                        @NotNull Integer idPrograma) implements UpdateType {
        }

        public record COHORTE_PATCH(
                        @NotNull Integer id,
                        @NotBlank String nombre,
                        @NotBlank Integer cupos,
                        @NotNull Boolean requiereentrevista,
                        @NotNull Boolean requiereprueba,
                        @NotNull Integer idEstado,
                        @NotNull Integer idSemestre,
                        @NotNull Integer idModalidad,
                        @NotNull Integer idPlazodocumentacion,
                        @NotNull Integer idPlazoinscripcion,
                        @NotNull Integer idPlazopago,
                        @NotNull Integer idPrograma) implements PatchType {
        }

        public record COHORTE_DELETE(@NotNull Integer id) implements DeleteType {
        }

        public record COHORTE_FIND(@NotNull Integer id) implements FindType {
        }

        public record COHORTE_DIRECTOR_CREATE(
                        @NotBlank String nombre,
                        @NotNull Integer cupos,
                        @NotNull LocalDate fechaInicio,
                        @NotNull LocalDate fechaLimiteDocumentos,
                        @NotNull LocalDate fechaLimitePago,
                        List<DOCUMENTOCOHORTE_CREATE> documentos) implements CreateType {
        }

        public record COHORTE_DIRECTOR_UPDATE(
                        @NotBlank String nombre,
                        @NotNull Integer cupos,
                        LocalDate fechaInicio,
                        LocalDate fechaLimiteDocumentos,
                        LocalDate fechaLimitePago,
                        List<DOCUMENTOCOHORTE_UPDATE> documentos) implements UpdateType {
        }

        public record COHORTE_WITHPLAZO_CREATE(
                        String nombre,
                        @NotBlank Integer cupos,
                        @NotNull Boolean requiereentrevista,
                        @NotNull Boolean requiereprueba,
                        @NotNull Integer idEstado,
                        @NotNull Integer idSemestre,
                        @NotNull Integer idModalidad,
                        @NotNull Integer idPlazodocumentacion,
                        @NotNull Integer idPlazoinscripcion,
                        @NotNull Integer idPlazopago,
                        @NotNull Integer idPrograma,
                        @NotNull PlazoInput.PLAZO_CREATE plazocreate) implements CreateType {
        }
}
