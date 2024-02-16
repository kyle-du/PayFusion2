package org.pay.payfusion2;

//TODO: Reconfigure to match Stripe Product-Session model

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class PayPalOrder {
    private final String name;
    private final String description;
    private final int quantity;
    private final double value;
    private final String orderLink;
    private final String orderID;

    public PayPalOrder(String name, String description, int quantity, int value) throws IOException {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.value = value;
        String[] responses = createOrder();
        orderID = responses[0];
        orderLink = responses[1];
    }

    public String[] createOrder() throws IOException {
        String[] responses = new String[2];
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Content-Type", "application/json");
        //TODO: Check if access token is expired
        httpConn.setRequestProperty("Authorization", "Bearer " + Constants.PYPL_ACCESS_TOKEN);

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        //TODO: Dynamic order creation
        writer.write("{\n" +
                "    \"intent\": \"CAPTURE\",\n" +
                "    \"purchase_units\": [\n" +
                "        {\n" +
                "            \"items\": [\n" +
                "                {\n" +
                "                    \"name\": \"" + name + "\",\n" +
                "                    \"description\": \"" + description + "\",\n" +
                "                    \"quantity\": \"" + quantity + "\",\n" +
                "                    \"unit_amount\": {\n" +
                "                        \"currency_code\": \"USD\",\n" +
                "                        \"value\": \"" + value + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "            \"amount\": {\n" +
                "                \"currency_code\": \"USD\",\n" +
                "                \"value\": \"" + value + "\",\n" +
                "                \"breakdown\": {\n" +
                "                    \"item_total\": {\n" +
                "                        \"currency_code\": \"USD\",\n" +
                "                        \"value\": \"" + value + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "\"payment_source\": { \"paypal\": { \"experience_context\": { \"payment_method_preference\": \"IMMEDIATE_PAYMENT_REQUIRED\", \"brand_name\": \"EXAMPLE INC\", \"locale\": \"en-US\", \"landing_page\": \"LOGIN\", \"shipping_preference\": \"GET_FROM_FILE\", \"user_action\": \"PAY_NOW\", \"return_url\": \"https://example.com/returnUrl\", \"cancel_url\": \"https://example.com/cancelUrl\" } } }" + //TODO: placeholder
//                "    \"application_context\": {\n" +
//                "        \"return_url\": \"https://example.com/return\",\n" +
//                "        \"cancel_url\": \"https://example.com/cancel\"\n" +
//                "    }\n" +
                "}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";

        JSONObject obj = new JSONObject(response.toString());
        System.out.println(response);
        responses[0] = obj.getString("id");
        responses[1] = obj.getJSONArray("links").getJSONObject(1).getString("href");
        return responses;
    }

    public static String[] captureAnyOrder(String id) throws IOException {
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + id + "/capture");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer " + Constants.PYPL_ACCESS_TOKEN);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";

        String[] placeholder = {""};
        System.out.println(response);
        return placeholder;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getQuantity() {
        return quantity;
    }

    public double getValue() {
        return value;
    }

    public String getOrderLink() {
        return orderLink;
    }

    public String getOrderID() {
        return orderID;
    }
}

