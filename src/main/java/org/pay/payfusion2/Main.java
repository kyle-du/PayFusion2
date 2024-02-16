package org.pay.payfusion2;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Constants.init();
        Constants.print();

        //PayPal tests
//        PayPalOrder testOrder = new PayPalOrder("Kaival", "Sammy Shah's bro", 1, 999);
//        System.out.println(testOrder.getOrderLink() + " " + testOrder.getOrderID());
//
//        PayPalOrder.captureAnyOrder("4LJ092903D5166432");

        //Master Product Tests
        MasterProduct p = new MasterProduct("sammy", "bar", 10.3, "usd", "price_1OkLkkBwTTupQWshgfihj4ug");
        System.out.println(p.getName());
        System.out.println(p.getDescription());
        System.out.println(p.getPrice());
//        System.out.println(p.getStripeProduct().getId());
//        System.out.println(p.getStripeProduct().getName());
//        System.out.println(p.getStripeProduct().getUrl());
//        System.out.println(p.getStripePrice().getId());
//        System.out.println(p.getStripePrice().getCurrency());
//        System.out.println(p.getStripePrice().getUnitAmountDecimal());

        //Stripe Tests
        StripeOrder order = new StripeOrder(p, 1L);
        System.out.println(order.getCheckoutURL());
    }
}

