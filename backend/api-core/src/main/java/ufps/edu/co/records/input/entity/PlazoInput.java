package ufps.edu.co.records.input.entity;

import java.time.LocalDate;
import ufps.edu.co.records.contracts.*;

public enum PlazoInput {
    ;

    public record PLAZO_CREATE(
            LocalDate fechainicio,
            LocalDate fechafin,
            Integer idTipoplazo) implements CreateType {
    }

    public record PLAZO_UPDATE(
            Integer id,
            LocalDate fechainicio,
            LocalDate fechafin,
            Integer idTipoplazo) implements UpdateType {
    }

    public record PLAZO_PATCH(
            Integer id,
            LocalDate fechainicio,
            LocalDate fechafin,
            Integer idTipoplazo)
            implements PatchType {
    }

    public record PLAZO_DELETE(
            Integer id) implements DeleteType {
    }

    public record PLAZO_FIND(
            Integer id) implements FindType {
    }
}
