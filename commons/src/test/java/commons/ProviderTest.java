package commons;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProviderTest {
    private Provider provider;

    @BeforeEach
    void setUp() {
        provider = new Provider("Alice", "789 Oak St", "987654321", "CIF987", "777-1234", "Bank C");
    }

    @Test
    void testProviderGettersAndSetters() {
        assertEquals("Alice", provider.getName());
        provider.setName("Alice B");
        assertEquals("Alice B", provider.getName());
    }

    @Test
    void testEquals() {
        Provider otherProvider = new Provider("Alice", "789 Oak St", "987654321", "CIF987", "777-1234", "Bank C");
        assertEquals(provider, otherProvider);
    }

    @Test
    void testHashCode() {
        Provider sameProvider = new Provider("Alice", "789 Oak St", "987654321", "CIF987", "777-1234", "Bank C");
        assertEquals(provider.hashCode(), sameProvider.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Provider{id=0}";
        assertEquals(expected, provider.toString());
    }
}
