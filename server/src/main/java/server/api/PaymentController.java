package server.api;

import commons.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.PaymentRepository;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    /**
     * Constructor for the PaymentController.
     *
     * @param paymentRepository the repository for payments
     */
    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Get all payments.
     *
     * @return all payments
     */
    @GetMapping(path = { "", "/" })
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    /**
     * Get a payment by its id.
     *
     * @param id the id of the payment
     * @return the payment with the given id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") long id) {
        if (!paymentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paymentRepository.findById(id).get());
    }

    /**
     * Edit a payment.
     *
     * @param id the id of the payment
     * @param updatedPayment the updated payment
     * @return the new payment
     */
    @PutMapping("/{id}")
    public ResponseEntity<Payment> editPayment(@PathVariable("id") long id,
                                               @RequestBody Payment updatedPayment) {
        if (!paymentRepository.existsById(id) || isNullOrEmptyPayment(updatedPayment)) {
            return ResponseEntity.badRequest().build();
        }
        Payment saved = paymentRepository.save(updatedPayment);
        return ResponseEntity.ok(saved);
    }

    /**
     * Add a payment.
     *
     * @param payment the payment to add
     * @return the added payment
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        if (isNullOrEmptyPayment(payment)) {
            return ResponseEntity.badRequest().build();
        }
        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.ok(saved);
    }

    /**
     * Delete a payment by its id.
     *
     * @param id the id of the payment to delete
     * @return the deleted payment
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Payment> deletePayment(@PathVariable("id") long id) {
        if (!paymentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Payment payment = paymentRepository.findById(id).get();
        paymentRepository.deleteById(id);
        return ResponseEntity.ok(payment);
    }

    /**
     * Check if a payment is null or has an empty meaning.
     *
     * @param payment the payment to check
     * @return true if the payment is null or has an empty meaning, false otherwise
     */
    private boolean isNullOrEmptyPayment(Payment payment) {
        return payment == null || payment.getMeaning() == null || payment.getMeaning().isBlank();
    }
}
