package com.example.demo13;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class SignalRClient {
    private HubConnection hubConnection;

    public SignalRClient(String hubUrl) {
        this.hubConnection = HubConnectionBuilder.create(hubUrl).build();
        this.hubConnection.start().blockingAwait();
    }

    public void sendBoardUpdate(String boardId) {
        this.hubConnection.send("NotifyBoardUpdate", boardId);
    }

    public void updateBoardAndNotify(String boardId, Board updatedBoard) {
        Database.updateBoard();

        // Trigger SignalR notification
        SignalRClient signalRClient = new SignalRClient("http://<your-signalr-server>/hub");
        signalRClient.sendBoardUpdate(boardId);
    }
}