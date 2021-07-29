package com.example.mstdnResponseEntities;

public class Error extends Entity {
    public Error(String jsonString) {
        super(jsonString);
    }

    public String error;
    public String error_description;
}
