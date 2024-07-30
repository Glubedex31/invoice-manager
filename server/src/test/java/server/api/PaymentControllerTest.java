package server.api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import commons.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import server.database.PaymentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class PaymentControllerTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentController paymentController;

    private Payment samplePayment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        samplePayment = new Payment("Service Charge", 100, false);
        samplePayment.setId(1); // Simulating that it's fetched from a database.
    }

    @Test
    void getAllPaymentsTest() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(samplePayment));
        List<Payment> result = paymentController.getAllPayments();
        assertFalse(result.isEmpty());
        assertEquals("Service Charge", result.get(0).getMeaning());
    }

    @Test
    void getPaymentByIdFoundTest() {
        when(paymentRepository.existsById(1L)).thenReturn(true);
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(samplePayment));
        ResponseEntity<Payment> response = paymentController.getPaymentById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Service Charge", response.getBody().getMeaning());
    }

    @Test
    void getPaymentByIdNotFoundTest() {
        when(paymentRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Payment> response = paymentController.getPaymentById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addPaymentTest() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(samplePayment);
        ResponseEntity<Payment> response = paymentController.addPayment(samplePayment);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Service Charge", response.getBody().getMeaning());
    }

    @Test
    void addPaymentBadRequestTest() {
        Payment invalidPayment = new Payment("", 0, false); // Invalid because meaning is empty.
        ResponseEntity<Payment> response = paymentController.addPayment(invalidPayment);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editPaymentTest() {
        when(paymentRepository.existsById(1L)).thenReturn(true);
        when(paymentRepository.save(any(Payment.class))).thenReturn(samplePayment);
        ResponseEntity<Payment> response = paymentController.editPayment(1L, samplePayment);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Service Charge", response.getBody().getMeaning());
    }

    @Test
    void editPaymentBadRequestTest() {
        when(paymentRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Payment> response = paymentController.editPayment(1L, samplePayment);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deletePaymentTest() {
        when(paymentRepository.existsById(1L)).thenReturn(true);
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(samplePayment));
        doNothing().when(paymentRepository).deleteById(1L);
        ResponseEntity<Payment> response = paymentController.deletePayment(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deletePaymentNotFoundTest() {
        when(paymentRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Payment> response = paymentController.deletePayment(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
