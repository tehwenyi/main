package seedu.address.model.epiggy;

import seedu.address.model.epiggy.item.Date;
import seedu.address.model.epiggy.item.Period;
import seedu.address.model.epiggy.item.Price;

/**
 * Represents a Budget in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {
    private final Price amount;
    private final Date startDate;
    //    private final Date endDate;
    private final Period period;

    /**
     * Represents a Budget in the expense book.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Budget(Price amount, Period period, Date startDate) {
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        //        this.endDate = calculateEndDate(startDate, period);
    }

    public Price getPrice() {
        return this.amount;
    }
    public Period getPeriod() {
        return this.period;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("$")
                .append(getPrice())
                .append(" for every ")
                .append(getPeriod())
                .append(" weeks starting from ")
                .append(getStartDate());
        return builder.toString();
    }
}
