package commons;

public class Client extends Person {
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
