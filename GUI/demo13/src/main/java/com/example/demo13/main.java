package com.example.demo13;

import com.example.demo13.HelloApplication;
import javafx.application.Application;

public class main {
    public static void main(String[] args) {
        Database.initialize();
        Application.launch(HelloApplication.class);
    }
}
