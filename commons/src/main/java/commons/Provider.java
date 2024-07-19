package commons;

public class Provider extends Person {

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
}
