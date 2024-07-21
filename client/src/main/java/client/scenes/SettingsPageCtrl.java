package client.scenes;

import com.google.inject.Inject;
import commons.Provider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import client.utils.ClientUtils;
import client.utils.ServerUtils;

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
    private boolean hasProvider;

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
        if(serverUtils.getProviders().size() == 1) {
            hasProvider = true;
            Provider provider = serverUtils.getProviders().get(0);
            nameField.setText(provider.getName());
            cifField.setText(provider.getCif());
            addressField.setText(provider.getAddress());
            accountField.setText(provider.getAccount());
            bankField.setText(provider.getBank());
            numberField.setText(provider.getNumber());
        }
        else {
            hasProvider = false;
        }
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

    /**
     * Handles the save button.
     */
    public void handleSave() {
        if(isBlankOrInvalid()) {
            showError();
            return;
        }
        Provider res = null;
        if(hasProvider) {
            Provider provider = serverUtils.getProviders().get(0);
            provider.setName(nameField.getText());
            provider.setCif(cifField.getText());
            provider.setAddress(addressField.getText());
            provider.setAccount(accountField.getText());
            provider.setBank(bankField.getText());
            provider.setNumber(numberField.getText());
            try {
                res = serverUtils.updateProvider(provider);
            } catch (Exception e) {
                showServerError();
                return;
            }

        }
        else {
            Provider provider = new Provider(nameField.getText(),
                addressField.getText(), accountField.getText(),
                cifField.getText(), numberField.getText(), bankField.getText());
            try {
                res = serverUtils.addProvider(provider);
            } catch (Exception e) {
                showServerError();
                return;
            }

        }
        if(res != null) {
            hasProvider = true;
            showSuccess();
        }
    }

    /**
     * Shows a success message.
     */
    private void showSuccess() {
        Map<String, String> map = clientUtils.getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(map.get("settings_success"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("settings_success_text"));
        alert.showAndWait();
    }

    /**
     * Shows an error message.
     */
    private void showError() {
        Map<String, String> map = clientUtils.getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(map.get("validation_error"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("validation_error_text"));
        alert.showAndWait();
    }

    /**
     * Shows a server error message.
     */
    private void showServerError() {
        Map<String, String> map = clientUtils.getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(map.get("settings_server_error"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("settings_server_error_text"));
        alert.showAndWait();
    }

    /**
     * Checks if the fields are blank or invalid.
     * @return True if the fields are blank or invalid, false otherwise
     */
    public boolean isBlankOrInvalid() {
        return nameField.getText().isBlank() || cifField.getText().isBlank() ||
            addressField.getText().isBlank() || accountField.getText().isBlank() ||
            bankField.getText().isBlank() || numberField.getText().isBlank()
            || nameField.getText().length() > 250 || cifField.getText().length() > 100 ||
            addressField.getText().length() > 250 || accountField.getText().length() > 100 ||
            bankField.getText().length() > 100 || numberField.getText().length() > 100;
    }

    /**
     * Handles the back button.
     */
    public void handleBack() {
        mainCtrl.showStartPage();
    }
}
