package com.example.mstdnResponseEntities;

public class ReportAdmin extends Entity {
    public ReportAdmin(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String action_taken;
    public String comment;
    public String created_at;
    public String updated_at;
    public Account account;
    public Account target_account;
    public Account assigned_account;
    public String action_taken_by_account;
    public Status[] statuses;
}
