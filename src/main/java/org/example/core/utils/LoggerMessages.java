package org.example.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggerMessages {
    private static final ResourceBundle messages = ResourceBundle.getBundle("logMessages");

    public static String getMessage(String property, String... args) {
        if (args.length > 0) {
            return String.format(messages.getString(property), args);
        } else {
            return messages.getString(property);
        }
    }
}
