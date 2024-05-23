package com.example.demo13;

public class User {
    public static User object;
    public String id;
    public String name;
    public String email;
//    public String status;
    public String boardId;

    User() {
        object = this;
    }

    User(String id, String name, String email, String boardID) {
        this.id = id;
        this.name = name;
        this.email = email;
//        this.status = status;
        this.boardId = boardID;
        object = this;
    }

    User(String id, String name, String email, String status, String boardID, String... strings) {
        this(id, name, email, boardID);
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
    public String getBoardId() { return boardId; }

//    // Getter for status
//    public String getStatus() { return status; }
//
//    // Setter for status
//    public void setStatus(String status) { this.status = status; }
}
