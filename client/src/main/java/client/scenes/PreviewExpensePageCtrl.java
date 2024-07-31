package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Payment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class PreviewExpensePageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private final ServerUtils serverUtils;

    @FXML
    private Text title;
    @FXML
    private Button saveButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Text numberField;
    @FXML
    private Label meaningField;
    @FXML
    private Text typeField;
    private Payment payment;


    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public PreviewExpensePageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils,
                                  ServerUtils serverUtils) {
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
        // TODO
    }

    /**
     * Sets the payment.
     * @param payment The payment
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * Refresh the controller.
     */
    public void refresh() {
        updateLanguage();
    }

    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        title.setText(map.get("payment_details"));
        saveButton.setText(map.get("settings_save"));
        editButton.setText(map.get("preview_edit"));
        deleteButton.setText(map.get("preview_delete"));

        numberField.setText(map.get("settings_number") + ": " + payment.getNumber());
        typeField.setText(map.get("preview_expense_type") + ": "
            + (payment.isBank() ? map.get("expense_bank") : map.get("expense_cash")));
        meaningField.setText(map.get("preview_expense_meaning") + ": " + payment.getMeaning());
    }

    /**
     * Handles the save button.
     */
    public void handleSave() {
        mainCtrl.showPaymentMenuPage();
    }

    /**
     * Handles the edit button.
     */
    public void handleEdit() {
        mainCtrl.showEditExpensePage(payment);
    }

    /**
     * Handles the delete button.
     */
    public void handleDelete() {
        boolean result = deleteConfirmation();

        if (result) {
            try {
                serverUtils.deletePayment(payment.getId());
                showSuccess();
                mainCtrl.showPaymentMenuPage();
            } catch (Exception e) {
                showError();
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows a delete confirmation.
     * @return true if the user confirms the deletion, false otherwise
     */
    private boolean deleteConfirmation() {
        Map<String, String> map = clientUtils.getLanguageMap();
        ButtonType deleteButton = new ButtonType(map.get("preview_delete_yes"));
        ButtonType cancelButton = new ButtonType(map.get("preview_delete_cancel"));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            map.get("preview_delete_confirm_text_expense"),
            deleteButton,
            cancelButton);
        alert.setTitle(map.get("preview_delete_confirm"));
        alert.setHeaderText(map.get("preview_delete_confirm_header_expense"));

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == deleteButton;
    }

    /**
     * Shows a success message.
     */
    private void showSuccess() {
        Map<String, String> map = clientUtils.getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(map.get("preview_delete_success"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("preview_delete_success_text_expense"));
        alert.showAndWait();
    }

    /**
     * Shows an error message.
     */
    private void showError() {
        Map<String, String> map = clientUtils.getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(map.get("preview_delete_error"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("preview_delete_error_text_expense"));
        alert.showAndWait();
    }
}
