package ufps.edu.co.wompi;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import ufps.edu.co.wompi.config.WompiProperties;
import ufps.edu.co.wompi.model.WompiCheckoutRequest;
import ufps.edu.co.wompi.model.WompiCheckoutResponse;
import ufps.edu.co.wompi.model.WompiWebhookRequest;

@Component
@ConditionalOnProperty(prefix = "wompi", name = "simulated", havingValue = "false")
public class RealWompiGateway implements WompiGateway {

    private final WompiProperties properties;

    public RealWompiGateway(WompiProperties properties) {
        this.properties = properties;
    }

    @Override
    public WompiCheckoutResponse createCheckout(WompiCheckoutRequest request) {
        return WompiCheckoutResponse.builder()
                .paymentId(request.paymentId())
                .aspiranteId(request.aspiranteId())
                .pagoconceptoId(request.pagoconceptoId())
                .concepto(request.concepto())
                .reference(request.reference())
                .amount(request.amount())
                .amountInCents(request.amountInCents())
                .currency(request.currency())
                .publicKey(request.publicKey())
                .signatureIntegrity(request.signatureIntegrity())
                .redirectUrl(request.redirectUrl())
                .widgetScriptUrl(request.widgetScriptUrl())
                .checkoutUrl(properties.getCheckoutBaseUrl() + "/" + request.reference())
                .simulated(false)
                .message("Integración real de Wompi pendiente de credenciales y endpoint definitivo")
                .transactionId(null)
                .customerEmail(request.customerEmail())
                .customerName(request.customerName())
                .build();
    }

    @Override
    public WompiCheckoutResponse confirmPayment(WompiWebhookRequest request) {
        return WompiCheckoutResponse.builder()
                .paymentId(request.paymentId())
                .reference(request.reference())
                .amount(request.amount())
                .amountInCents(request.amountInCents() != null ? request.amountInCents() : 0L)
                .currency(request.currency() != null ? request.currency() : properties.getCurrency())
                .publicKey(request.publicKey())
                .signatureIntegrity(request.signatureIntegrity())
                .redirectUrl(request.redirectUrl())
                .widgetScriptUrl(request.widgetScriptUrl())
                .checkoutUrl(properties.getCheckoutBaseUrl() + "/" + request.reference())
                .simulated(false)
                .message("Confirmación real de Wompi pendiente de conectar al endpoint oficial")
                .transactionId(request.transactionId())
                .customerEmail(request.customerEmail())
                .customerName(request.customerName())
                .build();
    }

    @Override
    public boolean isSimulated() {
        return false;
    }
}