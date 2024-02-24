package io.github.kyledu.payfusion2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.*;

/**
 * OAuth processes; currently only supporting PayPal access token creation,
 * others require manual input in PayFusionConstants.json file
 */
public class OAuth {

    public static String getPayPalAccessToken() {
        try {
            // Define OAuth 2.0 configuration
            String clientId = Constants.PYPL_CLIENT_ID;
            String clientSecret = Constants.PYPL_CLIENT_SECRET;
            String tokenEndpoint = "https://api-m.sandbox.paypal.com/v1/oauth2/token";

            // Create Request Body
            String credentials = clientId + ":" + clientSecret;
            String credentialsBase64 = Base64.getEncoder().encodeToString(credentials.getBytes());

            // Create the OAuth 2.0 token request
            String requestBody = "grant_type=client_credentials";
            byte[] postData = requestBody.getBytes("UTF-8");

            // Open a connection to the token endpoint
            URL url = new URL(tokenEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic " + credentialsBase64);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(postData.length));
            connection.setDoOutput(true);

            // Write the request body
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postData);
            }

            // Get the response from the authorization server
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                connection.disconnect();

                // Parse the response to obtain the access token
                JSONObject obj = new JSONObject(response.toString());
                String access_token = obj.getString("access_token");
                Constants.PYPL_ACCESS_TOKEN = access_token;
                return access_token;
            } else {
                System.err.println("Failed to obtain access token. Response Code: " + responseCode);
                connection.disconnect();
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}

