package seedu.address.testutil.epiggy;

import seedu.address.model.epiggy.Savings;

/**
 * A utility class to help with building Savings objects.
 */
public class SavingsBuilder {

    public static final double DEFAULT_SAVINGS = 300;

    private double savings;

    public SavingsBuilder() {
        savings = DEFAULT_SAVINGS;
    }

    /**
     * Sets the {@code savings} of the {@code Savings} that we are building.
     */
    public SavingsBuilder withSavings(double savings) {
        this.savings = savings;
        return this;
    }

    public Savings build() {
        return new Savings(savings);
    }
}
