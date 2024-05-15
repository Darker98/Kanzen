package com.example.demo13;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.CosmosClientBuilder;

public class Database {
    public static CosmosAsyncClient cosmosAsyncClient;
    public static CosmosAsyncContainer users;
    public static CosmosAsyncContainer boards;

    public static void initialize() {
        // Create CosmosAsyncClient
        cosmosAsyncClient = new CosmosClientBuilder()
                .endpoint("your_endpoint")
                .key("your_key")
                .buildAsyncClient();

        // Get the Users container
        users = cosmosAsyncClient.getDatabase("Kanzen").getContainer("Users");

        // Get the Boards container
        boards = cosmosAsyncClient.getDatabase("Kanzen").getContainer("Boards");
    }
}
