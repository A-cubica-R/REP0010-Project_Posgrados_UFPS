package ufps.edu.co.records.input;

import jakarta.validation.constraints.NotNull;
import ufps.edu.co.records.contracts.*;

public enum OtrosvaloresInput {
        ;

        public record OTROSVALORES_CREATE(
                        @NotNull Boolean carnet,
                        @NotNull Boolean estampilla,
                        @NotNull Boolean seguro) implements CreateType {
        }

        public record OTROSVALORES_UPDATE(
                        @NotNull Integer id,
                        @NotNull Boolean carnet,
                        @NotNull Boolean estampilla,
                        @NotNull Boolean seguro) implements UpdateType {
        }

        public record OTROSVALORES_PATCH(
                        @NotNull Integer id,
                        Boolean carnet,
                        Boolean estampilla,
                        Boolean seguro) implements PatchType {
        }

        public record OTROSVALORES_DELETE(@NotNull Integer id) implements DeleteType {
        }

        public record OTROSVALORES_FIND(@NotNull Integer id) implements FindType {
        }
}