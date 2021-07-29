package com.example.mstdnApi;

public class MSTDNRestfulRegister {
    public String name;
    public String path;
    public RequestEntity[] requestEntities;
    public String method;
    public Header[] headers;

    public class Header {
        public String key;
        public String value;
    }

    public class RequestEntity {
        public String entity;
        public String type;
        public String contentType;
    }
}
