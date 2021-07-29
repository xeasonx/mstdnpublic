package com.example.mstdnResponseEntities;

public class Marker extends Entity {
    public Marker(String jsonString) {
        super(jsonString);
    }

    public MarkerNote home;
    public MarkerNote notifications;

    public static class MarkerNote {
        public String last_read_id;
        public int version;
        public String updated_at;
    }
}
