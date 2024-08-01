package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Receipt;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class PreviewReceiptPageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private final ServerUtils serverUtils;
    private Receipt receipt;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private ScrollPane scrollPane;

    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public PreviewReceiptPageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils,
                                  ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.clientUtils = clientUtils;
        this.serverUtils = serverUtils;
    }

    /**
     * Initialize the controller.
     * @param url The URL
     * @param resourceBundle The resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }

    /**
     * Refresh the controller.
     */
    public void refresh() {
        updateLanguage();
        if (receipt != null) {
            try {
                File file = new File("src/main/resources/receipts/receipt_"
                    + receipt.getId() + ".pdf");
                clientUtils.loadPdf(file, scrollPane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        saveButton.setText(map.get("settings_save"));
        deleteButton.setText(map.get("preview_delete"));
        editButton.setText(map.get("preview_edit"));
    }

    /**
     * Get the receipt.
     * @return The receipt
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * Set the receipt.
     * @param receipt The receipt
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    /**
     * Handles the save button.
     */
    public void handleSave() {
        mainCtrl.showIncomeMenuPage();
    }

    /**
     * Handles the delete button.
     */
    public void handleDelete() {
        boolean result = deleteConfirmation();

        if (result) {
            try {
                receipt.getInvoice().setHasBeenPaid(false);
                serverUtils.updateInvoice(receipt.getInvoice());
                serverUtils.deleteReceipt(receipt.getId());

                Path path = Paths.get("src/main/resources/receipts/receipt_"
                    + receipt.getId() + ".pdf");
                Files.delete(path);

                showSuccess();
                mainCtrl.showIncomeMenuPage();
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
            map.get("preview_delete_confirm_text_receipt"),
            deleteButton,
            cancelButton);
        alert.setTitle(map.get("preview_delete_confirm"));
        alert.setHeaderText(map.get("preview_delete_confirm_header_receipt"));

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
        alert.setContentText(map.get("preview_delete_success_text_receipt"));
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
        alert.setContentText(map.get("preview_delete_error_text_receipt"));
        alert.showAndWait();
    }

    /**
     * Handles the edit button.
     */
    public void handleEdit() {
        mainCtrl.showEditReceiptPage(receipt);
    }
}
