package org.pay.payfusion2;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        // JSON data as a string
        String json = "{\"firstName\":\"Alice\",\"lastName\":\"Smith\"}";

        // Initialize Gson
        Gson gson = new Gson();

        // Deserialize JSON to a Person object
        Person person = gson.fromJson(json, Person.class);

        // Access the deserialized data
        System.out.println("First Name: " + person.getFirstName());
        System.out.println("Last Name: " + person.getLastName());
    }
}

