package org.pay.payfusion2;

import java.io.IOException;

public interface Order {

    long getQuantity();

    String getID();

    String getCheckoutURL();

    MasterProduct getProduct();

}
