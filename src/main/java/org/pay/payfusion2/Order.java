package org.pay.payfusion2;

import java.io.IOException;

public interface Order {

    String[] createOrder() throws IOException;

    String getName();

    String getDescription();

    double getValue();

}
