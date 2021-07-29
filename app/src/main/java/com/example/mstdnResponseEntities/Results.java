package com.example.mstdnResponseEntities;

public class Results extends Entity {
    public Results(String jsonString) {
        super(jsonString);
    }

    public Account[] accounts;
    public Status[] statuses;
    public Tag[] hashtags;
}
