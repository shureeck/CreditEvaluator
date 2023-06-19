package org.example.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggerMessages {
    private static final ResourceBundle messages = ResourceBundle.getBundle("logMessages");

    public static String getMessage(String property, String... args) {
        String message;
        try {
            message = messages.getString(property);
        } catch (MissingResourceException e) {
            return "Can't find appropriate message resource for property " + property + " in the ResourceBundle";
        }
        if (args.length > 0) {
            return String.format(message, args);
        } else {
            return message;
        }
    }
}
