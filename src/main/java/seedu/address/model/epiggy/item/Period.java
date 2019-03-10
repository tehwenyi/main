package seedu.address.model.epiggy.item;

/**
 * Represents a time period.
 * Guarantees: immutable
 */
public class Period {
    private final int timePeriod;

    public Period(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public int getTimePeriod() {
        return timePeriod;
    }
}
