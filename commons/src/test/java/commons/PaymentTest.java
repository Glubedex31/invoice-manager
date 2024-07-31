package commons;

import static org.junit.jupiter.api.Assertions.*;

import commons.Payment;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void testDefaultConstructor() {
        Payment payment = new Payment();
        assertNull(payment.getMeaning());
        assertEquals(0, payment.getNumber());
        assertFalse(payment.isBank());
    }

    @Test
    void testParameterizedConstructor() {
        Payment payment = new Payment("Service", 123, true);
        assertEquals("Service", payment.getMeaning());
        assertEquals(123, payment.getNumber());
        assertTrue(payment.isBank());
    }

    @Test
    void testSettersAndGetters() {
        Payment payment = new Payment();
        payment.setMeaning("Service");
        payment.setNumber(123);
        payment.setBank(true);

        assertEquals("Service", payment.getMeaning());
        assertEquals(123, payment.getNumber());
        assertTrue(payment.isBank());
    }

    @Test
    void testEquals() {
        Payment payment1 = new Payment("Service", 123, true);
        Payment payment2 = new Payment("Service", 123, true);
        Payment payment3 = new Payment("Goods", 124, false);

        assertEquals(payment1, payment2);
        assertNotEquals(payment1, payment3);
        assertNotEquals(payment1, null);
        assertNotEquals(payment1, new Object());
    }

    @Test
    void testHashCode() {
        Payment payment1 = new Payment("Service", 123, true);
        Payment payment2 = new Payment("Service", 123, true);

        assertEquals(payment1.hashCode(), payment2.hashCode());
    }

    @Test
    void testToString() {
        Payment payment = new Payment("Service", 123, true);
        String expected = "Payment{meaning='Service', number=123, id=0, isBank=true}";
        assertEquals(expected, payment.toString());
    }
}
