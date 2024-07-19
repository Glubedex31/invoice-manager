package commons;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DocumentTest {
    private Document document;
    private Client client;
    private Provider provider;
    private final LocalDate testDate = LocalDate.of(2024, 7, 19);

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        client = new Client("test", "test", "test", "test", "test", "Test");
        provider = new Provider("test", "test", "test", "test", "test", "Test"); // Assuming constructors/details for Provider
        document = new Document(100, 200, 1500, testDate, client, provider);
    }

    @Test
    void testGetId() {
        document.setId(1);
        assertEquals(1, document.getId(), "Check id getter");
    }

    @Test
    void testSetId() {
        document.setId(2);
        assertEquals(2, document.getId(), "Check id setter");
    }

    @Test
    void testGetNumber() {
        assertEquals(100, document.getNumber(), "Check number getter");
    }

    @Test
    void testSetNumber() {
        document.setNumber(101);
        assertEquals(101, document.getNumber(), "Check number setter");
    }

    @Test
    void testGetSeries() {
        assertEquals(200, document.getSeries(), "Check series getter");
    }

    @Test
    void testSetSeries() {
        document.setSeries(201);
        assertEquals(201, document.getSeries(), "Check series setter");
    }

    @Test
    void testGetAmount() {
        assertEquals(1500, document.getAmount(), "Check amount getter");
    }

    @Test
    void testSetAmount() {
        document.setAmount(1600);
        assertEquals(1600, document.getAmount(), "Check amount setter");
    }

    @Test
    void testGetDate() {
        assertEquals(testDate, document.getDate(), "Check date getter");
    }

    @Test
    void testSetDate() {
        LocalDate newDate = LocalDate.of(2025, 1, 1);
        document.setDate(newDate);
        assertEquals(newDate, document.getDate(), "Check date setter");
    }

    @Test
    void testGetClient() {
        assertSame(client, document.getClient(), "Check client getter");
    }

    @Test
    void testSetClient() {
        Client newClient = new Client("test", "test", "test", "test", "test", "Test2");
        document.setClient(newClient);
        assertSame(newClient, document.getClient(), "Check client setter");
    }

    @Test
    void testGetProvider() {
        assertSame(provider, document.getProvider(), "Check provider getter");
    }

    @Test
    void testSetProvider() {
        Provider newProvider = new Provider("test", "test", "test", "test", "test", "Test2");
        document.setProvider(newProvider);
        assertSame(newProvider, document.getProvider(), "Check provider setter");
    }

    @Test
    void testEquals() {
        Document sameDocument = new Document(100, 200, 1500, testDate, client, provider);
        Document differentDocument = new Document(101, 201, 1600, testDate, client, provider);
        assertEquals(sameDocument, document, "Check documents are equal");
        assertNotEquals(differentDocument, document, "Check documents are not equal");
    }

    @Test
    void testHashCode() {
        Document sameDocument = new Document(100, 200, 1500, testDate, client, provider);
        assertEquals(sameDocument.hashCode(), document.hashCode(), "Check hash codes are equal");
    }

    @Test
    void testToString() {
        document.setId(1);
        String expected = "Document{id=1, number=100, series=200, amount=1500, date=" + testDate +
            ", client=" + client + ", provider=" + provider + '}';
        assertEquals(expected, document.toString(), "Check toString method");
    }
}
