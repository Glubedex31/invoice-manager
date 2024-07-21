package commons;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long number;
    private long series;
    private long amount;
    private LocalDate date;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Provider provider;

    /**
     * Constructor for the Document class.
     * @param number    The number of the document.
     * @param series    The series of the document.
     * @param amount    The amount of the document.
     * @param date      The date of the document.
     * @param client    The client of the document.
     * @param provider  The provider of the document.
     */
    public Document(long number, long series, long amount, LocalDate date, Client client,
                    Provider provider) {
        this.number = number;
        this.series = series;
        this.amount = amount;
        this.date = date;
        this.client = client;
        this.provider = provider;
    }

    /**
     * Default constructor for the Document class.
     */
    public Document() {
    }

    /**
     * Get the id of the document.
     * @return The id of the document.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the id of the document.
     * @param id The id of the document.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the number of the document.
     * @return The number of the document.
     */
    public long getNumber() {
        return number;
    }

    /**
     * Set the number of the document.
     * @param number The number of the document.
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * Get the series of the document.
     * @return The series of the document.
     */
    public long getSeries() {
        return series;
    }

    /**
     * Set the series of the document.
     * @param series The series of the document.
     */
    public void setSeries(long series) {
        this.series = series;
    }

    /**
     * Get the amount of the document.
     * @return The amount of the document.
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Set the amount of the document.
     * @param amount The amount of the document.
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * Get the date of the document.
     * @return The date of the document.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set the date of the document.
     * @param date The date of the document.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get the client of the document.
     * @return The client of the document.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Set the client of the document.
     * @param client The client of the document.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Get the provider of the document.
     * @return The provider of the document.
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Set the provider of the document.
     * @param provider The provider of the document.
     */
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    /**
     * Check if two documents are equal.
     * @param object The object to compare.
     * @return True if the documents are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Document document)) return false;
        return id == document.id && number == document.number && series == document.series &&
            amount == document.amount && Objects.equals(date, document.date) &&
            Objects.equals(client, document.client) &&
            Objects.equals(provider, document.provider);
    }

    /**
     * Get the hash code of the document.
     * @return The hash code of the document.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, number, series, amount, date, client, provider);
    }

    /**
     * Get the string representation of the document.
     * @return The string representation of the document.
     */
    @Override
    public String toString() {
        return "Document{" +
            "id=" + id +
            ", number=" + number +
            ", series=" + series +
            ", amount=" + amount +
            ", date=" + date +
            ", client=" + client +
            ", provider=" + provider +
            '}';
    }
}
