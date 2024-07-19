package commons;

import java.util.Objects;

public class Payment {
    private String meaning;
    private long number;

    /**
     * Constructor for the Payment class.
     * @param meaning   The meaning of the payment.
     * @param number    The number of the payment.
     */
    public Payment(String meaning, long number) {
        this.meaning = meaning;
        this.number = number;
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
        return number == payment.number && Objects.equals(meaning, payment.meaning);
    }

    /**
     * Get the hash code of the payment.
     * @return The hash code of the payment.
     */
    @Override
    public int hashCode() {
        return Objects.hash(meaning, number);
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
            '}';
    }
}
