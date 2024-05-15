package com.example.demo13;

import java.util.ArrayList;

public class Board {
    private int boardId;
    public ArrayList<Column> columns;
    public ArrayList<User> users;

    // Constructor
    public Board(int boardId) {
        this.boardId = boardId;
    }

    // Getter for boardId
    public int getBoardId() { return boardId; }
}
