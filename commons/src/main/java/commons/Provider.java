package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Provider extends Person {

    @Id
    @GeneratedValue(generator = "increment")
    private long id;

    /**
     * Constructor for the Provider class.
     * @param name      The name of the provider.
     * @param address   The address of the provider.
     * @param account   The account of the provider.
     * @param cif       The cif of the provider.
     * @param number    The number of the provider.
     * @param bank      The bank of the provider.
     */
    public Provider(String name, String address, String account, String cif, String number,
                    String bank) {
        super(name, address, account, cif, number, bank);
    }

    /**
     * Default constructor for the Provider class.
     */
    public Provider() {
    }

    /**
     * Get the id of the provider.
     * @return The id of the provider.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id of the provider.
     * @param id The id of the provider.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Compare the provider with another object.
     * @param object The object to compare the provider with.
     * @return True if the provider is equal to the object, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Provider provider)) return false;
        if (!super.equals(object)) return false;
        return id == provider.id;
    }

    /**
     * Get the hash code of the provider.
     * @return The hash code of the provider.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    /**
     * Get the string representation of the provider.
     * @return The string representation of the provider.
     */
    @Override
    public String toString() {
        return "Provider{" +
            "id=" + id +
            '}';
    }
}
