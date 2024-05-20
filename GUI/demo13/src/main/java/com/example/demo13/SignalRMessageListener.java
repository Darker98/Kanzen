package com.example.demo13;

import java.util.List;

public interface SignalRMessageListener {
    void onMessageReceived(List<Integer> message);
}
