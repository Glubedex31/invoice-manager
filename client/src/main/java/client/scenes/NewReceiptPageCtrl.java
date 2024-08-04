package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

public class NewReceiptPageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private final ServerUtils serverUtils;

    @FXML
    private Text title;
    @FXML
    private Button backButton;
    @FXML
    private Button saveButton;
    @FXML
    private Text receiptDetailsText;
    @FXML
    private Text clientDetailsText;
    @FXML
    private TextField numberField;
    @FXML
    private TextField seriesField;
    @FXML
    private TextField amountField;
    @FXML
    private DatePicker dateField;
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
    private TextField clientNumberField;

    private Provider provider;
    private boolean isEdit;
    private Receipt receipt;
    private Invoice invoice;

    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public NewReceiptPageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils,
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
     * Refresh the page.
     * @param isEdit True if the receipt is being edited, false otherwise
     * @param receipt The receipt
     * @param invoice The invoice
     */
    public void refresh(boolean isEdit, Receipt receipt, Invoice invoice) {
        provider = serverUtils.getProviders().get(0);
        updateLanguage();

        this.isEdit = isEdit;
        this.receipt = receipt;
        this.invoice = invoice;

        if(isEdit) {
            setFields(receipt);
        }
        else {
            setFields(invoice);
        }
    }

    /**
     * Set the fields for the invoice.
     * @param document The document
     */
    public void setFields(Document document) {
        numberField.setText(String.valueOf(document.getNumber()));
        seriesField.setText(String.valueOf(document.getSeries()));
        amountField.setText(String.valueOf(document.getAmount()));
        dateField.setValue(document.getDate());
        //meaningField.setText(receipt.getMeaning());
        clientUtils.setClientFields(document, nameField, cifField,
            addressField, accountField, bankField, clientNumberField);
    }



    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        title.setText(map.get("new_receipt"));
        backButton.setText(map.get("settings_back"));
        saveButton.setText(map.get("settings_save"));
        receiptDetailsText.setText(map.get("receipt_details"));
        clientDetailsText.setText(map.get("client_details"));
        numberField.setPromptText(map.get("receipt_number"));
        seriesField.setPromptText(map.get("receipt_series"));
        amountField.setPromptText(map.get("receipt_amount"));
        dateField.setPromptText(map.get("receipt_date"));
        //meaningField.setPromptText(map.get("invoice_meaning"));
        clientUtils.setPromptClientFields(map, nameField, cifField, addressField,
            accountField, bankField, clientNumberField);
    }

    /**
     * Handles the back button.
     */
    public void handleBack() {
        mainCtrl.showChooseInvoicePage();
    }

    /**
     * Handles the save button.
     */
    public void handleSave() {
        if(isBlankOrInvalid()) {
            clientUtils.showError();
            return;
        }

        if(!hasDigits()) {
            clientUtils.showDigitsError();
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
        receipt.setNumber(Long.parseLong(numberField.getText()));
        receipt.setSeries(Long.parseLong(seriesField.getText()));
        receipt.setAmount(Long.parseLong(amountField.getText()));
        receipt.setDate(dateField.getValue());
        //receipt.setMeaning(meaningField.getText());

        clientUtils.setClientDetails(receipt.getClient(), nameField, cifField, addressField,
            accountField, bankField, clientNumberField);

        Receipt res1;
        Client res2;

        try {
            res2 = serverUtils.updateClient(receipt.getClient());
            res1 = serverUtils.updateReceipt(receipt);
        } catch (Exception e) {
            clientUtils.showServerError();
            return;
        }

        validateRes(res1, res2);
    }

    /**
     * Validates the receipt and client.
     * @param res1 The receipt
     * @param res2 The client
     */
    public void validateRes(Receipt res1, Client res2) {
        if(res1 == null || res2 == null) {
            clientUtils.showServerError();
        }
        else {
            clientUtils.showSuccessReceipt();
            serverUtils.generateReceiptPdf(res1.getId());
            if(invoice.isHasBeenPaid() == false) {
                invoice.setHasBeenPaid(true);
                serverUtils.updateInvoice(invoice);
            }
            mainCtrl.showPreviewReceiptPage(res1);
        }
    }

    /**
     * Handles the save button for the creation case.
     */
    public void handleSaveCreate() {

        long number = Long.parseLong(numberField.getText());
        long series = Long.parseLong(seriesField.getText());
        long amount = Long.parseLong(amountField.getText());
        LocalDate date = dateField.getValue();
        //String meaning = meaningField.getText();

        String name = nameField.getText();
        String cif = cifField.getText();
        String address = addressField.getText();
        String account = accountField.getText();
        String bank = bankField.getText();
        String  clientNumber = clientNumberField.getText();

        Receipt res1;
        Client res2;

        try {
            Client client = new Client(name, cif, address, account, bank, clientNumber);
            res2 = serverUtils.addClient(client);

            Receipt newReceipt = new Receipt(number, series, amount, date,
                res2, provider, invoice);
            res1 = serverUtils.addReceipt(newReceipt);

        } catch (Exception e) {
            clientUtils.showServerError();
            return;
        }

        validateRes(res1, res2);
    }

    /**
     * Checks if the fields are blank or invalid.
     * @return True if the fields are blank or invalid, false otherwise
     */
    private boolean isBlankOrInvalid() {
        return nameField.getText().isBlank() || cifField.getText().isBlank() ||
            addressField.getText().isBlank() || accountField.getText().isBlank() ||
            bankField.getText().isBlank() || numberField.getText().isBlank()
            || seriesField.getText().isBlank() || amountField.getText().isBlank()
            || dateField.getValue() == null || cifField.getText().length() > 100
            || clientNumberField.getText().isBlank() || nameField.getText().length() > 250
            || numberField.getText().length() > 15 || seriesField.getText().length() > 15 ||
            amountField.getText().length() > 15 || dateField.getValue().toString().length() > 50 ||
            addressField.getText().length() > 250 || accountField.getText().length() > 100 ||
            bankField.getText().length() > 100 || numberField.getText().length() > 100;
    }

    /**
     * Checks if the fields have digits.
     * @return True if the fields have digits, false otherwise
     */
    private boolean hasDigits() {
        return numberField.getText().matches("[0-9]+") && seriesField.getText().matches("[0-9]+") &&
            amountField.getText().matches("[0-9]+");
    }
}
