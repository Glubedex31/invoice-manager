package client.utils;

import commons.Client;
import commons.Provider;
import jakarta.ws.rs.client.ClientBuilder;
import commons.Invoice;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {
    private static final String SERVER = "http://localhost:8080/";

    /**
     * Get all invoices.
     * @return The invoices
     */
    public List<Invoice> getInvoices() {
        return ClientBuilder.newClient(new ClientConfig()
                .property(ClientProperties.CONNECT_TIMEOUT, 5000)
                .property(ClientProperties.READ_TIMEOUT, 5000))
            .target(SERVER).path("api/invoices")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<>() {});
    }

    /**
     * Add an invoice.
     * @param invoice The invoice
     * @return The invoice
     */
    public Invoice addInvoice(Invoice invoice) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/invoices")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.entity(invoice, APPLICATION_JSON), Invoice.class);
    }

    /**
     * Get all providers.
     * @return The providers
     */
    public List<Provider> getProviders() {
        try {


            return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/providers")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Provider>>() {
                });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add a provider.
     * @param provider The provider
     * @return The provider
     */
    public Provider addProvider(Provider provider) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/providers")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.entity(provider, APPLICATION_JSON), Provider.class);
    }

    /**
     * Get an invoice by id.
     * @param id The id
     * @return The invoice
     */
    public Invoice getInvoice(long id) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/invoices/" + id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(Invoice.class);
    }

    /**
     * Generate a pdf.
     * @param id The id
     * @return success or failure
     */
    public String generatePdf(long id) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/invoices/generate-pdf/" + id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(String.class);
    }

    /**
     * Get a provider by id.
     * @param id The id
     * @return The provider
     */
    public Provider getProvider(long id) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/providers/" + id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(Provider.class);
    }

    /**
     * Update an invoice.
     * @param invoice The invoice
     * @return The invoice
     */
    public Invoice updateInvoice(Invoice invoice) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/invoices/" + invoice.getId())
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .put(Entity.entity(invoice, APPLICATION_JSON), Invoice.class);
    }

    /**
     * Update a provider.
     * @param provider The provider
     * @return The provider
     */
    public Provider updateProvider(Provider provider) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/providers/" + provider.getId())
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .put(Entity.entity(provider, APPLICATION_JSON), Provider.class);
    }

    /**
     * Adds a client.
     * @param client The client
     * @return The client
     */
    public Client addClient(Client client) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/clients")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.entity(client, APPLICATION_JSON), Client.class);
    }
}
