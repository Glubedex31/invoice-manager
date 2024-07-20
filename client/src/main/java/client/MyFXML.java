package client;

import com.google.inject.Injector;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 * FXML loader.
 */
public class MyFXML {

    private final Injector injector;

    /**
     * Constructor.
     * @param injector The injector
     */
    public MyFXML(Injector injector) {
        this.injector = injector;
    }

    /**
     * Load an FXML file.
     *
     * @param <T> Type of the controller
     * @param c Class of the controller
     * @param parts Path parts
     * @return Pair of controller and parent
     */
    public <T> Pair<T, Parent> load(Class<T> c, String... parts) {
        try {
            var loader = new FXMLLoader(getLocation(parts), null,
                null, new MyFactory(), StandardCharsets.UTF_8);
            Parent parent = loader.load();
            T ctrl = loader.getController();
            return new Pair<>(ctrl, parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the location.
     * @param parts The parts
     * @return The URL
     */
    private URL getLocation(String... parts) {
        var path = Path.of("", parts).toString();
        return MyFXML.class.getClassLoader().getResource(path);
    }

    /**
     * Factory class.
     */
    private class MyFactory implements BuilderFactory, Callback<Class<?>, Object> {

        /**
         * Get the builder.
         * @param type The type
         * @return The builder
         */
        @Override
        @SuppressWarnings("rawtypes")
        public Builder<?> getBuilder(Class<?> type) {
            return new Builder() {
                @Override
                public Object build() {
                    return injector.getInstance(type);
                }
            };
        }

        /**
         * Call the class.
         * @param type The type
         * @return The object
         */
        @Override
        public Object call(Class<?> type) {
            return injector.getInstance(type);
        }
    }
}