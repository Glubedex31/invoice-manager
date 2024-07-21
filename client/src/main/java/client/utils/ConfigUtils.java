package client.utils;

import java.io.*;
import java.util.Properties;
import org.springframework.stereotype.Service;

@Service
public class ConfigUtils {
    private final String configFile = "config.properties";
    private final String configPath = "src/main/resources/" + configFile;
    private Properties properties = new Properties();

    /**
     * Constructor for the ConfigUtils class.
     *
     * @param properties the properties to set
     */
    public ConfigUtils(Properties properties) {
        this.properties = properties;
    }

    /**
     * Constructor for the ConfigUtils class.
     */
    public ConfigUtils() {
    }

    /**
     * Loads the config file.
     */
    public void initialize() {
        try (InputStream is = ConfigUtils.class.getClassLoader().getResourceAsStream(configFile)) {
            if (is != null) {
                properties.load(is);
            } else {
                throw new FileNotFoundException("config.properties not found in the classpath");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the language from the config file.
     *
     * @return language stored in config file, english if none found
     */
    public String getLanguage() {
        return switch (properties.getProperty("language")) {
            case "English" -> "English";
            case "Romanian" -> "Romanian";
            default -> "English";
        };
    }

    /**
     * Writes the language to the config file.
     *
     * @param language the language to write
     */
    public void writeLanguage(String language) {
        properties.setProperty("language", language);
        try (OutputStream os = new FileOutputStream(configPath)) {
            properties.store(os, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
