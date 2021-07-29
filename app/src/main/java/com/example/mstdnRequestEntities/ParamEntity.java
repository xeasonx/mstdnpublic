package com.example.mstdnRequestEntities;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ParamEntity {
    public String toParam(Object o, String delimiter) throws Exception {
        ArrayList<String> queryArray = new ArrayList<>();
        Field[] fields = o.getClass().getFields();

        for (Field field : fields) {
            String key = field.getName();
            Object value = field.get(o.getClass().newInstance());
            if (value != null) {
                queryArray.add(key + "=" + value.toString());
            }
        }

        return String.join(delimiter, queryArray);
    }
}
