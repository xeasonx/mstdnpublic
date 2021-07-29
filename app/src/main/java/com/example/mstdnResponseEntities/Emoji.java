package com.example.mstdnResponseEntities;

public class Emoji extends Entity {
    public Emoji(String jsonString) {
        super(jsonString);
    }

    public String shortcode;
    public String url;
    public String static_url;
    public boolean visible_in_picker;
    public String category;
}
