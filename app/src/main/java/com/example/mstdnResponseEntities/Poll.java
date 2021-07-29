package com.example.mstdnResponseEntities;

public class Poll extends Entity {
    public Poll(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String expires_at;
    public boolean expired;
    public boolean multiple;
    public int votes_count;
    public int voters_count;
    public boolean voted;
    public int[] own_votes;
    public PollOption options;
    public Emoji[] emojis;

    public static class PollOption {
        public String title;
        public int votes_count;
    }
}
