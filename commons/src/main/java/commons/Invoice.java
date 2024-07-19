package commons;

import java.time.LocalDate;
import java.util.Objects;

public class Invoice extends Document{
    private String meaning;
    private boolean hasBeenPaid;

    /**
     * Constructor for the Invoice class.
     * @param number    The number of the invoice.
     * @param series    The series of the invoice.
     * @param amount    The amount of the invoice.
     * @param date      The date of the invoice.
     * @param client    The client of the invoice.
     * @param provider  The provider of the invoice.
     * @param meaning   The meaning of the invoice.
     * @param hasBeenPaid  The status of the invoice.
     */
    public Invoice(long number, long series, long amount, LocalDate date, Client client,
                   Provider provider, String meaning, boolean hasBeenPaid) {
        super(number, series, amount, date, client, provider);
        this.meaning = meaning;
        this.hasBeenPaid = hasBeenPaid;
    }

    /**
     * Get the meaning of the invoice.
     * @return The meaning of the invoice.
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * Set the meaning of the invoice.
     * @param meaning The meaning of the invoice.
     */
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    /**
     * Get the status of the invoice.
     * @return The status of the invoice.
     */
    public boolean isHasBeenPaid() {
        return hasBeenPaid;
    }

    /**
     * Set the status of the invoice.
     * @param hasBeenPaid The status of the invoice.
     */
    public void setHasBeenPaid(boolean hasBeenPaid) {
        this.hasBeenPaid = hasBeenPaid;
    }

    /**
     * Compare the invoice with another object.
     * @param object The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Invoice invoice)) return false;
        if (!super.equals(object)) return false;
        return Objects.equals(meaning, invoice.meaning) && hasBeenPaid == invoice.hasBeenPaid;
    }

    /**
     * Get the hash code of the invoice.
     * @return The hash code of the invoice.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), meaning, hasBeenPaid);
    }

    /**
     * Get the string representation of the invoice.
     * @return The string representation of the invoice.
     */
    @Override
    public String toString() {
        return "Invoice{" +
            "meaning='" + meaning + '\'' +
            ", hasBeenPaid=" + hasBeenPaid +
            '}';
    }
}
