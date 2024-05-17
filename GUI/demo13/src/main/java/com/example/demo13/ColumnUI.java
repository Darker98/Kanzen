package com.example.demo13;

import javafx.scene.control.Label;

public class ColumnUI extends Column {
    public Label header;

    ColumnUI(Column column) {
        super(column.getID(), column.getName(), column.getWip());
        header = new Label(column.getName());
    }

    public void setHeader(Label header) {
        this.header = header;
    }

    public Label getHeader() {
        return header;
    }
}
