package seedu.address.testutil.epiggy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Period;

/**
 * A utility class to help with building Budget objects.
 */
public class BudgetBuilder {

    public static final String DEFAULT_AMOUNT = "500";
    public static final String DEFAULT_PERIOD = "25";
    // The date is in the format dd/MM/yyyy, hence 3rd February 2019 below
    public static final String DEFAULT_STARTDATE = "03/02/2019";

    private Cost amount;
    private Period days;
    private Date startDate;

    public BudgetBuilder() {
        amount = new Cost(DEFAULT_AMOUNT);
        days = new Period(DEFAULT_PERIOD);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            startDate = dateFormat.parse(DEFAULT_STARTDATE);
        } catch (ParseException e) {
            System.out.println("Date should be in the format dd/mm/yyyy.");
        }
    }

    /**
     * Initializes the BudgetBuilder with the data of {@code budgetToCopy}.
     */
    public BudgetBuilder(Budget budgetToCopy) {
        amount = budgetToCopy.getBudgetedAmount();
        days = budgetToCopy.getPeriod();
        startDate = budgetToCopy.getStartDate();
    }

    /**
     * Sets the {@code Cost} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withAmount(String amount) {
        this.amount = new Cost(amount);
        return this;
    }

    /**
     * Sets the {@code Period} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withPeriod(String days) {
        this.days = new Period(days);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withDate(String startDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.startDate = dateFormat.parse(startDate);
        } catch (ParseException e) {
            System.out.println("Date should be in the format dd/mm/yyyy.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Budget} that we are building.
     */
    public BudgetBuilder withDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Budget build() {
        return new Budget(amount, days, startDate);
    }

}
