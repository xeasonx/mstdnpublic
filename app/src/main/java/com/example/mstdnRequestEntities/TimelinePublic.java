package com.example.mstdnRequestEntities;

public class TimelinePublic extends ParamEntity {
    public boolean local = false;
    public boolean remote = false;
    public boolean only_media = false;
    public String max_id = null;
    public String since_id = null;
    public String min_id = null;
    public int limit = 20;

    public void setLocal(boolean local) {
        this.local = local;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setMax_id(String max_id) {
        this.max_id = max_id;
    }

    public void setOnly_media(boolean only_media) {
        this.only_media = only_media;
    }

    public void setMin_id(String min_id) {
        this.min_id = min_id;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public void setSince_id(String since_id) {
        this.since_id = since_id;
    }
}
