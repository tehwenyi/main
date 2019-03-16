package seedu.address.model.epiggy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private final Date endDate;
    private final Period period;

    /**
     * Represents a Budget in the expense book.
     * Guarantees: details are present and not null, field values are validated, immutable.
     */
    public Budget(Price amount, Period period, Date startDate) {
        this.amount = amount;
        this.startDate = startDate;
        this.period = period;
        this.endDate = calculateEndDate(startDate, period);
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

    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Calculates the end date = startDate + period (number of days)
     * @param startDate
     * @param period
     * @return endDate
     */
    private Date calculateEndDate(Date startDate, Period period) {
        // int noOfDays = period.getTimePeriod();
        // LocalDate endDate = (LocalDate)startDate.plus(period, ChronoUnit.WEEKS);
        Calendar calendar = Calendar.getInstance();
        String parseDate = startDate.toString();
        java.util.Date start;

        try {
            start = new SimpleDateFormat("dd/MM/yyyy").parse(parseDate);
            calendar.setTime(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.add(Calendar.DAY_OF_YEAR, period.getTimePeriod());
        return new Date(calendar.getTime().toString());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("$")
                .append(getPrice())
                .append(" for every ")
                .append(getPeriod())
                .append(" weeks starting from ")
                .append(getStartDate())
                .append(" till ")
                .append(getEndDate());
        return builder.toString();
    }
}
