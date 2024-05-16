package com.example.demo13;

import java.util.ArrayList;

public class Board {
    public static Board object;
    public String id;
    public ArrayList<Column> columns;
    public ArrayList<String> userEmails;

    public Board() {
        object = this;
    }

    // Constructor
    public Board(String id) {
        this.id = id;
        columns = new ArrayList<Column>();
        userEmails = new ArrayList<String>();

        System.out.println(id);
    }

    // Constructor for call using database
    public Board(String id, ArrayList<Column> columns, ArrayList<String> users, String... strings) {
        this.id = id;
        this.columns = columns;
        this.userEmails = users;
    }

    // Getter for boardId
    public String getBoardId() { return id; }
}
