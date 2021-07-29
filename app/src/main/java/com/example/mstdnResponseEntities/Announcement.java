package com.example.mstdnResponseEntities;

public class Announcement extends Entity {
    public Announcement(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String text;
    public boolean published;
    public boolean all_day;
    public String created_at;
    public String updated_at;
    public boolean read;
    public AnnouncementReaction[] reactions;
    public String scheduled_at;
    public String starts_at;
    public String ends_at;
}
