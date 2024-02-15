package org.pay.payfusion2;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Constants.init();
        Constants.print();
//        PayPalOrder testOrder = new PayPalOrder("Kaival", "Sammy Shah's bro", 1, 999);
//        System.out.println(testOrder.getOrderLink() + " " + testOrder.getOrderID());
//
        PayPalOrder.captureAnyOrder("4LJ092903D5166432");

    }
}

