package com.example.mstdnResponseEntities;

public class Preferences extends Entity {
    public Preferences(String jsonString) {
        super(jsonString);
    }

    public String postingDefaultVisibility;
    public boolean postingDefaultSensitive;
    public String postingDefaultLanguage;
    public String readingExpandMedia;
    public boolean readingExpandSpoilers;
}
