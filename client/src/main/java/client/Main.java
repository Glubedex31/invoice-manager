package client;

import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {
    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Main method.
     *
     * @param args Arguments
     */
    public static void main(String[] args) {
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
        // TODO Auto-generated method stub
    }
}