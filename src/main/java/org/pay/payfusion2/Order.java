package org.pay.payfusion2;

import java.io.IOException;

public interface Order {

    MasterProductDefinition getProduct();

    long getQuantity();

    String getOrderID();

    String getOrderURL();

    PaymentType getType();

    String capture();

}
