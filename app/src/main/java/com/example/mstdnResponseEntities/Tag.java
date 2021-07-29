package com.example.mstdnResponseEntities;

public class Tag extends Entity {
    public Tag(String jsonString) {
        super(jsonString);
    }

    public String name;
    public String url;
    public History[] history;
}
