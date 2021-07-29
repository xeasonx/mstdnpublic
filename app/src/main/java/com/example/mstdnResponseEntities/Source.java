package com.example.mstdnResponseEntities;

public class Source extends Entity {
    public Source(String jsonString) {
        super(jsonString);
    }

    public String note;
    public Field[] fields;
    public String privacy;
    public boolean sensitive;
    public String language;
    public int follow_requests_count;
}
