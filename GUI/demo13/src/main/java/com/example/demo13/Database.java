package com.example.demo13;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.models.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Database {
    public static CosmosAsyncClient cosmosAsyncClient;
    public static CosmosAsyncContainer users;
    public static CosmosAsyncContainer boards;

    public static void initialize() {
        // Create CosmosAsyncClient
        cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint("https://kanzen.documents.azure.com:443/")
                .key("cDnKbbnmKoLGMEVjP0Cr5lLTTJCxEAYhjGzpSX7mbDLoNTJRe7Vo9s4eUcKnpzb8azKA0e1tiY80ACDbdnozag==")
                .buildAsyncClient();

        // Get the Users container
        users = cosmosAsyncClient.getDatabase("Kanzen").getContainer("Users");

        // Get the Boards container
        boards = cosmosAsyncClient.getDatabase("Kanzen").getContainer("Boards");
    }

    // Generate a random id between 0 and 999999999
    // Not completely perfect but extremely low chance of conflicts
    public static String generateId() {
        Random random = new Random();
        int randomInt = random.nextInt(999999999);
        return String.valueOf(randomInt);
    }

    public static void getUser(String id, String name, String email, String status) throws IllegalAccessException {
        // Query record
        String queryString = "SELECT * FROM c WHERE c.id = @id";
        List<SqlParameter> sqlParameters = Collections.singletonList(new SqlParameter("@id", id));
        SqlQuerySpec sqlQuerySpec = new SqlQuerySpec(queryString, sqlParameters);
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions()
                .setPartitionKey(new PartitionKey(id));
        List<User> queryResults = users.queryItems(sqlQuerySpec, queryOptions, User.class)
                .byPage().blockFirst().getResults();


        User user;
        // If not exist, make new user and write to database
        if (queryResults.isEmpty()) {
            Random random = new Random();
            int boardId_int = random.nextInt(999999999);
            String boardId = String.valueOf(boardId_int);
            user = new User(id, name, email, status, boardId);
            users.createItem(user).block();
        } else {
            // Record exists, read user
            user = queryResults.get(0);
        }

        System.out.println(User.object.id);

        // Get board associated with user
        getBoard(user.getBoardId(), user.getEmail());
    }

    public static void getBoard(String boardId, String email) throws IllegalAccessException {
        String queryString;
        List<SqlParameter> sqlParameters;
        SqlQuerySpec sqlQuerySpec;
        CosmosQueryRequestOptions queryOptions;
        List<Board> queryResult;

        System.out.println("Querying board");

        // Query the record
        // Query by id if Manager
        // Query by email if Member
        if (User.object.getStatus().equals("Manager")) {
            queryString = "SELECT * FROM c WHERE c.id = @id";
            sqlParameters = Collections.singletonList(new SqlParameter("@id", boardId));
            sqlQuerySpec = new SqlQuerySpec(queryString, sqlParameters);
            queryOptions = new CosmosQueryRequestOptions()
                    .setPartitionKey(new PartitionKey(boardId));
            queryResult = boards.queryItems(sqlQuerySpec, queryOptions, Board.class)
                    .byPage().blockFirst().getResults();
        } else {
            queryString = "SELECT * FROM c WHERE ARRAY_CONTAINS(c.users, @userEmail)";
            sqlParameters = Collections.singletonList(new SqlParameter("@userEmail", email));
            sqlQuerySpec = new SqlQuerySpec(queryString, sqlParameters);

            // Since we do not have a specific partition key to filter on, the partition key is omitted
                        queryOptions = new CosmosQueryRequestOptions();

            // Execute the query
                        queryResult = boards.queryItems(sqlQuerySpec, queryOptions, Board.class)
                                .byPage().blockFirst().getResults();
        }

        Board board;
        // If not exist
        if (queryResult.isEmpty()) {
            // Make a new board if it is a manager login
            System.out.println("Creating board");
            if (User.object.getStatus().equals("Manager")) {
                board = new Board(boardId);
                board.columns.add(new Column(Database.generateId(), "Backlog", 100));
                board.columns.add(new Column(Database.generateId(), "To Do", 100));
                board.columns.add(new Column(Database.generateId(), "In Flight", 10));
                board.columns.add(new Column(Database.generateId(), "Done", 100));
                //board.columns.get(0).cards.add(new Card("Test", "testing...", "To Do", "To Do", false, false, new Date()));
                board.userEmails.add(User.object.getEmail());

                System.out.println("Converting to JSON");
                // Convert the Board object to JSON using Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode boardJsonNode = objectMapper.valueToTree(board);

                System.out.println("Uploading board");
                // Use the JsonNode directly to create the item in Cosmos DB
                boards.createItem(boardJsonNode).block();
            } else {
                throw new IllegalAccessException("No board exists! Contact your manager to add you to one.");
            }
        } else {
            // If exists, read from database
            board = queryResult.get(0);

            System.out.println(board.userEmails);

            // Update user record
            User.object.boardId = board.getBoardId();
            CosmosItemRequestOptions requestOptions = new CosmosItemRequestOptions();
            CosmosItemResponse<User> response = users.replaceItem(User.object, User.object.getID(), new PartitionKey(User.object.getID()), requestOptions).block();
        }

        return;
    }

    public static synchronized void updateBoard() {
        Board board = Board.object;
        CosmosItemRequestOptions requestOptions = new CosmosItemRequestOptions();
        CosmosItemResponse<Board> response = boards.replaceItem(board, board.getBoardId(), new PartitionKey(board.getBoardId()), requestOptions).block();
    }
}