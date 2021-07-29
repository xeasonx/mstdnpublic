package com.example.mstdnResponseEntities;

import com.google.gson.Gson;

public class Entity {
    public String jsonString = "";

    public Entity() {};

    public Entity(String jsonString) {
        this.jsonString = jsonString;
    }

    public Entity toEntity() {
        Gson gson = new Gson();
        return gson.fromJson(this.jsonString, this.getClass());
    }
}
