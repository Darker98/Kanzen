package com.example.demo13;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.microsoft.signalr.OnClosedCallback;
import io.reactivex.Completable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SignalRClient {

    private static HubConnection hubConnection;

    public static void start() {
        hubConnection = HubConnectionBuilder.create("https://kanzen-signalrapp.azurewebsites.net/broadcastHub").build();

        // Register handler for incoming messages
        hubConnection.on("ReceiveMessage", (message) -> {
            System.out.println("Received message: " + message);
            // Process your incoming message here
        }, int[].class);

        hubConnection.start().blockingAwait();
        System.out.println("Connected to hub...");
    }

    public static void stop() {
        if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            hubConnection.stop();
            System.out.println("Disconnected from SignalR Hub");
        }
    }

    public static void sendMessage(String boardId, int[] numbers) {
        hubConnection.send("SendMessage", boardId, numbers);
        System.out.println("Message sent...");
    }

    public static void joinGroup(String boardId) {
        hubConnection.send("JoinGroup", boardId);
        System.out.println("Joined group: " + boardId);
    }

    public static void leaveGroup(String boardId) {
        hubConnection.send("LeaveGroup", boardId);
    }

    public static void main(String[] args) {
        start();
        joinGroup("111");
        int[] numbers = new int[3];
        numbers[0] = 1;
        numbers[1] = 2;
        numbers[2] = 3;

        sendMessage("111", numbers);
    }
}