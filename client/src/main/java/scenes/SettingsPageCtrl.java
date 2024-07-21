package scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import utils.ClientUtils;
import utils.ServerUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;

public class SettingsPageCtrl implements Initializable {
    private final ServerUtils serverUtils;
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;

    @FXML
    private Text title;
    @FXML
    private Button saveButton;
    @FXML
    private Button backButton;
    @FXML
    private Text languageText;
    @FXML
    private Text configureText;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField cifField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField accountField;
    @FXML
    private TextField bankField;
    @FXML
    private TextField numberField;

    /**
     * Constructor.
     * @param serverUtils The server utils
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     */
    @Inject
    public SettingsPageCtrl(ServerUtils serverUtils, MainCtrl mainCtrl, ClientUtils clientUtils) {
        this.serverUtils = serverUtils;
        this.mainCtrl = mainCtrl;
        this.clientUtils = clientUtils;
    }

    /**
     * Initialize the controller.
     * @param location The location
     * @param resources The resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageComboBox.getItems().addAll(Arrays.asList("English", "Romanian"));

        languageComboBox.getSelectionModel().selectedItemProperty()
            .addListener((observableValue, oldLanguage, newLanguage) -> {
                clientUtils.setLanguage(newLanguage);
                refresh();
            });
    }

    /**
     * Refresh the controller.
     */
    public void refresh() {

        updateLanguage();
    }

    /**
     * Updates the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        title.setText(map.get("settings_title"));
        saveButton.setText(map.get("settings_save"));
        backButton.setText(map.get("settings_back"));
        languageText.setText(map.get("settings_language"));
        configureText.setText(map.get("settings_configure"));
        nameField.setPromptText(map.get("settings_name"));
        cifField.setPromptText(map.get("settings_cif"));
        addressField.setPromptText(map.get("settings_address"));
        accountField.setPromptText(map.get("settings_account"));
        bankField.setPromptText(map.get("settings_bank"));
        numberField.setPromptText(map.get("settings_number"));
        languageComboBox.setPromptText(map.get("settings_language_box"));
    }

    public void handleSave() {
        //TODO: Implement
    }

    public void handleBack() {
        mainCtrl.showStartPage();
    }
}
