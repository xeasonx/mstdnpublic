package com.example.mstdnResponseEntities;

public class Context extends Entity {
    public Context(String jsonString) {
        super(jsonString);
    }

    public Status[] ancestors;
    public Status[] descendants;
}
