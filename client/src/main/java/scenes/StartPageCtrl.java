package scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import utils.ClientUtils;
import utils.ServerUtils;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StartPageCtrl implements Initializable {

    private final ServerUtils serverUtils;
    private final MainCtrl mainCtrl;
    private final ClientUtils clientUtils;

    @FXML
    private Text title;
    @FXML
    private Button expensesButton;
    @FXML
    private Button incomeButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button registerButton;

    /**
     * Constructor.
     * @param serverUtils The server utils
     * @param mainCtrl The main controller
     * @param clientUtils The client utils
     */
    @Inject
    public StartPageCtrl(ServerUtils serverUtils, MainCtrl mainCtrl, ClientUtils clientUtils) {
        this.serverUtils = serverUtils;
        this.mainCtrl = mainCtrl;
        this.clientUtils = clientUtils;
    }

    /**
     * Initialize the controller.
     * @param location The location
     * @param resources The resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Implement
    }

    /**
     * Refresh the controller.
     */
    public void refresh() {
        // TODO: Implement

        updateLanguage();
    }

    /**
     * Handle the expenses.
     */
    public void handleExpenses() {

    }

    /**
     * Handle the income.
     */
    public void handleIncome() {

    }

    /**
     * Handle the settings.
     */
    public void handleSettings() {
        mainCtrl.showSettingsPage();
    }

    /**
     * Handle the register.
     */
    public void handleRegister() {

    }

    /**
     * Update the language.
     */
    public void updateLanguage() {
        Map<String, String> map = clientUtils.getLanguageMap();

        title.setText(map.get("start_page_title"));
        expensesButton.setText(map.get("start_page_expenses"));
        incomeButton.setText(map.get("start_page_income"));
        settingsButton.setText(map.get("start_page_settings"));
        registerButton.setText(map.get("start_page_register"));
    }
}
