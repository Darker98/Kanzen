//package com.example.demo13;
//
//import jakarta.websocket.*;
//import java.net.URI;
//import java.util.Scanner;
//
//@ClientEndpoint
//public class WebSocketClient {
//
//    public static String boardId;
//
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("Connected to server");
//        // No need to send a SUBSCRIBE message manually here
//        // Subscription logic will be handled by the Spring Boot server
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//        System.out.println("Received: " + message);
//    }
//
//    @OnClose
//    public void onClose(Session session, CloseReason closeReason) {
//        System.out.println("Disconnected: " + closeReason.getReasonPhrase());
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        System.out.println("Error: " + throwable.getMessage());
//    }
//
//    public static void main(String[] args) {
//        // Get the boardId dynamically, here it's hardcoded for simplicity
//        boardId = "642345";
//
//        try {
//            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//            container.connectToServer(WebSocketClient.class, URI.create("ws://kanzen-websocket.azurewebsites.net"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}