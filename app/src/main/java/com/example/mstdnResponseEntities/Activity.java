package com.example.mstdnResponseEntities;

public class Activity extends Entity {
    public Activity(String jsonString) {
        super(jsonString);
    }

    public String week;
    public String statuses;
    public String logins;
    public String registrations;
}
