package com.example.mstdnResponseEntities;

public class Application extends Entity {
    public Application(String jsonString) {
        super(jsonString);
    }

    public String name;
    public String website;
    public String vapid_key;
    public String client_id;
    public String client_secret;
}
