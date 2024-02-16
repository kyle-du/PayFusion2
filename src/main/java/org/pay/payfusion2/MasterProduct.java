package org.pay.payfusion2;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;

import java.math.BigDecimal;
import java.util.List;

public class MasterProduct {

    private final String name;
    private final String description;
    private final double price;
    private final String currencyCode; //usd
    private final List<String> imageURLs;

    private final Product stripeProduct;
    private final Price stripePrice;


    public MasterProduct(String name, String description, int price, String currencyCode, List<String> imageURLs) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.currencyCode = currencyCode;
        this.imageURLs = imageURLs;
        this.stripeProduct = createStripeProduct();
        this.stripePrice = createStripePrice();
    }

    private Product createStripeProduct() {
        ProductCreateParams params =
                ProductCreateParams.builder()
                        .setName(name)
                        .setDescription(description)
                        .addAllImage(imageURLs)
                        .build();
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
                        .setUnitAmountDecimal(BigDecimal.valueOf(price))
                        .setRecurring(
                                PriceCreateParams.Recurring.builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build()
                        )
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
}
