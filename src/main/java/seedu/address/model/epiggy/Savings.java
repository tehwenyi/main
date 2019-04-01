package seedu.address.model.epiggy;

/**
 * Represents a saving in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Savings {
    private double savings;

    public Savings() {
        this.savings = 0.00f;
    }

    public Savings(double savings) {
        this.savings = savings;
    }

    public Savings(Savings savings) {
        this.savings = savings.getSavings();
    }

    public double getSavings() {
        return savings;
    }

    public void addSavings(double increment) {
        savings += increment;
    }

    public void deductSavings(double decrement) {
        savings -= decrement;
    }

    @Override
    public String toString() {
        return String.format("$%.2f", savings);
    }
}
