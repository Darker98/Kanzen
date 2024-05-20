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
    private static List<SignalRMessageListener> listeners;

    public SignalRClient() {
        listeners = new ArrayList<>();
    }

    public void start() {
        hubConnection = HubConnectionBuilder.create("https://kanzen-signalrapp.azurewebsites.net/broadcastHub").build();

        // Register handler for incoming messages
        hubConnection.on("ReceiveMessage", (message) -> {
            System.out.println("Received message...");
            List<Integer> intMessage = new ArrayList<>();
            for (int integer : message) {
                intMessage.add(integer);
            }

            notifyListeners(intMessage);
        }, int[].class);

        hubConnection.start().blockingAwait();
        System.out.println("Connected to hub...");
    }

    public void stop() {
        if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            hubConnection.stop();
            System.out.println("Disconnected from SignalR Hub");
        }
    }

    public void sendMessage(String boardId, int[] numbers) {
        hubConnection.send("SendMessage", boardId, numbers);
        HelloApplication.messageSender = true;
        System.out.println("Message sent...");
    }

    public void joinGroup(String boardId) {
        hubConnection.send("JoinGroup", boardId);
        System.out.println("Joined group: " + boardId);
    }

    public void leaveGroup(String boardId) {
        hubConnection.send("LeaveGroup", boardId);
    }

    public void addMessageListener(SignalRMessageListener listener) {
        listeners.add(listener);
    }

    public void removeMessageListener(SignalRMessageListener listener) {
        listeners.remove(listener);
    }

    private static void notifyListeners(List<Integer> message) {
        for (SignalRMessageListener listener : listeners) {
            listener.onMessageReceived(message);
        }
    }

    public static void main(String[] args) {
//        start();
//        joinGroup("111");
//        int[] numbers = new int[3];
//        numbers[0] = 1;
//        numbers[1] = 2;
//        numbers[2] = 3;
//
//        sendMessage("111", numbers);
    }
}