package org.example;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

public class BoardHub extends Hub {
    // Clients call this method to send updates
    public void NotifyBoardUpdate(String boardId) {
        // Broadcast the boardId to all clients with the same boardId
        Clients.Group(boardId).send("ReceiveBoardUpdate", boardId);
    }

    // Clients join the group based on their boardId
    public Task JoinBoardGroup(String boardId) {
        return Groups.add(Context.ConnectionId, boardId);
    }
}