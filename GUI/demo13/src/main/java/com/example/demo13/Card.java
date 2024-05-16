package com.example.demo13;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Date;

public class Card {
    private int id;
    private String title;
    private String description;
    private String status;
    private String label;
    private boolean blocked;
    private boolean urgent;
    private Date date;
    private Date dateCreated;
    private ArrayList<Member> assigned;

    // Constructor
    public Card(int id, String title, String description, String status,
                String label, boolean blocked, boolean urgent, Date date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.label = label;
        this.blocked = blocked;
        this.urgent = urgent;
        this.date = date;
        this.dateCreated = new Date(); // Automatically set to current creation date
        this.assigned = new ArrayList<Member>();
    }

    public Card(int id, String title, String status) {
    }


    public void setID(int id){
        this.id = id;
    }

    public int getID(){
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

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return date;
    }
    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public ArrayList<Member> getMembers() {
        return assigned;
    }


    public void addMember(Member member) {
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
        date = new Date(String.valueOf(card.date));
        assigned = new ArrayList<Member>(card.assigned); // TODO: Fix
    }
}