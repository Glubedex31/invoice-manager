package server.api;

import commons.Receipt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ReceiptRepository;

import java.util.List;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {
    private final ReceiptRepository receiptRepository;

    /**
     * Constructor for the ReceiptController.
     *
     * @param receiptRepository the repository for receipts
     */
    public ReceiptController(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    /**
     * Get all receipts.
     *
     * @return all receipts
     */
    @GetMapping(path = { "", "/" })
    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    /**
     * Get a receipt by its id.
     *
     * @param id the id of the receipt
     * @return the receipt with the given id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable("id") long id) {
        if (!receiptRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receiptRepository.findById(id).get());
    }

    /**
     * Edit a receipt.
     *
     * @param id the id of the receipt
     * @param updatedReceipt the updated receipt
     * @return the new receipt
     */
    @PutMapping("/{id}")
    public ResponseEntity<Receipt> editReceipt(@PathVariable("id") long id,
                                               @RequestBody Receipt updatedReceipt) {
        if (!receiptRepository.existsById(id) || isNullOrEmptyReceipt(updatedReceipt)) {
            return ResponseEntity.badRequest().build();
        }
        Receipt saved = receiptRepository.save(updatedReceipt);
        return ResponseEntity.ok(saved);
    }

    /**
     * Add a receipt.
     *
     * @param receipt the receipt to add
     * @return the added receipt
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Receipt> addReceipt(@RequestBody Receipt receipt) {
        if(isNullOrEmptyReceipt(receipt)) {
            return ResponseEntity.badRequest().build();
        }
        Receipt saved = receiptRepository.save(receipt);
        return ResponseEntity.ok(saved);
    }

    /**
     * Delete a receipt by its id.
     *
     * @param id the id of the receipt to delete
     * @return the deleted receipt
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Receipt> deleteReceipt(@PathVariable("id") long id) {
        if (!receiptRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Receipt receipt = receiptRepository.findById(id).get();
        receiptRepository.deleteById(id);
        return ResponseEntity.ok(receipt);
    }

    /**
     * Check if a receipt is null or has null fields.
     * @param receipt the receipt to check
     * @return  true if the receipt is null or has null fields, false otherwise
     */
    private static boolean isNullOrEmptyReceipt(Receipt receipt) {
        //TODO: Implement this method
        return receipt == null || receipt.getInvoice() == null || receipt.getClient() == null
            || receipt.getProvider() == null;
    }
}
