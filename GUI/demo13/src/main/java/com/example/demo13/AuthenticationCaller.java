package com.example.demo13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;

import com.azure.cosmos.models.PartitionKey;
import com.example.demo13.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.demo13.Database;


public class AuthenticationCaller {
    public static void call(ArrayList<String> parameters, String procedure, String status) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("src/main/java/com/example/demo13/bin/Debug/net8.0-windows/Auth.exe",
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
                    // Create CosmosAsyncClient
                    CosmosAsyncClient cosmosAsyncClient = Database.cosmosAsyncClient;

                    // Create Users container
                    CosmosAsyncContainer users = Database.users;

                    // Create Boards container
                    CosmosAsyncContainer boards = Database.boards;

                    // Query if record exists
                    String queryString = "SELECT * FROM c WHERE c.id = @id";
                    CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions()
                            .setPartitionKey(new PartitionKey(id));
                    List<User> queryResults = users.queryItems(queryString, queryOptions, User.class)
                            .byPage().blockFirst().getResults();

                    // If record does not exist, create
                    if (queryResults.isEmpty()) {
                        Random random = new Random();
                        int boardId = random.nextInt(999999999);
                        User user = new User(id, name, email, status, boardId);
                        users.createItem(user).block();

                        Board board = new Board(boardId);
                        board.columns.add(new Column("To Do", 100));
                        board.columns.add(new Column("In Flight", 10));
                        board.columns.add(new Column("Done", 100));
                        board.users.add(user);

                        boards.createItem(board).block();
                    } else {
                        // Record exists, handle accordingly
                        User user = queryResults.get(0);

                        // Query if record exists
                        queryString = String.format("SELECT * FROM c WHERE c.id = {}", user.getBoardId());
                        queryOptions = new CosmosQueryRequestOptions()
                                .setPartitionKey(new PartitionKey(id));
                        List<Board> queryResult = boards.queryItems(queryString, queryOptions, Board.class)
                                .byPage().blockFirst().getResults();

                        Board board = queryResult.get(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Return id and email
            if (procedure.equals("login")) {
                parameters.add(id);
                parameters.add(name);
                parameters.add(email);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AuthenticationCaller.call(new ArrayList<String>(), "login", "Member");
    }
}