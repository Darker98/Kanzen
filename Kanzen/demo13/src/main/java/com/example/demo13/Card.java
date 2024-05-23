package com.example.demo13;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Card {
    public static int id_tracker = 0;
    public String id;
    public String title;
    public String description;
    public String status;
    public String label;
    public boolean blocked;
    public boolean urgent;
    public LocalDate date;
    public Date dateCreated;
    public ArrayList<User> assigned;

    public String Email_address;



    // No arg constructor
    public Card() { }

    // Constructor
    public Card(String id, String title, String description, String status,
                String label, boolean blocked, boolean urgent, LocalDate date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.label = label;
        this.blocked = blocked;
        this.urgent = urgent;
        this.date = date;
        this.dateCreated = new Date(); // Automatically set to current creation date
        this.assigned = new ArrayList<User>();
    }

    public Card(String id, String title, String status, LocalDate date) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.date = date;
    }


    public void setID(String id){
        this.id = id;
    }

    public String getID(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;

    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setStatus(String status){
        this.status= status;
    }

    public String getStatus(){
        return status;
    }

    public void setLabel(String label){
        this.label= label;
    }

    public String getLabel(){
        return label;
    }

    public void setBlocked (boolean blocked){
        this.blocked = blocked;
    }

    public boolean getBlocked(){
        return blocked;
    }

    public void setUrgent (boolean urgent){
        this.urgent= urgent;
    }

    public boolean getUrgent(){
        return urgent;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalDate getDate(){
        return date;
    }
    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public void setEmail_address(String Email_address){
        this.Email_address =  Email_address;
    }

    public String getEmail_address(){
        return Email_address;
    }

    public ArrayList<User> getMembers() {
        return assigned;
    }


    public void addMember(User member) {
        assigned.add(member);
    }

    public void deleteMember(int index) {
        if (assigned == null || index < 0 || index >= assigned.size()) {
            return;  // for invalid index
        }
        assigned.remove(index);
    }

    public void updateCard(Card card) {
        id = card.id;
        title = new String(card.title);
        description = new String(card.description);
        status = new String(card.status);
        label = new String(card.label);
        blocked = card.blocked;
        urgent = card.urgent;
        date = card.date;
        assigned = new ArrayList<User>(card.assigned); // TODO: Fix
    }
}