package seedu.address.model.epiggy;

import java.util.Date;

import seedu.address.model.epiggy.item.Period;
import seedu.address.model.epiggy.item.Price;

/**
 * Represents a Budget in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {
    private final Price amount;
    private final Date startDate;
    private final Period period;

    /**
     * Represents a Budget in the expense book.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Budget(Price amount, Date startDate, Period period) {
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
    }

    /**
     * A second constructor, temporary.
     * Represents a Budget in the expense book.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Budget(Price amount, Period period) {
        this.amount = amount;
        this.startDate = new Date();
        this.period = period;
    }

    public Price getPrice() { return this.amount; }
    public Period getPeriod() { return this.period; }
    public Date getStartDate() { return this.startDate; }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("$")
                .append(getPrice())
                .append(" for every ")
                .append(getPeriod())
                .append(" weeks");
        return builder.toString();
    }
}
