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

    public double getSavings() {
        return savings;
    }

    public Savings addSavings(double increment) {
        return new Savings(savings + increment);
    }

    public Savings deductSavings(double decrement) {
        return new Savings(savings - decrement);
    }

    @Override
    public String toString() {
        return String.format("$%.2f", savings);
    }
}
