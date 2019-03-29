package seedu.address.model.epiggy.item;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Price.
 * Guarantees: immutable}
 */
public class Price {
    // Change message constraints when we change amount from int to float
    public static final String MESSAGE_CONSTRAINTS =
            "Cost should only contain whole numbers of at least value 1.";
    public static final String VALIDATION_REGEX = "^0*[1-9]\\d*$";
    private final int amount;

    public Price(int amount) {
        this.amount = amount;
    }

    // Constructor for test purposes
    public Price (String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        amount = Integer.parseInt(price);
    }

    public Price deduct(Price amountToDeduct) {
        return new Price(this.amount - amountToDeduct.getAmount());
    }

    public int getAmount() {
        return amount;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(amount);
    }
}
