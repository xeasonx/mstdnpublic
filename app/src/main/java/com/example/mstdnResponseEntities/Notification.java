package com.example.mstdnResponseEntities;

public class Notification extends Entity {
    public Notification(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String type;
    public String created_at;
    public Account account;
    public Status status;
}
