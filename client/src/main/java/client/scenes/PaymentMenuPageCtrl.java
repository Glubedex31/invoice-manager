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

public class PaymentMenuPageCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;
    private final ServerUtils serverUtils;

    @FXML
    private Button backButton;
    @FXML
    private Button createPaymentButton;
    @FXML
    private Button paymentSummaryButton;
    @FXML
    private Text title;

    /**
     * Constructor.
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     * @param serverUtils The server utils
     */
    @Inject
    public PaymentMenuPageCtrl(MainCtrl mainCtrl, ClientUtils clientUtils,
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
    }

    /**
     * Update the language.
     */
    private void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        title.setText(map.get("payment_menu"));
        createPaymentButton.setText(map.get("create_payment"));
        paymentSummaryButton.setText(map.get("payment_summary"));
        backButton.setText(map.get("settings_back"));
    }

    /**
     * Handles the create payment button.
     */
    public void handleCreatePayment() {
        mainCtrl.showNewExpensePage();
    }

    /**
     * Handles the payment summary button.
     */
    public void handlePaymentSummary() {
        mainCtrl.showExpenseSummaryPage();
    }

    /**
     * Handles the back button.
     */
    public void handleBack() {
        mainCtrl.showStartPage();
    }
}
