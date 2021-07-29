package com.example.mstdnResponseEntities;

public class List extends Entity {
    public List(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String title;
    public String replies_policy;
}
