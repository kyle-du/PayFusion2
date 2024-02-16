package org.pay.payfusion2;

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
                            .setPrice(product.getStripePriceID())
                            .build())
                .build();
        try {
            Session session = Session.create(params);
            String[] responses = new String[2];
            responses[0] = session.getId();
            responses[1] = session.getUrl();
            return responses;

        } catch (StripeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public MasterProduct getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getID() {
        return sessionID;
    }

    public String getCheckoutURL() {
        return sessionURL;
    }
}
