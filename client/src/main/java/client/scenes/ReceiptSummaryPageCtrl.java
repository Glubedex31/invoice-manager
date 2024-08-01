package client.scenes;

import client.utils.ClientUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Invoice;
import commons.Receipt;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ReceiptSummaryPageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private final ServerUtils serverUtils;
    @FXML
    private Text title;
    @FXML
    private Button backButton;
    @FXML
    private ListView<Receipt> listView;

    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public ReceiptSummaryPageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils,
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
        loadReceipts();
        setupEventListView();
    }

    /**
     * This loads the receipts from the server.
     */
    private void loadReceipts() {
        Task<List<Receipt>> task = new Task<>() {
            @Override
            protected List<Receipt> call() throws Exception {
                List<Receipt> receipts = serverUtils.getReceipts();
                return receipts;
            }
        };

        task.setOnSucceeded(receipt ->
            listView.setItems(FXCollections.observableArrayList(task.getValue())));
        task.setOnFailed(invoice -> {
            Throwable cause = task.getException();
            cause.printStackTrace();
            showAlert("summary_load_error_title", clientUtils.getLanguageMap()
                .get("summary_load_error_text"));
            System.out.println(cause.getMessage());
        });
        new Thread(task).start();
    }

    /**
     * Set up the event list view.
     */
    private void setupEventListView() {
        listView.setCellFactory(lv -> new ListCell<Receipt>() {
            @Override
            protected void updateItem(Receipt receipt, boolean empty) {
                super.updateItem(receipt, empty);
                setText(empty || receipt == null ? null :
                    clientUtils.getLanguageMap().get("receipt") + " " +
                        clientUtils.getLanguageMap().get("nr") + " " + receipt.getNumber());
                setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2 && !empty) {
                        mainCtrl.showPreviewReceiptPage(receipt);
                    }
                });
            }
        });
    }

    /**
     * Show an alert.
     * @param title The title
     * @param message The message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the back button.
     */
    public void handleBack() {
        mainCtrl.showIncomeMenuPage();
    }

    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();
        
        title.setText(map.get("receipt_summary"));
        backButton.setText(map.get("settings_back"));
    }
}
