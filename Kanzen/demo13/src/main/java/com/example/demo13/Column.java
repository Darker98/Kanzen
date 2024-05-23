package com.example.demo13;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class Column {
    public String id;
    public String name;
    public int wip;
    //public int cardAmount;
    public ArrayList<Card> cards;
    //private ArrayList<Swimlane> swimlanes;
//    public String header;


    public Column() {
        cards = new ArrayList<Card>();
    }

    // Constructor
    Column(String id, String name, int wip) {
        // Initialize values
        this.id = id;
        this.name = name;
        this.wip = wip;
        //cardAmount = 0;

        // Initialize arrays
        cards = new ArrayList<Card>();
//        header = new Label(name);
       // swimlanes = new ArrayList<Swimlane>();
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for wip
    public void setWip(int wip) {
        this.wip = wip;
    }

    // Getter for wip
    public int getWip() {
        return wip;
    }

    // Setter for id
    public void setID(String id) {
        this.id = id;
    }

    // Getter for id
    public String getID() {
        return id;
    }

    // Getter for cardsAmount
//    public int getCardAmount() {
//        return cardAmount;
//    }

//    // Add a card
//    public void addCard(Card card) {
//        cards.add(card);
//        cardAmount++;
//    }
//    public Label getHeader(){
//        return header;
//    }
//
//    public void setHeader(Label header) {
//        this.header = header;
//    }

//    // Delete a card
//    public void deleteCard(String id) throws Exception {
//        // Loop through array and remove card if id matches
//        for (int i = 0; i < cardAmount; i++) {
//            if (cards.get(i).getID() == id) {
//                cards.remove(i);
//                cardAmount--;
//                return;
//            }
//        }
//
//        throw new Exception("Invalid ID provided to deleteCard...");
//    }

//    // Get a card
//    public Card getCard(String id) throws Exception {
//        // Loop through array and return card if id matches
//        for (int i = 0; i < cardAmount; i++) {
//            if (cards.get(i).getID() == id)
//                return cards.get(i);
//        }
//
//        throw new Exception("Invalid ID provided to getCard...");
//    }

    // Add a swimlane
//    public void addSwimlane(Swimlane swimlane) {
//        swimlanes.add(swimlane);
//    }

    // Delete a swimlane
//    public void deleteSwimlane(int id) {
//        // Loop through array and delete swimlane if id matches
//        for (int i = 0; i < swimlanes.size(); i++) {
//            if (swimlanes.get(i).getID() == id) {
//                swimlanes.remove(i);
//                return;
//            }
//        }
//
//        throw new Exception("Invalid ID provided to deleteSwimlane...");
//    }

    // Get a swimlane
//    public Swimlane getSwimlane(int id) {
//        // Loop through array and return if id matches
//        for (int i = 0; i < swimlanes.size(); i++) {
//            if (swimlanes.get(i).getID() == id) {
//                return swimlanes.get(i);
//            }
//        }
//
//        throw new Exception("Invalid ID provided to getSwimlane...");
//    }

    
    /* // TODO: Create methods
        - updateCard
        - updateSwimlane
    */
}
