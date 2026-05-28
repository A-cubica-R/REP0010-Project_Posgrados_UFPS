package ufps.edu.co.wompi;

import ufps.edu.co.wompi.model.WompiCheckoutRequest;
import ufps.edu.co.wompi.model.WompiCheckoutResponse;
import ufps.edu.co.wompi.model.WompiWebhookRequest;

public interface WompiGateway {

    WompiCheckoutResponse createCheckout(WompiCheckoutRequest request);

    WompiCheckoutResponse confirmPayment(WompiWebhookRequest request);

    boolean isSimulated();
}