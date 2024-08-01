package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Receipt extends Document{

    @OneToOne
    private Invoice invoice;

    /**
     * Constructor for the Receipt class.
     * @param number    The number of the receipt.
     * @param series    The series of the receipt.
     * @param amount    The amount of the receipt.
     * @param date      The date of the receipt.
     * @param client    The client of the receipt.
     * @param provider  The provider of the receipt.
     * @param invoice   The invoice for the receipt.
     */
    public Receipt(long number, long series, long amount, LocalDate date,
                   Client client, Provider provider, Invoice invoice) {
        super(number, series, amount, date, client, provider);
        this.invoice = invoice;
    }

    /**
     * Constructor for the Receipt class.
     */
    public Receipt() {
    }

    public Receipt(Invoice invoice) {
        super(invoice.getNumber(), invoice.getSeries(), invoice.getAmount(), invoice.getDate(), invoice.getClient(), invoice.getProvider());
        this.invoice = invoice;
    }

    /**
     * Get the invoice for the receipt.
     * @return The invoice for the receipt.
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * Set the invoice for the receipt.
     * @param invoice The invoice for the receipt.
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /**
     * Check if the receipt is equal to another object.
     * @param object The object to compare.
     * @return True if the receipt is equal to the object, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Receipt receipt)) return false;
        if (!super.equals(object)) return false;
        return Objects.equals(invoice, receipt.invoice);
    }

    /**
     * Get the hash code of the receipt.
     * @return The hash code of the receipt.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), invoice);
    }

    /**
     * Get the string representation of the receipt.
     * @return The string representation of the receipt.
     */
    @Override
    public String toString() {
        return "Receipt{" +
            "invoice=" + invoice +
            '}';
    }
}
