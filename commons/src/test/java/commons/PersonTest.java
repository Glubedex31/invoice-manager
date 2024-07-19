package commons;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonTest {
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("John Doe", "123 Main St", "123456789", "CIF123", "555-1234", "Bank A") {
            // Anonymous subclass instance
        };
    }

    @Test
    void gettersAndSetters() {
        person.setName("Jane Doe");
        assertEquals("Jane Doe", person.getName());

        person.setAddress("456 Elm St");
        assertEquals("456 Elm St", person.getAddress());

        person.setAccount("987654321");
        assertEquals("987654321", person.getAccount());

        person.setCif("CIF987");
        assertEquals("CIF987", person.getCif());

        person.setNumber("555-4321");
        assertEquals("555-4321", person.getNumber());

        person.setBank("Bank B");
        assertEquals("Bank B", person.getBank());
    }

    @Test
    void testEquals() {
        Person otherPerson = new Person("John Doe", "123 Main St", "123456789", "CIF123", "555-1234", "Bank A") {
            // Another anonymous subclass instance
        };
        assertEquals(person, otherPerson);
    }

    @Test
    void testHashCode() {
        Person samePerson = new Person("John Doe", "123 Main St", "123456789", "CIF123", "555-1234", "Bank A") {
            // Another anonymous subclass instance
        };
        assertEquals(person.hashCode(), samePerson.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Person{name='John Doe', address='123 Main St', account='123456789', cif='CIF123', number='555-1234', bank='Bank A'}";
        assertEquals(expected, person.toString());
    }
}