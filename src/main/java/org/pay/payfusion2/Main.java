package org.pay.payfusion2;

import com.google.gson.Gson;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(OAuth.getAccessToken());
        Order testOrder = new Order("Kaival", "Sammy Shah's bro", 1, 999);
        System.out.println(testOrder.getOrderLink());
    }
}

