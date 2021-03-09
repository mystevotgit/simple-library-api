package com.accessment.library.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonString {

    private JsonString() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }catch(Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
