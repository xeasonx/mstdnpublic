package com.example.mstdnResponseEntities;

public class FeaturedTag extends Entity {
    public FeaturedTag(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String name;
    public String url;
    public int statuses_count;
    public String last_status_at;
}
