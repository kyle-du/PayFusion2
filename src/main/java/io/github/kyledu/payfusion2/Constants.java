package io.github.kyledu.payfusion2;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.stripe.Stripe;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Collection of constants to use for API requests
 */
public class Constants {
    public static String PYPL_CLIENT_ID;
    public static String PYPL_CLIENT_SECRET;
    public static String PYPL_ACCESS_TOKEN;

    public static String SQUARE_ACCESS_TOKEN;
    public static String LOCATION_ID;

    public static String CANCEL_URL;
    public static String RETURN_URL;

    /**
     * Initializes runtime constants from PayFusionConstants.json file
     *
     * @throws IOException
     * @throws ParseException
     */
    public static void init(String SQUARE_ACCESS_TOKEN) throws IOException, ParseException {
        InputStream stream = Constants.class.getClassLoader().getResourceAsStream("PayFusionConstants.json");

        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

        JSONObject a = (JSONObject) parser.parse(reader);

        //PayPal Constants
        JSONObject pypl = (JSONObject) a.get("PayPal");
        PYPL_CLIENT_ID = (String) pypl.get("PYPL_CLIENT_ID");
        PYPL_CLIENT_SECRET = (String) pypl.get("PYPL_CLIENT_SECRET");
        PYPL_ACCESS_TOKEN = OAuth.getPayPalAccessToken();

        //Stripe Constants
        JSONObject stripe = (JSONObject) a.get("Stripe");
        Stripe.apiKey = (String) stripe.get("STRIPE_KEY");

        //Square Constants
        Constants.SQUARE_ACCESS_TOKEN = SQUARE_ACCESS_TOKEN;
        JSONObject square = (JSONObject) a.get("Square");
        LOCATION_ID = (String) square.get("LOCATION_ID");

        //All Constants
        JSONObject all = (JSONObject) a.get("All");
        CANCEL_URL = (String) all.get("CANCEL_URL");
        RETURN_URL = (String) all.get("RETURN_URL");
    }

    public static void print() {
        System.out.println("PayPal Constants\n_________________");
        System.out.println("Client ID " + PYPL_CLIENT_ID);
        System.out.println("Client Secret " + PYPL_CLIENT_SECRET);
        System.out.println("Access Token " + PYPL_ACCESS_TOKEN + "\n");
        System.out.println("Stripe Constants\n_________________");
        System.out.println("API Key " + Stripe.apiKey + "\n");
        System.out.println("Square Constants\n_________________");
        System.out.println("Access Token " + SQUARE_ACCESS_TOKEN);
        System.out.println("Location ID " + LOCATION_ID + "\n");
        System.out.println("Other Constants\n_________________");
        System.out.println("Cancel URL " + CANCEL_URL);
        System.out.println("Return URL " + RETURN_URL);
    }
}
