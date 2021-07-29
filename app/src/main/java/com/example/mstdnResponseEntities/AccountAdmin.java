package com.example.mstdnResponseEntities;

public class AccountAdmin extends Entity {
    public AccountAdmin(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String username;
    public String domain;
    public String created_at;
    public String email;
    public String ip;
    public String locale;
    public String invite_request;
    public String role;
    public boolean confirmed;
    public boolean approved;
    public boolean disabled;
    public boolean silenced;
    public boolean suspended;
    public Account account;
    public String created_by_application_id;
    public String invited_by_account_id;
}
