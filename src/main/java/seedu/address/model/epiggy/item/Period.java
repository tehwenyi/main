package seedu.address.model.epiggy.item;

/**
 * Represents a time period.
 * Guarantees: immutable
 */
public class Period {
    public static final String MESSAGE_CONSTRAINTS =
            "Time period is in terms of weeks and should only contain whole numbers of at least value 1.";
    public static final String VALIDATION_REGEX = "\\d+";
    private final int timePeriod;

    public Period(int timePeriod) {
        this.timePeriod = timePeriod;
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
}
