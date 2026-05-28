package ufps.edu.co.wompi.model;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record WompiCheckoutResponse(
                Integer paymentId,
                Integer aspiranteId,
                Integer pagoconceptoId,
                String concepto,
                String reference,
                BigDecimal amount,
                long amountInCents,
                String currency,
                String publicKey,
                String signatureIntegrity,
                String redirectUrl,
                String widgetScriptUrl,
                String checkoutUrl,
                boolean simulated,
                String message,
                String transactionId,
                String customerEmail,
                String customerName,
                String status) {
}