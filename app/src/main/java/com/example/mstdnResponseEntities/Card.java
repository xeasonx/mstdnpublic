package com.example.mstdnResponseEntities;

public class Card extends Entity {
    public Card(String jsonString) {
        super(jsonString);
    }

    public String url;
    public String title;
    public String description;
    public String type;
    public String author_name;
    public String author_url;
    public String provider_name;
    public String provider_url;
    public String html;
    public int width;
    public int height;
    public String image;
    public String embed_url;
    public String blurhash;
}
