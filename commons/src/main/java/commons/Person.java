package commons;

import java.util.Objects;

public abstract class Person {
    private String name;
    private String address;
    private String account;
    private String cif;
    private String number;
    private String bank;

    /**
     * Constructor for the Person class.
     * @param name      The name of the person.
     * @param address   The address of the person.
     * @param account   The account of the person.
     * @param cif       The cif of the person.
     * @param number    The number of the person.
     * @param bank      The bank of the person.
     */
    public Person(String name, String address, String account, String cif, String number,
                  String bank) {
        this.name = name;
        this.address = address;
        this.account = account;
        this.cif = cif;
        this.number = number;
        this.bank = bank;
    }

    /**
     * Default constructor for the Person class.
     */
    public Person() {
    }

    /**
     * Get the name of the person.
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the person.
     * @param name The name of the person.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the address of the person.
     * @return The address of the person.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address of the person.
     * @param address The address of the person.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the account of the person.
     * @return The account of the person.
     */
    public String getAccount() {
        return account;
    }

    /**
     * Set the account of the person.
     * @param account The account of the person.
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Get the cif of the person.
     * @return The cif of the person.
     */
    public String getCif() {
        return cif;
    }

    /**
     * Set the cif of the person.
     * @param cif The cif of the person.
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * Get the number of the person.
     * @return The number of the person.
     */
    public String getNumber() {
        return number;
    }

    /**
     * Set the number of the person.
     * @param number The number of the person.
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Get the bank of the person.
     * @return The bank of the person.
     */
    public String getBank() {
        return bank;
    }

    /**
     * Set the bank of the person.
     * @param bank The bank of the person.
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * Compare two objects of the Person class.
     * @param object The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Person person)) return false;
        return Objects.equals(name, person.name) &&
            Objects.equals(address, person.address) &&
            Objects.equals(account, person.account) &&
            Objects.equals(cif, person.cif) &&
            Objects.equals(number, person.number) &&
            Objects.equals(bank, person.bank);
    }

    /**
     * Get the hash code of the person.
     * @return The hash code of the person.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, address, account, cif, number, bank);
    }

    /**
     * Get the string representation of the person.
     * @return The string representation of the person.
     */
    @Override
    public String toString() {
        return "Person{" +
            "name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", account='" + account + '\'' +
            ", cif='" + cif + '\'' +
            ", number='" + number + '\'' +
            ", bank='" + bank + '\'' +
            '}';
    }
}
