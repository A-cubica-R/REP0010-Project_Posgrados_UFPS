package ufps.edu.co.records.input.entity;

import java.math.BigDecimal;

import lombok.Builder;
import ufps.edu.co.records.contracts.*;

public enum CriteriocohorteInput {
        ;

        @Builder
        public record CRITERIOCOHORTE_CREATE(
                        Integer idCohorte,
                        Integer idCriterio,
                        BigDecimal pesoSnapshot) implements CreateType {
        }

        @Builder
        public record CRITERIOCOHORTE_UPDATE(
                        Integer id,
                        Integer idCohorte,
                        Integer idCriterio,
                        BigDecimal pesoSnapshot) implements UpdateType {
        }

        @Builder
        public record CRITERIOCOHORTE_PATCH(
                        Integer id,
                        Integer idCohorte,
                        Integer idCriterio,
                        BigDecimal pesoSnapshot) implements PatchType {
        }

        @Builder
        public record CRITERIOCOHORTE_DELETE(
                        Integer id) implements DeleteType {
        }

        @Builder
        public record CRITERIOCOHORTE_FIND(
                        Integer id) implements FindType {
        }
}
