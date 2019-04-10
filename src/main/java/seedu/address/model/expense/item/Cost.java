package seedu.address.model.expense.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Cost.
 * Guarantees: immutable
 */
public class Cost {
    public static final String MESSAGE_CONSTRAINTS = "Cost should be an numerical amount not more than 6 digits.";
    private static final Pattern AMOUNT_FORMAT = Pattern.compile("^(?!\\.?$)\\d{0,6}(\\.\\d{0,2})?$");
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
            Matcher matcher = AMOUNT_FORMAT.matcher(test);
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_CONSTRAINTS));
            }
            double d = Double.parseDouble(test);
            return d > 0. && d < 1000000.0;
        } catch (NumberFormatException | ParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("$%.2f", amount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Cost)) {
            return false;
        }
        return amount == ((Cost) other).getAmount();
    }
}
