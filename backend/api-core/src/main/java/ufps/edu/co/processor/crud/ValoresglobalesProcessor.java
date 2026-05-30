package ufps.edu.co.processor.crud;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufps.edu.co.domain.exceptions.DomainException;
import ufps.edu.co.domain.exceptions.errorcodes.PagoErrorCode;
import ufps.edu.co.maps.specific.ValoresglobalesMap;
import ufps.edu.co.rest.dto.ValoresglobalesDTO;
import ufps.edu.co.rest.services.ValoresglobalesService;

@Service
public class ValoresglobalesProcessor {

    private static final Pattern GLOBAL_KEY_PATTERN = Pattern.compile("^(?<prefix>[A-Z_]+)_(?<year>\\d{4})_(?<version>\\d+)$");

    @Autowired
    private ValoresglobalesService valoresglobalesService;

    @Autowired
    private ValoresglobalesMap valoresglobalesMap;

    public ValoresglobalesDTO findCurrentByPrefix(String prefix) {
        int currentYear = LocalDate.now().getYear();
        return findAll().stream()
                .filter(item -> item.getClave() != null)
                .filter(item -> item.getClave().startsWith(prefix + "_" + currentYear + "_"))
                .max(Comparator.comparingInt(item -> extractVersion(item.getClave(), prefix, currentYear)))
                .orElseThrow(() -> new DomainException(PagoErrorCode.VALOR_GLOBAL_NO_CONFIGURADO_NOT_FOUND,
                        prefix + "_" + currentYear));
    }

    public Long getCurrentSalaryMinimumPesos() {
        ValoresglobalesDTO dto = findCurrentByPrefix("SALARIO_MINIMO");
        Long salarioMinimo = valoresglobalesMap.parsePesos(dto.getValor());
        if (salarioMinimo == null) {
            throw new DomainException(PagoErrorCode.VALOR_GLOBAL_FORMATO_INVALIDO, dto.getClave());
        }
        return salarioMinimo;
    }

    public BigDecimal getCurrentValorInscripcionMultiplier() {
        ValoresglobalesDTO dto = findCurrentByPrefix("VALOR_INSCRIPCION");
        BigDecimal multiplier = valoresglobalesMap.parseSmmlvMultiplier(dto.getValor());
        if (multiplier == null) {
            throw new DomainException(PagoErrorCode.VALOR_GLOBAL_FORMATO_INVALIDO, dto.getClave());
        }
        return multiplier;
    }

    public BigDecimal calcularValorInscripcionPesos() {
        BigDecimal salarioMinimo = BigDecimal.valueOf(getCurrentSalaryMinimumPesos());
        BigDecimal multiplier = getCurrentValorInscripcionMultiplier();
        return salarioMinimo.multiply(multiplier);
    }

    public long calcularValorInscripcionCentavos() {
        return calcularValorInscripcionPesos().movePointRight(2).setScale(0, RoundingMode.HALF_UP).longValueExact();
    }

    private List<ValoresglobalesDTO> findAll() {
        return valoresglobalesService.findAll();
    }

    private int extractVersion(String clave, String prefix, int year) {
        Matcher matcher = GLOBAL_KEY_PATTERN.matcher(clave);
        if (!matcher.matches()) {
            return 0;
        }
        if (!prefix.equalsIgnoreCase(matcher.group("prefix"))) {
            return 0;
        }
        if (Integer.parseInt(matcher.group("year")) != year) {
            return 0;
        }
        return Integer.parseInt(matcher.group("version"));
    }
}