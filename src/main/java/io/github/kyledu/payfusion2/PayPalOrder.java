package org.pay.payfusion2;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

/**
 * defines a PayPal Order object with its checkout URL
 */
class PayPalOrder implements Order {

    private final MasterProductDefinition product;
    private final long quantity;
    private final String orderID;
    private final String orderURL;
    private final PaymentType type = PaymentType.PAYPAL;


    public PayPalOrder(MasterProductDefinition product, long quantity) throws IOException {
        this.product = product;
        this.quantity = quantity;
        String[] responses = createOrder();
        this.orderID = responses[0];
        this.orderURL = responses[1];
    }

    private String[] createOrder() throws IOException {
        String[] responses = new String[2];
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer " + Constants.PYPL_ACCESS_TOKEN);

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        String input = "{\n" +
                "    \"intent\": \"CAPTURE\",\n" +
                "    \"purchase_units\": [\n" +
                "        {\n" +
                "            \"items\": [\n" +
                "                {\n" +
                "                    \"name\": \"" + product.getName() + "\",\n" +
                "                    \"description\": \"" + product.getDescription() + "\",\n" +
                "                    \"quantity\": \"" + quantity + "\",\n" +
                "                    \"unit_amount\": {\n" +
                "                        \"currency_code\": \"USD\",\n" +
                "                        \"value\": \"" + product.getPrice() + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "            \"amount\": {\n" +
                "                \"currency_code\": \"" + product.getCurrencyCode().toUpperCase(Locale.ROOT) + "\",\n" +
                "                \"value\": \"" + product.getPrice() * quantity + "\",\n" +
                "                \"breakdown\": {\n" +
                "                    \"item_total\": {\n" +
                "                        \"currency_code\": \"USD\",\n" +
                "                        \"value\": \"" + product.getPrice() * quantity + "\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "\"payment_source\": { \"paypal\": { \"experience_context\": { \"payment_method_preference\": " +
                "\"IMMEDIATE_PAYMENT_REQUIRED\", \"brand_name\": \"EXAMPLE INC\", \"locale\": \"en-US\", \"landing_page\": " +
                "\"LOGIN\", \"shipping_preference\": \"GET_FROM_FILE\", \"user_action\": \"PAY_NOW\", \"return_url\": " +
                "\"" + Constants.RETURN_URL + "\", \"cancel_url\": \"" + Constants.CANCEL_URL + "\" } } }" +
//                "    \"application_context\": {\n" +
//                "        \"return_url\": \"https://example.com/return\",\n" +
//                "        \"cancel_url\": \"https://example.com/cancel\"\n" +
//                "    }\n" +
                "}";
        writer.write(input);
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String httpResponse = s.hasNext() ? s.next() : "";

        JSONObject obj = new JSONObject(httpResponse.toString());
        responses[0] = obj.getString("id");
        responses[1] = obj.getJSONArray("links").getJSONObject(1).getString("href");
        return responses;
    }

    /**
     * for capturing a completed PayPal order
     *
     * @return String success/fail message
     */
    public String capture() {
        try {
            URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + this.orderID + "/capture");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Authorization", "Bearer " + Constants.PYPL_ACCESS_TOKEN);

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";

        } catch (IOException e) {
            return "capture fail";
        }
        return "capture success";
    }

    //for testing
    public static String[] captureAnyOrder(String orderID) throws IOException {
        URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderID + "/capture");
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
        return placeholder;
    }

    public MasterProductDefinition getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderURL() {
        return orderURL;
    }

    public PaymentType getType() {
        return type;
    }
}

