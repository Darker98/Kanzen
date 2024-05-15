package com.example.demo13;

public class User {
    public static User object;
    private String id;
    private String name;
    private String email;
    private String status;
    private int boardId;

    User(String id, String name, String email, String status, int boardID) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.boardId = boardID;
        object = this;
    }

    // Getter for id
    public String getID() {
        return id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for boardId
    public int getBoardId() { return boardId; }
}
