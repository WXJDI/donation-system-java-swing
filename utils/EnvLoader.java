package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    private static final Map<String, String> ENV_VARS = new HashMap<>();

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comments and blank lines
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                // Split the line into key and value
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    ENV_VARS.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file", e);
        }
    }

    public static String get(String key) {
        return ENV_VARS.get(key);
    }
}
