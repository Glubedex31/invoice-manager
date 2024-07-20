package server.api;

import commons.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ClientRepository;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientRepository clientRepository;

    /**
     * Constructor.
     *
     * @param clientRepository the client repository
     */
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Get all clients.
     *
     * @return all clients
     */
    @GetMapping(path = { "", "/" })
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    /**
     * Get a client by its id.
     *
     * @param id the id of the client
     * @return the client with the given id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") long id) {
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientRepository.findById(id).get());
    }

    /**
     * Edit a client.
     *
     * @param id the id of the client
     * @param updatedClient the updated client
     * @return the new client
     */
    @PutMapping("/{id}")
    public ResponseEntity<Client> editClient(@PathVariable("id") long id,
                                             @RequestBody Client updatedClient) {
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        Client saved = clientRepository.save(updatedClient);
        return ResponseEntity.ok(saved);
    }

    /**
     * Add a client.
     *
     * @param client the client to add
     * @return the added client
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        if (isNullOrEmptyClient(client)) {
            return ResponseEntity.badRequest().build();
        }
        Client saved = clientRepository.save(client);
        return ResponseEntity.ok(saved);
    }

    /**
     * Delete a client by its id.
     *
     * @param id the id of the client to delete
     * @return the deleted client
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") long id) {
        if (!clientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Client client = clientRepository.findById(id).get();
        clientRepository.deleteById(id);
        return ResponseEntity.ok(client);
    }

    /**
     * Check if a client is null or has null fields.
     *
     * @param client the client to check
     * @return true if the client is null or has null fields,
     * false otherwise
     */
    private boolean isNullOrEmptyClient(Client client) {
        //TODO: Revise this method when you know exactly what fields are optional
        return client == null || client.getName() == null || client.getAccount() == null
            || client.getAddress() == null || client.getBank() == null || client.getCif() == null
            || client.getName().isBlank() || client.getAccount().isBlank()
            || client.getAddress().isBlank() || client.getBank().isBlank()
            || client.getCif().isBlank();
    }
}
