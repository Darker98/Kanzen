package com.example.demo13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.models.*;

import com.example.demo13.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.demo13.Database;

import java.util.ArrayList;

public class AuthenticationCaller {
    public static void call(ArrayList<String> parameters, String procedure, String status) throws IllegalAccessException {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("src/main/java/com/example/demo13/bin/Auth.exe",
                    procedure);

            // Redirect the standard output stream to capture the output
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Get the input stream of the process
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read the output line by line
            String line;
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            System.out.println(lines);

            String email, id, name;
            email = null;
            id = null;
            name = null;

            // Read email and ID
            if (procedure.equals("login")) {
                try {
                    if (lines.get(1).equalsIgnoreCase("Success")) {
                        String json = lines.get(0);

                        // Parse JSON
                        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

                        // Get the givenName value
                        name = jsonObject.get("displayName").getAsString();
                        email = jsonObject.get("mail").getAsString();
                        id = jsonObject.get("id").getAsString();
                    }
                }
                catch (Exception e) {

                }
            }

            // You can optionally wait for the process to complete
            process.waitFor();

            // Access database if successful login
            if (id != null && email != null) {
                try {
                    Database.getUser(id, name, email, status);
                }
                catch (IllegalAccessException e) {
                    throw new IllegalAccessException();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            // Return id and email
//            if (procedure.equals("login")) {
//                parameters.add(id);
//                parameters.add(name);
//                parameters.add(email);
//            }
        }
        catch (IllegalAccessException e) {
            throw new IllegalAccessException();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Database.initialize();
        try {
            AuthenticationCaller.call(new ArrayList<String>(), "login", "Member");
        } catch (IllegalAccessException e) {

        }
    }
}