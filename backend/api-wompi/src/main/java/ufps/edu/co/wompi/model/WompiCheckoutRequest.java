package ufps.edu.co.wompi.model;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Builder;

@Builder
public record WompiCheckoutRequest(
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
        String customerEmail,
        String customerName,
        WompiCustomerData customerData,
        WompiReceiptData receiptData,
        String returnUrl,
        String webhookUrl,
        Map<String, String> metadata) {
}