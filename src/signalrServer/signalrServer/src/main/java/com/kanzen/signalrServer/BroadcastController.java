package com.kanzen.signalrServer;

import com.azure.resourcemanager.signalr.SignalRManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class BroadcastController {

    @Autowired
    private SignalRManager signalRManager;

    @PostMapping("/broadcast")
    public ResponseEntity<String> broadcastData(@RequestBody BroadcastMessage message) {
        try {
            Connection connection = signalRManager.getConnection(); // Assuming getConnection exists
            connection.sendAsync("boardUpdate", message.getBoardId(), message.getIntegerList());
            return ResponseEntity.ok("Message broadcast successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

class BroadcastMessage {
    private String boardId;
    private List<Integer> integerList;

    // Getters and Setters
    public String getBoardId() {
        return boardId;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }
}