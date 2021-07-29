package com.example.mstdnResponseEntities;

public class IdentityProof extends Entity {
    public IdentityProof(String jsonString) {
        super(jsonString);
    }

    public String provider;
    public String provider_username;
    public String updated_at;
    public String proof_url;
    public String profile_url;
}
