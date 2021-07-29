package com.example.mstdnResponseEntities;

public class Instance extends Entity {
    public Instance(String jsonString) {
        super(jsonString);
    }

    public String uri;
    public String title;
    public String description;
    public String short_description;
    public String email;
    public String version;
    public String[] languages;
    public boolean registrations;
    public boolean approval_required;
    public boolean invites_enabled;
    public StreamingAPI urls;
    public Stats stats;
    public String thumbnail;
    public Account contact_account;

    public static class StreamingAPI {
        public String streaming_api;
    }

    public static class Stats {
        public int user_count;
        public int status_count;
        public int domain_count;
    }
}
