package com.example.mstdnResponseEntities;

public class ScheduledStatus extends Entity {
    public ScheduledStatus(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String scheduled_at;
    public Status params;
    public Attachment[] media_attachments;
}
