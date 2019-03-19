package seedu.address.model.epiggy;

import java.util.Calendar;
import java.util.Date;

import seedu.address.model.epiggy.item.Period;
import seedu.address.model.epiggy.item.Price;

/**
 * Represents a Budget in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {
    public static final String MESSAGE_CONSTRAINTS =
            "Budgeted amount should be more than $0.";
    private final Price amount;
    private final Date startDate;
    private final Date endDate;
    private final Period period;
    private Price remainingAmount;
    private Period remainingDays;

    /**
     * Represents a Budget in the expense book.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Budget(Price amount, Period period, Date startDate) {
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.endDate = calculateEndDate(startDate, period);
        this.remainingAmount = amount;
        this.remainingDays = period;
    }

    /**
     * Represents a Budget in the expense book.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Budget() {
        this.amount = new Price(0);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        this.startDate = cal.getTime();
        this.period = new Period(0);
        this.endDate = this.startDate;
        this.remainingAmount = amount;
        this.remainingDays = period;
    }

    /**
     * Calculates the end date = startDate + period (number of days)
     * @param startDate
     * @param period
     * @return endDate
     */
    private Date calculateEndDate(Date startDate, Period period) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.startDate);
        cal.add(Calendar.DATE, period.getTimePeriod());
        return cal.getTime();
    }

    private void setRemainingAmount(Price remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public void setRemainingDays(Period remainingDays) {
        this.remainingDays = remainingDays;
    }

    public void deductRemainingAmount(Price amountToDeduct) {
        this.remainingAmount = this.remainingAmount.deduct(amountToDeduct);
    }

    public Price getRemainingAmount() {
        return remainingAmount;
    }

    public Period getRemainingDays() {
        return remainingDays; }

    public Price getPrice() {
        return this.amount;
    }

    public Period getPeriod() {
        return this.period;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("$")
                .append(getPrice())
                .append(" for ")
                .append(getPeriod())
                .append(" days starting from ")
                .append(getStartDate())
                .append(" till ")
                .append(getEndDate());
        return builder.toString();
    }
}
