package org.pay.payfusion2;

import com.squareup.square.*;
import com.squareup.square.api.*;
import com.squareup.square.exceptions.*;
import com.squareup.square.models.*;
import com.squareup.square.models.Error;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

public class SquareOrder implements Order {
    private final MasterProductDefinition product;
    private final long quantity;
    private final String orderID; //square [order_id] property, not [id]
    private final String orderURL;
    private final PaymentType type = PaymentType.SQUARE;

    public SquareOrder(MasterProductDefinition product, long quantity) throws IOException {
        this.product = product;
        this.quantity = quantity;
        String[] responses = createOrder();
        this.orderID = responses[0];
        this.orderURL = responses[1];
    }

    public String[] createOrder() throws IOException {
        String[] responses = new String[2];

        URL url = new URL("https://connect.squareupsandbox.com/v2/online-checkout/payment-links");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Square-Version", "2024-01-18");
        httpConn.setRequestProperty("Authorization", "Bearer " + Constants.SQUARE_ACCESS_TOKEN);
        httpConn.setRequestProperty("Content-Type", "application/json");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());

        String input = "{\n" +
                "    \"order\": {\n" +
                "      \"location_id\": \"" + Constants.LOCATION_ID + "\",\n" +
                "      \"line_items\": [\n" +
                "        {\n" +
                "          \"quantity\": \"" + quantity + "\",\n" +
                "          \"base_price_money\": {\n" +
                "            \"amount\":" + (int) (product.getPrice() * 100) + ",\n" +
                "            \"currency\": \"" + product.getCurrencyCode().toUpperCase(Locale.ROOT) + "\"\n" +
                "          },\n" +
                "          \"name\": \"" + product.getName() + "\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    \"idempotency_key\": \"" + UUID.randomUUID() + "\",\n" +
                "    \"description\": \"" + product.getDescription() + "\",\n" +
                "    \"checkout_options\": {\n" +
                "      \"redirect_url\": \"" + Constants.RETURN_URL + "\"\n" +
                "    }\n" +
                "  }";
        writer.write(input);
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String httpResponse = s.hasNext() ? s.next() : "";

        System.out.println(httpResponse);
        JSONObject obj = new JSONObject(httpResponse.toString());
        responses[0] = obj.getJSONObject("payment_link").getString("order_id");
        responses[1] = obj.getJSONObject("payment_link").getString("url");
        return responses;
    }

    public String capture() {
        return "capture not required nor supported for Square";
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
