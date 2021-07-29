package com.example.mstdnResponseEntities;

public class Account extends Entity {
    public Account(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String username;
    public String acct;
    public String display_name;
    public boolean locked;
    public boolean bot;
    public String created_at;
    public String note;
    public String url;
    public String avatar;
    public String avatar_static;
    public String header;
    public String header_static;
    public int followers_count;
    public int following_count;
    public int statuses_count;
    public String last_status_at;
    public boolean discoverable;
    public Emoji[] emojis;
    public Field[] fields;
    public Account moved;
    public boolean suspend;
    public String mute_expires_at;
    public Source source;
}
