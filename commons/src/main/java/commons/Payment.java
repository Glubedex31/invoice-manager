package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String meaning;
    private long number;
    private boolean isBank;

    /**
     * Constructor for the Payment class.
     * @param meaning   The meaning of the payment.
     * @param number    The number of the payment.
     * @param isBank    True if the payment is a bank payment, false otherwise.
     */
    public Payment(String meaning, long number, boolean isBank) {
        this.meaning = meaning;
        this.number = number;
        this.isBank = isBank;
    }

    /**
     * Default constructor for the Payment class.
     */
    public Payment() {
    }

    /**
     * Get the meaning of the payment.
     * @return The meaning of the payment.
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * Get the number of the payment.
     * @return The number of the payment.
     */
    public long getNumber() {
        return number;
    }

    /**
     * Set the meaning of the payment.
     * @param meaning The meaning of the payment.
     */
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    /**
     * Set the number of the payment.
     * @param number The number of the payment.
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * Compare the payment with another object.
     * @param object The object to compare the payment with.
     * @return True if the payment is equal to the object, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Payment payment)) return false;
        return number == payment.number && Objects.equals(meaning, payment.meaning)
            && id == payment.id && isBank == payment.isBank;
    }

    /**
     * Get the hash code of the payment.
     * @return The hash code of the payment.
     */
    @Override
    public int hashCode() {
        return Objects.hash(meaning, number, id, isBank);
    }

    /**
     * Get the string representation of the payment.
     * @return The string representation of the payment.
     */
    @Override
    public String toString() {
        return "Payment{" +
            "meaning='" + meaning + '\'' +
            ", number=" + number +
            ", id=" + id +
            ", isBank=" + isBank +
            '}';
    }

    /**
     * Get the id of the payment.
     * @return The id of the payment.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id of the payment.
     * @param id The id of the payment.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Check if the payment is a bank payment.
     * @return True if the payment is a bank payment, false otherwise.
     */
    public boolean isBank() {
        return isBank;
    }

    /**
     * Set the payment to be a bank payment.
     * @param bank True if the payment is a bank payment, false otherwise.
     */
    public void setBank(boolean bank) {
        isBank = bank;
    }
}
