package by.belstu.it.lyskov.service;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class TestDataReader {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(System.getProperty("environment"));

    public static String getTestData(String key) {
        return new String(resourceBundle.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
