package io.github.kyledu.payfusion2;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

/**
 * defines a Stripe Order object with its checkout URL
 */
public class StripeOrder implements Order {

    private final MasterProductDefinition product;
    private final long quantity;
    private final String orderID;
    private final String orderURL;
    private final PaymentType type = PaymentType.STRIPE;

    public StripeOrder(MasterProductDefinition product, long quantity) {
        this.product = product;
        this.quantity = quantity;
        String[] responses = createOrder();
        this.orderID = responses[0];
        this.orderURL = responses[1];
    }

    private String[] createOrder() {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT) //specifies one-time payment, alt: subscription
                        .setSuccessUrl(Constants.RETURN_URL)
                        .setCancelUrl(Constants.CANCEL_URL)
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

    /**
     * Stripe quickpay no need for backend capturing
     *
     * @return String message
     */
    public String capture() {
        return "capture not required nor supported for Stripe";
    }

    public MasterProductDefinition getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderURL() {
        return orderURL;
    }

    public PaymentType getType() {
        return type;
    }
}
