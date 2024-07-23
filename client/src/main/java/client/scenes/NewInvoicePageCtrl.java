package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Client;
import commons.Invoice;
import commons.Provider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

public class NewInvoicePageCtrl implements Initializable {
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
    private Text invoiceDetailsText;
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
    private TextArea meaningField;
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
    private Invoice invoice;

    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public NewInvoicePageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils, ServerUtils serverUtils) {
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
     * @param isEdit True if the invoice is being edited, false otherwise
     * @param invoice The invoice
     */
    public void refresh(boolean isEdit, Invoice invoice) {
        provider = serverUtils.getProviders().get(0);
        updateLanguage();

        this.isEdit = isEdit;
        this.invoice = invoice;

        if(isEdit) {
            numberField.setText(String.valueOf(invoice.getNumber()));
            seriesField.setText(String.valueOf(invoice.getSeries()));
            amountField.setText(String.valueOf(invoice.getAmount()));
            dateField.setValue(invoice.getDate());
            meaningField.setText(invoice.getMeaning());
            nameField.setText(invoice.getClient().getName());
            cifField.setText(invoice.getClient().getCif());
            addressField.setText(invoice.getClient().getAddress());
            accountField.setText(invoice.getClient().getAccount());
            bankField.setText(invoice.getClient().getBank());
            clientNumberField.setText(invoice.getClient().getNumber());
        }
    }

    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        title.setText(map.get("new_invoice"));
        backButton.setText(map.get("settings_back"));
        saveButton.setText(map.get("settings_save"));
        invoiceDetailsText.setText(map.get("invoice_details"));
        clientDetailsText.setText(map.get("client_details"));
        numberField.setPromptText(map.get("invoice_number"));
        seriesField.setPromptText(map.get("invoice_series"));
        amountField.setPromptText(map.get("invoice_amount"));
        dateField.setPromptText(map.get("invoice_date"));
        meaningField.setPromptText(map.get("invoice_meaning"));
        nameField.setPromptText(map.get("client_name"));
        cifField.setPromptText(map.get("client_cif"));
        addressField.setPromptText(map.get("client_address"));
        accountField.setPromptText(map.get("client_account"));
        bankField.setPromptText(map.get("client_bank"));
        clientNumberField.setPromptText(map.get("client_number"));
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
        invoice.setNumber(Long.parseLong(numberField.getText()));
        invoice.setSeries(Long.parseLong(seriesField.getText()));
        invoice.setAmount(Long.parseLong(amountField.getText()));
        invoice.setDate(dateField.getValue());
        invoice.setMeaning(meaningField.getText());

        invoice.getClient().setName(nameField.getText());
        invoice.getClient().setCif(cifField.getText());
        invoice.getClient().setAddress(addressField.getText());
        invoice.getClient().setAccount(accountField.getText());
        invoice.getClient().setBank(bankField.getText());
        invoice.getClient().setNumber(clientNumberField.getText());

        Invoice res1 = null;
        Client res2 = null;

        try {
            res2 = serverUtils.updateClient(invoice.getClient());
            res1 = serverUtils.updateInvoice(invoice);
        } catch (Exception e) {
            showServerError();
            return;
        }

        if(res1 == null || res2 == null) {
            showServerError();
        }
        else {
            showSuccess();
            serverUtils.generatePdf(res1.getId());
            mainCtrl.showPreviewInvoicePage(res1);
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
        String meaning = meaningField.getText();

        String name = nameField.getText();
        String cif = cifField.getText();
        String address = addressField.getText();
        String account = accountField.getText();
        String bank = bankField.getText();
        String  clientNumber = clientNumberField.getText();

        Invoice res1 = null;
        Client res2 = null;

        try {
            Client client = new Client(name, cif, address, account, bank, clientNumber);
            res2 = serverUtils.addClient(client);

            Invoice newInvoice = new Invoice(number, series, amount, date, res2,
                provider, meaning, false);
            res1 = serverUtils.addInvoice(newInvoice);
        } catch (Exception e) {
            showServerError();
            return;
        }

        if(res1 == null || res2 == null) {
            showServerError();
        }
        else {
            showSuccess();
            serverUtils.generatePdf(res1.getId());
            mainCtrl.showPreviewInvoicePage(res1);
        }
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
            || dateField.getValue() == null || meaningField.getText().isBlank()
            || clientNumberField.getText().isBlank() || nameField.getText().length() > 250
            || cifField.getText().length() > 100 || meaningField.getText().length() > 500 ||
            numberField.getText().length() > 15 || seriesField.getText().length() > 15 ||
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
        alert.setContentText(map.get("digits_error_text"));
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
        alert.setContentText(map.get("settings_server_error_text_invoice"));
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
        alert.setContentText(map.get("settings_success_text_invoice"));
        alert.showAndWait();
    }

    /**
     * Handles the back button.
     */
    public void handleBack() {
        if(isEdit) {
            mainCtrl.showPreviewInvoicePage(invoice);
        }
        else {
            mainCtrl.showIncomeMenuPage();
        }
    }
}
