package com.example.mstdnResponseEntities;

public class Relationship extends Entity {
    public Relationship(String jsonString) {
        super(jsonString);
    }

    public String id;
    public boolean following;
    public boolean showing_reblogs;
    public boolean notifying;
    public boolean followed_by;
    public boolean blocking;
    public boolean blocked_by;
    public boolean muting;
    public boolean muting_notifications;
    public boolean requested;
    public boolean domain_blocking;
    public boolean endorsed;
    public String note;
}
