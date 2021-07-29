package com.example.mstdnResponseEntities;

public class Token extends Entity {
    public Token(String jsonString) {
        super(jsonString);
    }

    public String access_token;
    public String token_type;
    public String scope;
    public int created_at;
}
