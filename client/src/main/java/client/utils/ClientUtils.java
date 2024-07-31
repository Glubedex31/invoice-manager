package client.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
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

    /**
     * Loads a PDF file into a scroll pane.
     *
     * @param file the PDF file
     * @param scrollPane the scroll pane
     * @throws IOException if an error occurs
     */
    public void loadPdf(File file, ScrollPane scrollPane) throws IOException {
        PDDocument document = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(document);

        VBox box = new VBox(5);
        for (int page = 0; page < document.getNumberOfPages(); page++) {
            BufferedImage bufferedImage = renderer.renderImageWithDPI(
                page, 150, ImageType.RGB);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(scrollPane.getWidth() - 20);
            imageView.setPreserveRatio(true);
            box.getChildren().add(imageView);
        }

        scrollPane.setContent(box);
        document.close();
    }
}
