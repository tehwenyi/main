package seedu.address.model.epiggy.item;

import java.util.Calendar;

/**
 * Represents a Date in ePiggy.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Date {

    private final int day;
    private final int month;
    private final int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Check if given year/month/day is valid
     * @param day
     * @param month
     * @param year
     * @return true if it is valid date
     */
    public static boolean isValidDate(int day, int month, int year) {
        if (year < 0) {
            return false;
        }
        if ((month < 1) || (month > 12)) {
            return false;
        }
        if ((day < 1) || (day > 31)) {
            return false;
        }
        switch (month) {
            case 1: return true;
            case 2: return (isLeap(year) ? day <= 29 : day <= 28);
            case 3: return true;
            case 4: return day < 31;
            case 5: return true;
            case 6: return day < 31;
            case 7: return true;
            case 8: return true;
            case 9: return day < 31;
            case 10: return true;
            case 11: return day < 31;
            default: return true;
        }
    }

    /**
     * Check if given year is a leap year
     * @param year
     * @return true if year is leap year
     */
    public static boolean isLeap(int year) {
        // uses system library to check
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }
}
