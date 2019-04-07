package seedu.address.model.Expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Cost.
 * Guarantees: immutable
 */
public class Cost {
    public static final String MESSAGE_CONSTRAINTS =
            "Cost should be a non-negative real number";
    private final double amount;

    public Cost(double amount) {
        this.amount = amount;
    }

    public Cost(String amount) {
        requireNonNull(amount);
        checkArgument(isValidCost(amount), MESSAGE_CONSTRAINTS);
        this.amount = Double.parseDouble(amount);
    }

    public Cost deduct(Cost amountToDeduct) {
        return new Cost(this.amount - amountToDeduct.getAmount());
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Returns true if a given string is a valid Cost.
     */
    public static boolean isValidCost(String test) {
        try {
            double d = Double.parseDouble(test);
            return d >= 0.;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("$%.2f", amount);
    }
}
