package commons;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client("Bob", "101 Pine St", "123321123", "CIF321", "888-1234", "Bank D");
    }

    @Test
    void testClientGettersAndSetters() {
        assertEquals("Bob", client.getName());
        client.setId(1);
        assertEquals(1, client.getId());
    }

    @Test
    void testEquals() {
        Client otherClient = new Client("Bob", "101 Pine St", "123321123", "CIF321", "888-1234", "Bank D");
        assertEquals(client, otherClient);
    }

    @Test
    void testHashCode() {
        Client sameClient = new Client("Bob", "101 Pine St", "123321123", "CIF321", "888-1234", "Bank D");
        assertEquals(client.hashCode(), sameClient.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Person{name='Bob', address='101 Pine St', account='123321123', cif='CIF321', number='888-1234', bank='Bank D'}";
        assertEquals(expected, client.toString());
    }
}
