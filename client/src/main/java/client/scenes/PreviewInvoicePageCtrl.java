package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Invoice;
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

public class PreviewInvoicePageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private Invoice invoice;
    private final ServerUtils serverUtils;

    @FXML
    private Button saveButton;
    @FXML
    private Button saveButtonReceipt;
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
    public PreviewInvoicePageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils,
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

    }

    /**
     * Refresh the controller.
     */
    public void refresh() {
        updateLanguage();
        if (invoice != null) {
            try {
                File file = new File("src/main/resources/invoices/invoice_"
                    + invoice.getId() + ".pdf");
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

        //backButton.setText(map.get("settings_back"));
        saveButton.setText(map.get("settings_save"));
        saveButtonReceipt.setText(map.get("preview_save_receipt"));
        deleteButton.setText(map.get("preview_delete"));
        editButton.setText(map.get("preview_edit"));
    }

    /**
     * Gets the invoice.
     * @return invoice The invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * Sets the invoice.
     * @param invoice The invoice
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
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
                serverUtils.deleteInvoice(invoice.getId());

                Path path = Paths.get("src/main/resources/invoices/invoice_"
                    + invoice.getId() + ".pdf");
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
            map.get("preview_delete_confirm_text"),
            deleteButton,
            cancelButton);
        alert.setTitle(map.get("preview_delete_confirm"));
        alert.setHeaderText(map.get("preview_delete_confirm_header"));

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
        alert.setContentText(map.get("preview_delete_success_text"));
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
        alert.setContentText(map.get("preview_delete_error_text"));
        alert.showAndWait();
    }

    /**
     * Handles the edit button.
     */
    public void handleEdit() {
        mainCtrl.showEditInvoicePage(invoice);
    }

    /**
     * Handles the save receipt button.
     */
    public void handleSaveReceipt() {
        Receipt receipt;

        try {
            receipt = new Receipt(invoice);
            receipt = serverUtils.addReceipt(receipt);
        } catch (Exception e) {
            showServerErrorReceipt();
            return;
        }

        if(receipt == null) {
            showServerErrorReceipt();
        }
        else {
            clientUtils.showSuccessReceipt();
            serverUtils.generateReceiptPdf(receipt.getId());
            invoice.setHasBeenPaid(true);
            serverUtils.updateInvoice(invoice);
            mainCtrl.showPreviewReceiptPage(receipt);
        }
    }

    /**
     * Shows a server error message.
     */
    private void showServerErrorReceipt() {
        Map<String, String> map = clientUtils.getLanguageMap();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(map.get("settings_server_error"));
        alert.setHeaderText(null);
        alert.setContentText(map.get("settings_server_error_text_receipt"));
        alert.showAndWait();
    }
}
