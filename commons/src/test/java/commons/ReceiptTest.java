package commons;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ReceiptTest {
    private Receipt receipt;
    private Invoice invoice;
    private Client client;
    private Provider provider;
    private final LocalDate testDate = LocalDate.of(2024, 7, 19);

    @BeforeEach
    void setUp() {
        client = new Client("John Doe", "123 Main St", "123456789", "CIF123", "555-1234", "Bank A");
        provider = new Provider("Alice Smith", "456 Elm St", "987654321", "CIF987", "777-1234", "Bank C");
        invoice = new Invoice(100, 200, 1500, testDate, client, provider, "Consulting Services", true);
        receipt = new Receipt(101, 201, 1600, testDate, client, provider, invoice);
    }

    @Test
    void testGetInvoice() {
        assertSame(invoice, receipt.getInvoice());
    }

    @Test
    void testSetInvoice() {
        Invoice newInvoice = new Invoice(102, 202, 1700, testDate, client, provider, "Legal Services", false);
        receipt.setInvoice(newInvoice);
        assertSame(newInvoice, receipt.getInvoice());
    }

    @Test
    void testEquals() {
        Receipt sameReceipt = new Receipt(101, 201, 1600, testDate, client, provider, invoice);
        Receipt differentReceipt = new Receipt(101, 201, 1600, testDate, client, provider, new Invoice(102, 202, 1700, testDate, client, provider, "Legal Services", false));
        assertEquals(sameReceipt, receipt);
        assertNotEquals(differentReceipt, receipt);
    }

    @Test
    void testHashCode() {
        Receipt sameReceipt = new Receipt(101, 201, 1600, testDate, client, provider, invoice);
        assertEquals(receipt.hashCode(), sameReceipt.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Receipt{invoice=Invoice{meaning='Consulting Services', hasBeenPaid=true}}";
        assertEquals(expected, receipt.toString());
    }
}
