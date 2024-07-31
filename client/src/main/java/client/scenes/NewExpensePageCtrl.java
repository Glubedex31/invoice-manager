package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Payment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class NewExpensePageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private final ServerUtils serverUtils;

    @FXML
    private Text title;
    @FXML
    private Text paymentDetailsText;
    @FXML
    private Button backButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField numberField;
    @FXML
    private TextArea meaningField;
    @FXML
    private CheckBox bankCheckbox;
    @FXML
    private CheckBox cashCheckbox;
    private boolean isEdit;
    private Payment payment;



    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public NewExpensePageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.clientUtils = clientUtils;
        this.serverUtils = serverUtils;
    }

    /**
     * Initialize the controller.
     * @param location The location
     * @param resources The resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Refresh the page.
     * @param isEdit True if the payment is being edited, false otherwise
     * @param payment The payment
     */
    public void refresh(boolean isEdit, Payment payment) {
        updateLanguage();

        this.isEdit = isEdit;
        this.payment = payment;

        if(isEdit) {
            numberField.setText(String.valueOf(payment.getNumber()));
            meaningField.setText(payment.getMeaning());
            cashCheckbox.setSelected(!payment.isBank());
            bankCheckbox.setSelected(payment.isBank());
        }
    }

    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        title.setText(map.get("new_expense"));
        backButton.setText(map.get("settings_back"));
        saveButton.setText(map.get("settings_save"));
        numberField.setPromptText(map.get("settings_number"));
        meaningField.setPromptText(map.get("preview_expense_meaning"));
        bankCheckbox.setText(map.get("expense_bank"));
        cashCheckbox.setText(map.get("expense_cash"));
        paymentDetailsText.setText(map.get("payment_details"));
    }

    /**
     * Handles the save button.
     */
    public void handleSave() {
        if(isBlankOrInvalid()) {
            showError();
            return;
        }

        if(!hasDigits()) {
            showDigitsError();
            return;
        }

        if(isEdit) {
            handleSaveEdit();
        }
        else {
            handleSaveCreate();
        }
    }

    /**
     * Handles the save button for the edit case.
     */
    public void handleSaveEdit() {
        payment.setNumber(Long.parseLong(numberField.getText()));
        payment.setMeaning(meaningField.getText());
        payment.setBank(bankCheckbox.isSelected());

        Payment res = null;

        try {
            res = serverUtils.updatePayment(payment);
        } catch (Exception e) {
            showServerError();
            return;
        }

        if(res == null) {
            showServerError();
        }
        else {
            showSuccess();
            mainCtrl.showPreviewExpensePage(res);
        }
    }

    /**
     * Handles the save button for the creation case.
     */
    public void handleSaveCreate() {

        long number = Long.parseLong(numberField.getText());
        String meaning = meaningField.getText();
        boolean isBank = bankCheckbox.isSelected();

        Payment res = null;

        try {
            Payment payment = new Payment(meaning, number, isBank);
            res = serverUtils.addPayment(payment);
        } catch (Exception e) {
            showServerError();
            return;
        }

        if(res == null) {
            showServerError();
        }
        else {
            showSuccess();
            mainCtrl.showPreviewExpensePage(res);
        }
    }

    /**
     * Checks if the fields are blank or invalid.
     * @return True if the fields are blank or invalid, false otherwise
     */
    private boolean isBlankOrInvalid() {
        return numberField.getText().isBlank() || meaningField.getText().isBlank() ||
            (!cashCheckbox.isSelected() && !bankCheckbox.isSelected());
    }

    /**
     * Checks if the fields have digits.
     * @return True if the fields have digits, false otherwise
     */
    private boolean hasDigits() {
        return numberField.getText().matches("[0-9]+");
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
     * Shows an error message.
     */
    private void showDigitsError() {
        Map<String, String> map = clientUtils.getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(map.get("digits_error"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("digits_error_text_expense"));
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
        alert.setContentText(map.get("settings_server_error_text_expense"));
        alert.showAndWait();
    }

    /**
     * Shows a success message.
     */
    private void showSuccess() {
        Map<String, String> map = clientUtils.getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(map.get("settings_success"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("settings_success_text_expense"));
        alert.showAndWait();
    }

    /**
     * Handles the back button.
     */
    public void handleBack() {
        if(isEdit) {
            mainCtrl.showPreviewExpensePage(payment);
        }
        else {
            mainCtrl.showPaymentMenuPage();
        }
    }

    /**
     * Handles the checkbox.
     */
    public void handleCheckbox() {
        if (cashCheckbox.isSelected() && bankCheckbox.isSelected()) {
            if (cashCheckbox.isFocused()) {
                bankCheckbox.setSelected(false);
            } else if (bankCheckbox.isFocused()) {
                cashCheckbox.setSelected(false);
            }
        }
    }
}
