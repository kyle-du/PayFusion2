package org.pay.payfusion2;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

public class StripeOrder implements Order {

    private final MasterProduct product;
    private final long quantity;
    private final String sessionID;
    private final String sessionURL;

    public StripeOrder(MasterProduct product, long quantity) {
        this.product = product;
        this.quantity = quantity;
        String[] responses = createOrder();
        this.sessionID = responses[0];
        this.sessionURL = responses[1];
    }

    public String[] createOrder() {
        SessionCreateParams params =
                SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT) //specifies one-time payment, alt: subscription
                .setSuccessUrl("https://example.com/returnUrl") //@TODO: placeholder
                .setCancelUrl("https://example.com/cancelUrl")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                            .setQuantity((quantity))
                            .setPrice(product.getStripePrice().getId())
                            .build())
                .build();
        try {
            Session session = Session.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
        }


    }
}
