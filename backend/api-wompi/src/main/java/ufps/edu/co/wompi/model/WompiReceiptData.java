package ufps.edu.co.wompi.model;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record WompiReceiptData(
        Integer paymentId,
        Integer aspiranteId,
        Integer personaId,
        Integer cohorteId,
        Integer pagoconceptoId,
        String concepto,
        String reference,
        BigDecimal amount,
        long amountInCents,
        String currency,
        String fullName,
        String email,
        String phoneNumber,
        String legalId,
        String legalIdType) {
}