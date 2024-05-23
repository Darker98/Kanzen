package com.example.demo13;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ColumnUI extends Column {
    public Label header;
    public HBox hbox;


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
