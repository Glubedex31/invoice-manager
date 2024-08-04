package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Payment;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ExpenseSummaryPageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private final ServerUtils serverUtils;

    @FXML
    private Text title;
    @FXML
    private Button backButton;
    @FXML
    private ListView<Payment> listView;
    @FXML
    private CheckBox bankCheckbox;
    @FXML
    private CheckBox cashCheckbox;

    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public ExpenseSummaryPageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils,
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
        bankCheckbox.setSelected(true);
        cashCheckbox.setSelected(true);
    }

    /**
     * Refresh the controller.
     */
    public void refresh() {
        updateLanguage();
        loadExpenses();
        setupEventListView();
    }

    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        title.setText(map.get("payment_summary"));
        backButton.setText(map.get("settings_back"));
        bankCheckbox.setText(map.get("expense_bank"));
        cashCheckbox.setText(map.get("expense_cash"));
    }

    /**
     * Handles the bank checkbox.
     */
    public void handleBankCheckbox() {
        loadExpenses();
    }

    /**
     * Handles the cash checkbox.
     */
    public void handleCashCheckbox() {
        loadExpenses();
    }

    /**
     * This loads the expenses from the server.
     */
    private void loadExpenses() {
        Task<List<Payment>> task = new Task<>() {
            @Override
            protected List<Payment> call() throws Exception {
                List<Payment> payments = serverUtils.getPayments();
                if(!cashCheckbox.isSelected()) {
                    payments = payments.stream()
                        .filter(Payment::isBank)
                        .toList();
                }
                if(!bankCheckbox.isSelected()) {
                    payments = payments.stream()
                        .filter(payment -> !payment.isBank())
                        .toList();
                }
                return payments;
            }
        };

        task.setOnSucceeded(expense ->
            listView.setItems(FXCollections.observableArrayList(task.getValue())));
        task.setOnFailed(expense -> {
            Throwable cause = task.getException();
            cause.printStackTrace();
            showAlert(clientUtils.getLanguageMap()
                .get("summary_load_error_text"));
            System.out.println(cause.getMessage());
        });
        new Thread(task).start();
    }

    /**
     * Set up the event list view.
     */
    private void setupEventListView() {
        listView.setCellFactory(lv -> new ListCell<Payment>() {
            @Override
            protected void updateItem(Payment payment, boolean empty) {
                super.updateItem(payment, empty);
                setText(empty || payment == null ? null :
                    clientUtils.getLanguageMap().get("expense") + " " +
                        clientUtils.getLanguageMap().get("nr") + " " + payment.getNumber());
                setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2 && !empty) {
                        mainCtrl.showPreviewExpensePage(payment);
                    }
                });
            }
        });
    }

    /**
     * Show an alert.
     *
     * @param message The message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("summary_load_error_title");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the back button.
     */
    public void handleBack() {
        mainCtrl.showPaymentMenuPage();
    }
}
