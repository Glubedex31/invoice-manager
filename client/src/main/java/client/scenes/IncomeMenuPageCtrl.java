package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class IncomeMenuPageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private final ServerUtils serverUtils;

    @FXML
    private Text title;
    @FXML
    private Button backButton;
    @FXML
    private Button createInvoiceButton;
    @FXML
    private Button invoiceSummaryButton;
    @FXML
    private Button createReceiptButton;
    @FXML
    private Button receiptSummaryButton;
    @FXML
    private Button createBankButton;
    @FXML
    private Button bankSummaryButton;

    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public IncomeMenuPageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils, ServerUtils serverUtils) {
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
    }

    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        title.setText(map.get("income_menu"));
        backButton.setText(map.get("settings_back"));
        createInvoiceButton.setText(map.get("create_invoice"));
        invoiceSummaryButton.setText(map.get("invoice_summary"));
        createReceiptButton.setText(map.get("create_receipt"));
        receiptSummaryButton.setText(map.get("receipt_summary"));
        createBankButton.setText(map.get("create_bank"));
        bankSummaryButton.setText(map.get("bank_summary"));
    }

    /**
     * Handles the back button.
     */
    public void handleBack() {
        mainCtrl.showStartPage();
    }

    public void handleCreateInvoice() {
        mainCtrl.showNewInvoicePage();
    }

    public void handleInvoiceSummary() {

    }

    public void handleCreateReceipt() {

    }

    public void handleReceiptSummary() {

    }

    public void handleCreateBank() {

    }

    public void handleBankSummary() {

    }
}
