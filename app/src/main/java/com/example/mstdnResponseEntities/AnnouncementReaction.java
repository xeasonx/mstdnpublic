package com.example.mstdnResponseEntities;

public class AnnouncementReaction extends Entity {
    public AnnouncementReaction(String jsonString) {
        super(jsonString);
    }

    public String name;
    public int count;
    public boolean me;
    public String url;
    public String static_url;
}
