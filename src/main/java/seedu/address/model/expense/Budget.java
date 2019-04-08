package seedu.address.model.expense;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import seedu.address.model.expense.item.Cost;
import seedu.address.model.expense.item.Period;

/**
 * Represents a Budget in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Budget {
    public static final String MESSAGE_CONSTRAINTS =
            "Budgeted amount should be greater than $0.00.";
    public static final String CURRENT_BUDGET = "Current";
    private static final String OLD_BUDGET = "Old";
    private static final String FUTURE_BUDGET = "Future";

    private final Cost amount;
    private final Date startDate;
    private final Date endDate;
    private final Period period;

    private Cost remainingAmount;
    private Period remainingDays;
    private String status = null;
    private Date todaysDate;

    /**
     * Represents a Budget in the expense book.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Budget(Cost amount, Period period, Date startDate) {
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.endDate = calculateEndDate(startDate, period);
        this.remainingAmount = amount;
        this.remainingDays = period;
        this.todaysDate = new Date();
        if (!todaysDate.before(startDate) && !todaysDate.after(endDate)) {
            this.status = CURRENT_BUDGET;
        } else if (todaysDate.before(startDate)) {
            this.status = FUTURE_BUDGET;
        } else {
            this.status = OLD_BUDGET;
        }
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

    public void setRemainingDays(Period remainingDays) {
        this.remainingDays = remainingDays;
    }

    public void setRemainingAmount(Cost remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public void resetRemainingAmount() {
        this.remainingAmount = this.amount;
    }

    public void deductRemainingAmount(Cost amountToDeduct) {
        this.remainingAmount = this.remainingAmount.deduct(amountToDeduct);
    }

    public String getStatus() {
        return status;
    }

    public Cost getRemainingAmount() {
        return remainingAmount;
    }

    public Cost getPositiveRemainingAmount() {
        if (remainingAmount.getAmount() < 0) {
            return new Cost(-remainingAmount.getAmount());
        }
        return remainingAmount;
    }

    public Period getRemainingDays() {
        return remainingDays; }

    public Cost getBudgetedAmount() {
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

    public int getDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.startDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.startDate);
        return cal.get(Calendar.MONTH);
    }

    public int getYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.startDate);
        return cal.get(Calendar.YEAR);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Budget)) {
            return false;
        }

        Budget b = (Budget) o;
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.startDate);
        return this.amount.equals(b.getBudgetedAmount())
                && cal.get(Calendar.YEAR) == b.getYear()
                && cal.get(Calendar.MONTH) == b.getMonth()
                && cal.get(Calendar.DAY_OF_MONTH) == b.getDay()
                && this.period.equals(b.getPeriod());
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        final StringBuilder builder = new StringBuilder();
        builder.append("$")
                .append(getBudgetedAmount())
                .append(" for ")
                .append(getPeriod())
                .append(" days starting from ")
                .append(sdf.format(getStartDate()))
                .append(" till ")
                .append(sdf.format(getEndDate()))
                .append(".\n")
                .append(getRemainingDays())
                .append(" days remaining and $")
                .append(getRemainingAmount())
                .append(" remaining.");
        return builder.toString();
    }
}
