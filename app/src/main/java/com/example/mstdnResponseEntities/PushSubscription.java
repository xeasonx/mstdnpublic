package com.example.mstdnResponseEntities;

public class PushSubscription extends Entity {
    public PushSubscription(String jsonString) {
        super(jsonString);
    }

    public String id;
    public String endpoint;
    public String server_key;
    public Alerts alerts;

    public static class Alerts {
        public boolean follow;
        public boolean favourite;
        public boolean mention;
        public boolean reblog;
        public boolean poll;
    }
}
