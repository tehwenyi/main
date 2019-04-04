package seedu.address.model.epiggy.item;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a time period.
 * Guarantees: immutable
 */
public class Period {
    // TODO PERIOD CANNOT BE NEGATIVE
    public static final String MESSAGE_CONSTRAINTS =
            "Time period is in terms of days and should only contain whole numbers of at least value 1.";
    public static final String VALIDATION_REGEX = "\\d+";
    private final int timePeriod;

    public Period(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Period(String period) {
        requireNonNull(period);
        checkArgument(isValidPeriod(period), MESSAGE_CONSTRAINTS);
        timePeriod = Integer.parseInt(period);
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    /**
     * Returns true if a given string is a valid time period.
     */
    public static boolean isValidPeriod(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Period)) {
            return false;
        }

        Period p = (Period) o;
        return this.timePeriod == p.getTimePeriod();
    }

    @Override
    public String toString() {
        return Integer.toString(timePeriod);
    }
}
