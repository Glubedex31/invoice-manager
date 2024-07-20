package utils;

import jakarta.ws.rs.client.ClientBuilder;
import commons.Invoice;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {
    private static final String SERVER = "http://localhost:8080";

    /**
     * Get all invoices.
     * @return The invoices
     */
    public List<Invoice> getEvents() {
        return ClientBuilder.newClient(new ClientConfig()
                .property(ClientProperties.CONNECT_TIMEOUT, 5000)
                .property(ClientProperties.READ_TIMEOUT, 5000))
            .target(SERVER).path("api/invoices")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<>() {});
    }
}
