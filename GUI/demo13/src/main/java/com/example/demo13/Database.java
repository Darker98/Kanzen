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
                .endpoint("https://kanzen.documents.azure.com:443/")
                .key("cDnKbbnmKoLGMEVjP0Cr5lLTTJCxEAYhjGzpSX7mbDLoNTJRe7Vo9s4eUcKnpzb8azKA0e1tiY80ACDbdnozag==")
                .buildAsyncClient();

        // Get the Users container
        users = cosmosAsyncClient.getDatabase("Kanzen").getContainer("Users");

        // Get the Boards container
        boards = cosmosAsyncClient.getDatabase("Kanzen").getContainer("Boards");
    }
}
