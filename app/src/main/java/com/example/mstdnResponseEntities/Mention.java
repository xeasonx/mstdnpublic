package com.example.mstdnResponseEntities;

public class Mention extends Entity {
    public Mention(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String username;
    public String url;
    public String acct;
}
