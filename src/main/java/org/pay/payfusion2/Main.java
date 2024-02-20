package org.pay.payfusion2;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(System.getProperty("java.class.path"));
        Constants.init("EAAAly_Wmr4FT5kvz6SYyjh9vCeqxQdzW_5gP2GBX_vj8zUn39TU3PE6JsW2U6O7");
        Constants.print();

        //Master Product Tests
        MasterProductDefinition p = new MasterProductDefinition("sammy", "bar", 10.3, "usd");
        System.out.println(p.getName());
        System.out.println(p.getDescription());
        System.out.println(p.getPrice());
//        System.out.println(p.getStripeProduct().getId());
//        System.out.println(p.getStripeProduct().getName());
//        System.out.println(p.getStripeProduct().getUrl());
//        System.out.println(p.getStripePrice().getId());
//        System.out.println(p.getStripePrice().getCurrency());
//        System.out.println(p.getStripePrice().getUnitAmountDecimal());
//

////        PayPal tests
        Order testOrder = new PayPalOrder(p, 2);
        System.out.println(testOrder.getOrderID());
        System.out.println(testOrder.getOrderURL());
        System.out.println(testOrder.getType());

//        System.out.println(PayPalOrder.captureAnyOrder("5MT58586YM361081W"));

//
        //Stripe Tests
        StripeOrder order = new StripeOrder(p, 2);
        System.out.println(order.getOrderURL());

        //Square Tests
        SquareOrder s = new SquareOrder(p, 2);
        System.out.println(s.getOrderURL());
    }
}

