package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Client extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Constructor for the Client class.
     * @param name      The name of the client.
     * @param address   The address of the client.
     * @param account   The account of the client.
     * @param cif       The cif of the client.
     * @param number    The number of the client.
     * @param bank      The bank of the client.
     */
    public Client(String name, String address, String account, String cif, String number,
                  String bank) {
        super(name, address, account, cif, number, bank);
    }

    /**
     * Default constructor for the Client class.
     */
    public Client() {
    }

    /**
     * Get the id of the client.
     * @return The id of the client.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id of the client.
     * @param id The id of the client.
     */
    public void setId(long id) {
        this.id = id;
    }
}
