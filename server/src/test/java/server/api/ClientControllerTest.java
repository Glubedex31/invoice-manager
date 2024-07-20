package server.api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import commons.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import server.database.ClientRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ClientControllerTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientController clientController;

    private Client sampleClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleClient = new Client("John Doe", "123 Main St", "123456789", "CIF123", "555-1234", "Bank A");
    }

    @Test
    void getAllClientsTest() {
        when(clientRepository.findAll()).thenReturn(Arrays.asList(sampleClient));
        List<Client> result = clientController.getAllClients();
        assertFalse(result.isEmpty());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void getClientByIdFoundTest() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(sampleClient));
        ResponseEntity<Client> response = clientController.getClientById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    void getClientByIdNotFoundTest() {
        when(clientRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Client> response = clientController.getClientById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addClientTest() {
        when(clientRepository.save(any(Client.class))).thenReturn(sampleClient);
        ResponseEntity<Client> response = clientController.addClient(sampleClient);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    void addClientBadRequestTest() {
        Client invalidClient = new Client(null, null, null, null, null, null);
        ResponseEntity<Client> response = clientController.addClient(invalidClient);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editClientTest() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        when(clientRepository.save(any(Client.class))).thenReturn(sampleClient);
        ResponseEntity<Client> response = clientController.editClient(1L, sampleClient);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    void editClientNotFoundTest() {
        when(clientRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Client> response = clientController.editClient(1L, sampleClient);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteClientTest() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(sampleClient));
        doNothing().when(clientRepository).deleteById(1L);
        ResponseEntity<Client> response = clientController.deleteClient(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteClientNotFoundTest() {
        when(clientRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Client> response = clientController.deleteClient(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
