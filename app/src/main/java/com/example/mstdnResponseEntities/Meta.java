package com.example.mstdnResponseEntities;

public class Meta extends Entity {
    public Meta(String jsonString) {
        super(jsonString);
    }

    public MetaData original;
    public MetaData small;
    String description;
    String blurhash;

    public class MetaData {
        public int width;
        public int height;
        public String size;
        public float aspect;

    }
}
