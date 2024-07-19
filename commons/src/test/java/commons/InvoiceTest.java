package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {
    private Invoice invoice;
    private Client client;
    private Provider provider;
    private final LocalDate testDate = LocalDate.of(2024, 7, 19);

    @BeforeEach
    void setUp() {
        client = new Client("test", "test", "test", "test", "test", "test");
        provider = new Provider("test", "test", "test", "test", "test", "test");
        invoice = new Invoice(100, 200, 1500, testDate, client, provider, "Consulting Services", true);
    }

    @Test
    void testGetMeaning() {
        assertEquals("Consulting Services", invoice.getMeaning(), "Check meaning getter");
    }

    @Test
    void testSetMeaning() {
        invoice.setMeaning("Legal Services");
        assertEquals("Legal Services", invoice.getMeaning(), "Check meaning setter");
    }

    @Test
    void testIsHasBeenPaid() {
        assertTrue(invoice.isHasBeenPaid(), "Check hasBeenPaid getter");
    }

    @Test
    void testSetHasBeenPaid() {
        invoice.setHasBeenPaid(false);
        assertFalse(invoice.isHasBeenPaid(), "Check hasBeenPaid setter");
    }

    @Test
    void testEquals() {
        Invoice sameInvoice = new Invoice(100, 200, 1500, testDate, client, provider, "Consulting Services", true);
        Invoice differentInvoice = new Invoice(101, 201, 1600, testDate, client, provider, "Consulting Services", false);
        assertEquals(sameInvoice, invoice, "Check invoices are equal");
        assertNotEquals(differentInvoice, invoice, "Check invoices are not equal");
    }

    @Test
    void testHashCode() {
        Invoice sameInvoice = new Invoice(100, 200, 1500, testDate, client, provider, "Consulting Services", true);
        assertEquals(sameInvoice.hashCode(), invoice.hashCode(), "Check hash codes are equal");
    }

    @Test
    void testToString() {
        String expected = "Invoice{meaning='Consulting Services', hasBeenPaid=true}";
        assertEquals(expected, invoice.toString(), "Check toString method");
    }

}