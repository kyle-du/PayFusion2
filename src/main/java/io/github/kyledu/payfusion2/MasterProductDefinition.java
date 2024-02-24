package org.pay.payfusion2;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;

import java.math.BigDecimal;
import java.util.List;

/**
 * product definition for use in order creation. one to many; product -> orders
 */
public class MasterProductDefinition {

    private final String name;
    private final String description;
    private final double price;
    private final String currencyCode; //usd
    private final List<String> imageURLs;
    private final Product stripeProduct;
    private final Price stripePrice;
    private final String stripePriceID;

    /**
     * default constructor
     *
     * @param name
     * @param description
     * @param price
     * @param currencyCode
     * @param imageURLs
     */
    public MasterProductDefinition(String name, String description, double price, String currencyCode, List<String> imageURLs) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.currencyCode = currencyCode;
        this.imageURLs = imageURLs;
        this.stripeProduct = createStripeProduct();
        this.stripePrice = createStripePrice();
        this.stripePriceID = stripePrice.getId();
    }

    /**
     * default imageless constructor
     *
     * @param name
     * @param description
     * @param price
     * @param currencyCode
     */
    public MasterProductDefinition(String name, String description, double price, String currencyCode) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.currencyCode = currencyCode;
        this.imageURLs = null;
        this.stripeProduct = createStripeProduct();
        this.stripePrice = createStripePrice();
        this.stripePriceID = stripePrice.getId();
    }

    /**
     * constructor for existing stripe price
     *
     * @param name
     * @param description
     * @param price
     * @param currencyCode
     * @param imageURLs
     * @param priceId
     */
    public MasterProductDefinition(String name, String description, double price, String currencyCode, List<String> imageURLs, String priceId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.currencyCode = currencyCode;
        this.imageURLs = imageURLs;
        this.stripeProduct = null;
        this.stripePrice = null;
        this.stripePriceID = priceId;
    }

    /**
     * constructor for existing stripe price, imageless
     *
     * @param name
     * @param description
     * @param price
     * @param currencyCode
     * @param priceId
     */
    public MasterProductDefinition(String name, String description, double price, String currencyCode, String priceId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.currencyCode = currencyCode;
        this.imageURLs = null;
        this.stripeProduct = null;
        this.stripePrice = null;
        this.stripePriceID = priceId;
    }

    private Product createStripeProduct() {
        ProductCreateParams params;
        if (imageURLs == null) {
            params = ProductCreateParams.builder()
                    .setName(name)
                    .setDescription(description)
                    .build();
        } else {
            params = ProductCreateParams.builder()
                    .setName(name)
                    .setDescription(description)
                    .addAllImage(imageURLs)
                    .build();
        }
        try {
            return Product.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Price createStripePrice() {
        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency(currencyCode)
                        .setUnitAmountDecimal(BigDecimal.valueOf(price * 100))
                        .setProduct(stripeProduct.getId())
                        .build();
        try {
            return Price.create(params);
        } catch (StripeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public List<String> getImageURLs() {
        return imageURLs;
    }

    public Product getStripeProduct() {
        return stripeProduct;
    }

    public Price getStripePrice() {
        return stripePrice;
    }

    public String getStripePriceID() {
        return stripePriceID;
    }
}
