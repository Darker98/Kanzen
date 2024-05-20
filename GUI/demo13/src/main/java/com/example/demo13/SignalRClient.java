package com.example.demo13;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

public class SignalRClient {

    public static void main(String[] args) throws Exception {
        HubConnection hubConnection = HubConnectionBuilder.create("https://kanzen-signalr.service.signalr.net")
                .build();
    }
}