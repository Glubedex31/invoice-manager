package server.api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import commons.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import server.database.InvoiceRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class InvoiceControllerTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceController invoiceController;

    private Invoice sampleInvoice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleInvoice = new Invoice(100, 200, 1500, LocalDate.now(), null,
            null, "Service", true);
        sampleInvoice.setId(1L);
    }

    @Test
    void getAllInvoicesTest() {
        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(sampleInvoice));
        List<Invoice> result = invoiceController.getAllInvoices();
        assertFalse(result.isEmpty());
        assertEquals("Service", result.get(0).getMeaning());
    }

    @Test
    void getInvoiceByIdFoundTest() {
        when(invoiceRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(sampleInvoice));
        ResponseEntity<Invoice> response = invoiceController.getInvoiceById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Service", response.getBody().getMeaning());
    }

    @Test
    void getInvoiceByIdNotFoundTest() {
        when(invoiceRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Invoice> response = invoiceController.getInvoiceById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addInvoiceTest() {
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(sampleInvoice);
        ResponseEntity<Invoice> response = invoiceController.add(sampleInvoice);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Service", response.getBody().getMeaning());
    }

    @Test
    void addInvoiceBadRequestTest() {
        Invoice invalidInvoice = new Invoice();
        ResponseEntity<Invoice> response = invoiceController.add(invalidInvoice);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editInvoiceTest() {
        when(invoiceRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(sampleInvoice);
        ResponseEntity<Invoice> response = invoiceController.editInvoice(1L, sampleInvoice);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Service", response.getBody().getMeaning());
    }

    @Test
    void editInvoiceBadRequestTest() {
        Invoice invalidInvoice = new Invoice();
        ResponseEntity<Invoice> response = invoiceController.editInvoice(1L, invalidInvoice);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteInvoiceNotFoundTest() {
        when(invoiceRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Invoice> response = invoiceController.deleteInvoice(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteInvoiceTest() {
        when(invoiceRepository.existsById(1L)).thenReturn(true);
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(sampleInvoice));
        doNothing().when(invoiceRepository).deleteById(1L);
        ResponseEntity<Invoice> response = invoiceController.deleteInvoice(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
