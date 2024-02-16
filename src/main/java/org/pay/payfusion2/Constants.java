package org.pay.payfusion2;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.stripe.Stripe;

import java.io.FileReader;
import java.io.IOException;

public class Constants {
    public static String PYPL_CLIENT_ID;
    public static String PYPL_CLIENT_SECRET;
    public static String PYPL_ACCESS_TOKEN;

    public static void init() throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        JSONObject a = (JSONObject) parser.parse(new FileReader("constants.json"));

        //PayPal Constants
        JSONObject pypl = (JSONObject) a.get("PayPal");
        PYPL_CLIENT_ID = (String) pypl.get("PYPL_CLIENT_ID");
        PYPL_CLIENT_SECRET = (String) pypl.get("PYPL_CLIENT_SECRET");
        PYPL_ACCESS_TOKEN = OAuth.getAccessToken();

        //Stripe Constants
        JSONObject stripe = (JSONObject) a.get("Stripe");
        Stripe.apiKey = (String) stripe.get("STRIPE_KEY");
    }

    public static void print() {
        System.out.println(PYPL_CLIENT_ID);
        System.out.println(PYPL_CLIENT_SECRET);
        System.out.println(PYPL_ACCESS_TOKEN);
        System.out.println(Stripe.apiKey);
    }
}
