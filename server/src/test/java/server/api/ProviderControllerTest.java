package server.api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import commons.Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import server.database.ProviderRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ProviderControllerTest {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderController providerController;

    private Provider sampleProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProvider = new Provider("Provider A", "100 Main St", "9876543210", "CIF200", "800-123-4567", "Bank X");
    }

    @Test
    void getAllProvidersTest() {
        when(providerRepository.findAll()).thenReturn(Arrays.asList(sampleProvider));
        List<Provider> result = providerController.getAllProviders();
        assertFalse(result.isEmpty());
        assertEquals("Provider A", result.get(0).getName());
    }

    @Test
    void getProviderByIdFoundTest() {
        when(providerRepository.existsById(1L)).thenReturn(true);
        when(providerRepository.findById(1L)).thenReturn(Optional.of(sampleProvider));
        ResponseEntity<Provider> response = providerController.getProviderById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Provider A", response.getBody().getName());
    }

    @Test
    void getProviderByIdNotFoundTest() {
        when(providerRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Provider> response = providerController.getProviderById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addProviderTest() {
        when(providerRepository.save(any(Provider.class))).thenReturn(sampleProvider);
        ResponseEntity<Provider> response = providerController.addProvider(sampleProvider);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Provider A", response.getBody().getName());
    }

    @Test
    void addProviderBadRequestTest() {
        Provider invalidProvider = new Provider(null, null, null, null, null, null);
        ResponseEntity<Provider> response = providerController.addProvider(invalidProvider);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editProviderTest() {
        when(providerRepository.existsById(1L)).thenReturn(true);
        when(providerRepository.save(any(Provider.class))).thenReturn(sampleProvider);
        ResponseEntity<Provider> response = providerController.editProvider(1L, sampleProvider);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Provider A", response.getBody().getName());
    }

    @Test
    void editProviderBadRequestTest() {
        when(providerRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Provider> response = providerController.editProvider(1L, sampleProvider);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteProviderTest() {
        when(providerRepository.existsById(1L)).thenReturn(true);
        when(providerRepository.findById(1L)).thenReturn(Optional.of(sampleProvider));
        doNothing().when(providerRepository).deleteById(1L);
        ResponseEntity<Provider> response = providerController.deleteProvider(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteProviderNotFoundTest() {
        when(providerRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Provider> response = providerController.deleteProvider(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
