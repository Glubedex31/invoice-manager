package scenes;

import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import utils.ClientUtils;
import utils.ConfigUtils;

public class MainCtrl {
    private Stage primaryStage;
    private StartPageCtrl startPageCtrl;
    private Scene startPage;
    private SettingsPageCtrl settingsPageCtrl;
    private Scene settingsPage;
    @Inject
    private ClientUtils clientUtils;
    @Inject
    private ConfigUtils configUtils;

    /**
     * Constructor for the main controller.
     * @param primaryStage The primary stage
     * @param startPage The start page
     **/
    public void initialize(Stage primaryStage,
                           Pair<StartPageCtrl, Parent> startPage,
                           Pair<SettingsPageCtrl, Parent> settingsPage) {
        this.primaryStage = primaryStage;

        this.startPageCtrl = startPage.getKey();
        this.startPage = new Scene(startPage.getValue());

        this.settingsPageCtrl = settingsPage.getKey();
        this.settingsPage = new Scene(settingsPage.getValue());

        clientUtils.setLanguage(configUtils.getLanguage());

        showStartPage();
        primaryStage.show();
    }

    /**
     * Set the primary stage.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    /**
     * Show the start page.
     */
    public void showStartPage() {
        primaryStage.setTitle("Start Page");
        primaryStage.setScene(startPage);
        startPageCtrl.refresh();
    }

    /**
     * Show the settings page.
     */
    public void showSettingsPage() {
        primaryStage.setTitle("Settings Page");
        primaryStage.setScene(settingsPage);
        settingsPageCtrl.refresh();
    }
}
