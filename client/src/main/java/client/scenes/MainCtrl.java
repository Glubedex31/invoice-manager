package client.scenes;

import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import client.utils.ClientUtils;
import client.utils.ConfigUtils;

public class MainCtrl {
    private Stage primaryStage;
    private StartPageCtrl startPageCtrl;
    private Scene startPage;
    private SettingsPageCtrl settingsPageCtrl;
    private Scene settingsPage;
    private IncomeMenuPageCtrl incomeMenuPageCtrl;
    private Scene incomeMenuPage;
    @Inject
    private ClientUtils clientUtils;
    @Inject
    private ConfigUtils configUtils;

    /**
     * Constructor for the main controller.
     * @param primaryStage The primary stage
     * @param startPage The start page
     * @param settingsPage The settings page
     */
    public void initialize(Stage primaryStage,
                           Pair<StartPageCtrl, Parent> startPage,
                           Pair<SettingsPageCtrl, Parent> settingsPage,
                           Pair<IncomeMenuPageCtrl, Parent> incomeMenuPage) {
        this.primaryStage = primaryStage;

        this.startPageCtrl = startPage.getKey();
        this.startPage = new Scene(startPage.getValue());

        this.settingsPageCtrl = settingsPage.getKey();
        this.settingsPage = new Scene(settingsPage.getValue());

        this.incomeMenuPageCtrl = incomeMenuPage.getKey();
        this.incomeMenuPage = new Scene(incomeMenuPage.getValue());

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

    /**
     * Show the income menu page.
     */
    public void showIncomeMenuPage() {
        primaryStage.setTitle("Income Menu Page");
        primaryStage.setScene(incomeMenuPage);
        incomeMenuPageCtrl.refresh();
    }
}
