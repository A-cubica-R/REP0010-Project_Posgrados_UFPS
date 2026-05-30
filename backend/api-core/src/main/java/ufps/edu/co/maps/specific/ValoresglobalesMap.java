package ufps.edu.co.maps.specific;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class ValoresglobalesMap {

    public Long parsePesos(String valor) {
        if (valor == null) {
            return null;
        }

        String normalized = valor.trim().replace(".", "").replace(",", ".");
        if (normalized.isEmpty()) {
            return null;
        }

        try {
            return new BigDecimal(normalized).longValueExact();
        } catch (NumberFormatException | ArithmeticException ex) {
            return null;
        }
    }

    public BigDecimal parseSmmlvMultiplier(String valor) {
        if (valor == null) {
            return null;
        }

        String normalized = valor.trim().toUpperCase();
        if (normalized.isEmpty()) {
            return null;
        }

        normalized = normalized.replace("SMMLV", "").replace("SMLMV", "").trim();
        normalized = normalized.replace(",", ".");

        if (normalized.isEmpty()) {
            return BigDecimal.ONE;
        }

        try {
            return new BigDecimal(normalized);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}