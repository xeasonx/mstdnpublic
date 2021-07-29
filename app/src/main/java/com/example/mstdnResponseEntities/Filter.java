package com.example.mstdnResponseEntities;

public class Filter extends Entity {
    public Filter(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String phrase;
    public String[] context;
    public String expires_at;
    public boolean irreversible;
    public boolean whole_word;

}
