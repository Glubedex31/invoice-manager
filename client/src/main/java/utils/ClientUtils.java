package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ClientUtils {
    private String language;
    private Map<String, String> languageMap = new HashMap<>();

    /**
     * Constructor for the ClientUtils class.
     */
    public ClientUtils() {}

    /**
     * Loads the language map from the language file.
     *
     * @param language the language to load
     */
    public void loadLanguageMap(String language) {
        String path = "src/main/resources/languages/" + language.toLowerCase() + ".json";
        ObjectMapper mapper = new ObjectMapper();
        try {
            languageMap = mapper.readValue(new File(path), Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the language map.
     *
     * @return the language map
     */
    public Map<String, String> getLanguageMap() {
        return languageMap;
    }

    /**
     * Changes the language of the application.
     *
     * @param language language to change to
     */
    public void setLanguage(String language) {
        this.language = language;
        loadLanguageMap(language);
    }

    /**
     * Returns the current language of the application.
     *
     * @return the current language
     */
    public String getLanguage() {
        return language;
    }
}
