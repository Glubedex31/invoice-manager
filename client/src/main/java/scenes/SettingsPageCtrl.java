package scenes;

import com.google.inject.Inject;
import javafx.fxml.Initializable;
import utils.ServerUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPageCtrl implements Initializable {
    private final ServerUtils serverUtils;
    private final MainCtrl mainCtrl;

    /**
     * Constructor.
     * @param serverUtils The server utils
     * @param mainCtrl The main controller
     */
    @Inject
    public SettingsPageCtrl(ServerUtils serverUtils, MainCtrl mainCtrl) {
        this.serverUtils = serverUtils;
        this.mainCtrl = mainCtrl;
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

    }
}
