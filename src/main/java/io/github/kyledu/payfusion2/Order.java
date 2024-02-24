package io.github.kyledu.payfusion2;

/**
 * Order interface defines order methods. To be implemented by specific payment gateway orders
 */
public interface Order {

    MasterProductDefinition getProduct();

    long getQuantity();

    String getOrderID();

    String getOrderURL();

    PaymentType getType();

    String capture();

}
