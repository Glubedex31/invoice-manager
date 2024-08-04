package client.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Client;
import commons.Document;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

    /**
     * Shows a server error message.
     */
    public void showError() {
        Map<String, String> map = getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(map.get("validation_error"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("validation_error_text"));
        alert.showAndWait();
    }

    /**
     * Sets the client fields.
     * @param document the document
     * @param nameField the name field
     * @param cifField the CIF field
     * @param addressField the address field
     * @param accountField the account field
     * @param bankField the bank field
     * @param clientNumberField the client number field
     */
    public void setClientFields(Document document, TextField nameField, TextField cifField,
                                TextField addressField, TextField accountField,
                                TextField bankField, TextField clientNumberField) {
        nameField.setText(document.getClient().getName());
        cifField.setText(document.getClient().getCif());
        addressField.setText(document.getClient().getAddress());
        accountField.setText(document.getClient().getAccount());
        bankField.setText(document.getClient().getBank());
        clientNumberField.setText(document.getClient().getNumber());
    }

    /**
     * Sets the prompt text for the client fields.
     * @param map the language map
     * @param nameField the name field
     * @param cifField the CIF field
     * @param addressField the address field
     * @param accountField the account field
     * @param bankField the bank field
     * @param clientNumberField the client number field
     */
    public void setPromptClientFields(Map<String, String> map, TextField nameField,
                                      TextField cifField, TextField addressField,
                                      TextField accountField, TextField bankField,
                                      TextField clientNumberField) {
        nameField.setPromptText(map.get("client_name"));
        cifField.setPromptText(map.get("client_cif"));
        addressField.setPromptText(map.get("client_address"));
        accountField.setPromptText(map.get("client_account"));
        bankField.setPromptText(map.get("client_bank"));
        clientNumberField.setPromptText(map.get("client_number"));
    }

    /**
     * Sets the client details.
     * @param client the client
     * @param nameField the name field
     * @param cifField the CIF field
     * @param addressField the address field
     * @param accountField the account field
     * @param bankField the bank field
     * @param clientNumberField the client number field
     */
    public void setClientDetails(Client client, TextField nameField, TextField cifField,
                                 TextField addressField, TextField accountField,
                                 TextField bankField, TextField clientNumberField) {
        client.setName(nameField.getText());
        client.setCif(cifField.getText());
        client.setAddress(addressField.getText());
        client.setAccount(accountField.getText());
        client.setBank(bankField.getText());
        client.setNumber(clientNumberField.getText());
    }

    /**
     * Shows a digits error message.
     */
    public void showDigitsError() {
        Map<String, String> map = getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(map.get("digits_error"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("digits_error_text"));
        alert.showAndWait();
    }

    /**
     * Shows a server error message.
     */
    public void showServerError() {
        Map<String, String> map = getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(map.get("settings_server_error"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("settings_server_error_text_receipt"));
        alert.showAndWait();
    }

    /**
     * Shows a success message.
     */
    public void showSuccessReceipt() {
        Map<String, String> map = getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(map.get("settings_success"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("settings_success_text_receipt"));
        alert.showAndWait();
    }
}
