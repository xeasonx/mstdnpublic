package com.example.mstdnResponseEntities;

public class Status extends Entity {
    public Status() {};
    public Status(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String uri;
    public String created_at;
    public Account account;
    public String content;
    public String visibility;
    public boolean sensitive;
    public String spoiler_text;
    public Attachment[] media_attachments;
    public Application application;
    public Mention[] mentions;
    public Tag[] tags;
    public Emoji[] emojis;
    public int reblogs_count;
    public int favourites_count;
    public int replies_count;
    public String url;
    public String in_reply_to_id;
    public String in_reply_to_account_id;
    public Status reblog;
    public Poll poll;
    public Card card;
    public String language;
    public String text;
    public boolean favourited;
    public boolean reblogged;
    public boolean muted;
    public boolean bookmarked;
    public boolean pinned;
}
