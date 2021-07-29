package com.example.mstdnResponseEntities;

public class History extends Entity {
    public History(String jsonString) {
        super(jsonString);
    }

    public String day;
    public String uses;
    public String accounts;
}
