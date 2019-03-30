package seedu.address.model.epiggy.item;

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

    public Cost deduct(Cost amountToDeduct) {
        return new Cost(this.amount - amountToDeduct.getAmount());
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
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
