package org.pay.payfusion2;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Constants.init("EAAAly_Wmr4FT5kvz6SYyjh9vCeqxQdzW_5gP2GBX_vj8zUn39TU3PE6JsW2U6O7");
        Constants.print();

        //Master Product Tests
        MasterProductDefinition p = new MasterProductDefinition("T-Shirt", "A generic t-shirt. Nothing to look at here", 10.99, "usd", "price_1NU0mvBwTTupQWshXvKt6cQQ");
//        System.out.println(p.getName());
//        System.out.println(p.getDescription());
//        System.out.println(p.getPrice());
//        System.out.println(p.getStripeProduct().getId());
//        System.out.println(p.getStripeProduct().getName());
//        System.out.println(p.getStripeProduct().getUrl());
//        System.out.println(p.getStripePrice().getId());
//        System.out.println(p.getStripePrice().getCurrency());
//        System.out.println(p.getStripePrice().getUnitAmountDecimal());
//

////        PayPal tests
//        Order testOrder = new PayPalOrder(p, 2);
//        System.out.println(testOrder.getOrderID());
//        System.out.println(testOrder.getOrderURL());
//        System.out.println(testOrder.getType());

//        System.out.println(PayPalOrder.captureAnyOrder("83K10059S15968713"));

//
        //Stripe Tests
//        Order order = new StripeOrder(p, 2);
//        System.out.println(order.getOrderURL());

        //Square Tests
//        Order s = new SquareOrder(p, 2);
//        System.out.println(s.getOrderURL());
    }
}

