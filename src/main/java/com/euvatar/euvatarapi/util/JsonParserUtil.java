package com.euvatar.euvatarapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonParserUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonParserUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Parse a JSON string to a Java object.
     *
     * @param jsonString the JSON string to parse
     * @param clazz the class of the Java object
     * @param <T> the type of the Java object
     * @return the parsed Java object, or null if parsing fails
     */
    public static <T> T parseJson(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse JSON string: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Convert a Java object to a JSON string.
     *
     * @param object the Java object to convert
     * @return the JSON string, or null if conversion fails
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Failed to convert object to JSON string: {}", e.getMessage());
            return null;
        }
    }
}