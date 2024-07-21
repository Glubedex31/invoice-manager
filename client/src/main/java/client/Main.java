package client;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import client.scenes.MainCtrl;
import client.scenes.SettingsPageCtrl;
import client.scenes.StartPageCtrl;
import client.utils.ClientUtils;
import client.utils.ConfigUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {
    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @Inject
    private ConfigUtils configUtils;
    @Inject
    private ClientUtils clientUtils;

    /**
     * Main method.
     *
     * @param args Arguments
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     * Get the injector.
     * @return The injector
     */
    public static Injector injector() {
        return INJECTOR;
    }

    /**
     * Start the application.
     * @param primaryStage The primary stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.INJECTOR.injectMembers(this);

        if (configUtils == null) {
            throw new RuntimeException("Dependency injection failed for configReader.");
        }

        configUtils.initialize();
        var startPage = FXML.load(StartPageCtrl.class, "scenes", "StartPage.fxml");
        var settingsPage = FXML.load(SettingsPageCtrl.class, "scenes", "SettingsPage.fxml");

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage,
            startPage,
            settingsPage);

        primaryStage.setOnCloseRequest(e -> {
            configUtils.writeLanguage(clientUtils.getLanguage());
        });
    }
}