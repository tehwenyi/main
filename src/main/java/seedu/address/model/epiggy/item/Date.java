package seedu.address.model.epiggy.item;

import java.util.Calendar;

/**
 * Represents a Date in ePiggy.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should be in the format dd/mm/yyyy, dd.mm.yyyy or dd-mm-yyyy and year should be no earlier than 1600";
    public static final String VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    private final int day;
    private final int month;
    private final int year;

    public Date(String date) {
        this.day = Integer.valueOf(date.substring(0, 2));
        this.month = Integer.valueOf(date.substring(3, 5));
        this.year = Integer.valueOf(date.substring(6, 10));
    }

    /**
     * Getter for day
     * @return day
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Getter for month
     * @return month
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Getter for year
     * @return year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Check if given dd mm yy format is valid
     * @return true if it is valid date format
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Check if given year is a leap year
     * @param year
     * @return true if year is leap year
     */
    public boolean isLeap(int year) {
        // uses system library to check
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDay())
                .append("/")
                .append(getMonth())
                .append("/")
                .append(getYear());
        return builder.toString();
    }
}
