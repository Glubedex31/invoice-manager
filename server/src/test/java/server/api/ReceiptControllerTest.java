package server.api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import commons.Client;
import commons.Invoice;
import commons.Provider;
import commons.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import server.database.ReceiptRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ReceiptControllerTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptController receiptController;

    private Receipt sampleReceipt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleReceipt = new Receipt();
        sampleReceipt.setInvoice(new commons.Invoice());
    }

    @Test
    void getAllReceiptsTest() {
        when(receiptRepository.findAll()).thenReturn(Arrays.asList(sampleReceipt));
        List<Receipt> result = receiptController.getAllReceipts();
        assertFalse(result.isEmpty());
        assertNotNull(result.get(0));
    }

    @Test
    void getReceiptByIdFoundTest() {
        when(receiptRepository.existsById(1L)).thenReturn(true);
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(sampleReceipt));
        ResponseEntity<Receipt> response = receiptController.getReceiptById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getReceiptByIdNotFoundTest() {
        when(receiptRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Receipt> response = receiptController.getReceiptById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addReceiptTestBad() {
        when(receiptRepository.save(any(Receipt.class))).thenReturn(sampleReceipt);
        ResponseEntity<Receipt> response = receiptController.addReceipt(sampleReceipt);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void addReceiptTest() {
        when(receiptRepository.save(any(Receipt.class))).thenReturn(sampleReceipt);

        Client client = new Client("John Doe", "123 Main St", "123456789", "CIF123", "555-1234", "Bank A");
        Provider provider = new Provider("Alice Smith", "456 Elm St", "987654321", "CIF987", "777-1234", "Bank C");
        LocalDate testDate = LocalDate.of(2024, 7, 19);
        Invoice invoice = new Invoice(100, 200, 1500, testDate, client, provider, "Consulting Services", true);
        sampleReceipt = new Receipt(101, 201, 1600, testDate, client, provider, invoice);

        ResponseEntity<Receipt> response = receiptController.addReceipt(sampleReceipt);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void addReceiptBadRequestTest() {
        Receipt invalidReceipt = new Receipt(); // Assume it does not meet validation criteria
        ResponseEntity<Receipt> response = receiptController.addReceipt(invalidReceipt);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editReceiptTest() {
        when(receiptRepository.existsById(1L)).thenReturn(true);
        when(receiptRepository.save(any(Receipt.class))).thenReturn(sampleReceipt);

        Client client = new Client("John Doe", "123 Main St", "123456789", "CIF123", "555-1234", "Bank A");
        Provider provider = new Provider("Alice Smith", "456 Elm St", "987654321", "CIF987", "777-1234", "Bank C");
        LocalDate testDate = LocalDate.of(2024, 7, 19);
        Invoice invoice = new Invoice(100, 200, 1500, testDate, client, provider, "Consulting Services", true);
        sampleReceipt = new Receipt(101, 201, 1600, testDate, client, provider, invoice);

        ResponseEntity<Receipt> response = receiptController.editReceipt(1L, sampleReceipt);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void editReceiptBadRequestTest() {
        when(receiptRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Receipt> response = receiptController.editReceipt(1L, sampleReceipt);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteReceiptTest() {
        when(receiptRepository.existsById(1L)).thenReturn(true);
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(sampleReceipt));
        doNothing().when(receiptRepository).deleteById(1L);
        ResponseEntity<Receipt> response = receiptController.deleteReceipt(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteReceiptNotFoundTest() {
        when(receiptRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Receipt> response = receiptController.deleteReceipt(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
