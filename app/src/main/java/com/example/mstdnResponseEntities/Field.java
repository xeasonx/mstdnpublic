package com.example.mstdnResponseEntities;

public class Field extends Entity {
    public Field(String jsonString) {
        super(jsonString);
    }

    public String name;
    public String value;
    public String verified_at;
}
