package com.example.mstdnResponseEntities;

public class Conservation extends Entity {
    public Conservation(String jsonString) {
        super(jsonString);
    }

    public String id;
    public Account[] accounts;
    public boolean unread;
    public Status last_status;
}
