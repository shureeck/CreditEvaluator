package org.example.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditUtils {
    public static <T> T readJson(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return clazz.cast(mapper.readValue(json, clazz));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
