package com.example.mstdnResponseEntities;

public class Attachment extends Entity {
    public Attachment(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String type;
    public String url;
    public String preview_url;
    public String remote_url;
    public String text_url;
    public Meta meta;
    public String description;
    public String blurhash;
}
