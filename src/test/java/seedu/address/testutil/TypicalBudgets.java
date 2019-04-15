package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_SECONDEXTRA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EPiggy;
import seedu.address.model.epiggy.Budget;
import seedu.address.testutil.epiggy.BudgetBuilder;

/**
 * A utility class containing a list of {@code Budget} objects to be used in tests.
 */
public class TypicalBudgets {

    // Date is in the form dd/MM/yyyy
    public static final Budget ONE = new BudgetBuilder().withAmount("100")
            .withPeriod("7").withDate("04/02/2019").build();
    public static final Budget TWO = new BudgetBuilder().withAmount("300")
            .withPeriod("18").withDate("11/02/2019").build();
    public static final Budget THREE = new BudgetBuilder().withAmount("500")
            .withDate("01/03/2019").withPeriod("30").build();
    public static final Budget FOUR = new BudgetBuilder().withAmount("15")
            .withDate("31/03/2019").withPeriod("1").build();
    public static final Budget FIVE = new BudgetBuilder().withAmount("20")
            .withDate("01/04/2019").withPeriod("2").build();
    public static final Budget SIX = new BudgetBuilder().withAmount("90")
            .withDate("15/04/2019").withPeriod("5").build();
    public static final Budget SEVEN = new BudgetBuilder().withAmount("12000")
            .withDate("20/04/2019").withPeriod("365").build();
    public static final Budget EIGHT = new BudgetBuilder().withAmount("90")
            .withDate("01/02/2019").withPeriod("3").build();
    public static final Budget NINE = new BudgetBuilder().withAmount("12")
            .withDate("31/01/2019").withPeriod("1").build();

    // Other budgets
    public static final Budget TEN = new BudgetBuilder().withAmount("10")
            .withPeriod("1").withDate("04/02/2020").build();
    public static final Budget ELEVEN = new BudgetBuilder().withAmount("30")
            .withPeriod("1").withDate("05/02/2020").build();
    public static final Budget TWELVE = new BudgetBuilder().withAmount("50")
            .withDate("06/02/2020").withPeriod("1").build();
    public static final Budget THIRTEEN = new BudgetBuilder().withAmount("15")
            .withDate("07/02/2020").withPeriod("1").build();
    public static final Budget FOURTEEN = new BudgetBuilder().withAmount("20")
            .withDate("08/02/2020").withPeriod("1").build();
    public static final Budget FIFTEEN = new BudgetBuilder().withAmount("9")
            .withDate("09/02/2020").withPeriod("1").build();
    public static final Budget SIXTEEN = new BudgetBuilder().withAmount("15")
            .withDate("10/02/2020").withPeriod("1").build();
    public static final Budget SEVENTEEN = new BudgetBuilder().withAmount("90")
            .withDate("11/02/2020").withPeriod("1").build();
    public static final Budget EIGHTEEN = new BudgetBuilder().withAmount("12")
            .withDate("12/02/2020").withPeriod("1").build();
    public static final Budget NINETEEN = new BudgetBuilder().withAmount("12")
            .withDate("13/02/2020").withPeriod("1").build();
    public static final Budget TWENTY = new BudgetBuilder().withAmount("12")
            .withDate("14/02/2020").withPeriod("1").build();

    // Manually added - Budget's details found in {@code CommandTestUtil}
    public static final Budget FIRST_EXTRA = new BudgetBuilder().withAmount(VALID_AMOUNT_FIRSTEXTRA)
            .withPeriod(VALID_PERIOD_FIRSTEXTRA).withDate(VALID_DATE_FIRSTEXTRA).build();
    public static final Budget SECOND_EXTRA = new BudgetBuilder().withAmount(VALID_AMOUNT_SECONDEXTRA)
            .withPeriod(VALID_PERIOD_SECONDEXTRA).withDate(VALID_DATE_SECONDEXTRA).build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalBudgets() {} // prevents instantiation

    /**
     * Returns an {@code EPiggy} with all the typical persons.
     */
    public static EPiggy getTypicalEPiggy() {
        EPiggy ab = new EPiggy();
        int i = 0;
        for (Budget budget : getTypicalBudgets()) {
            ab.addBudget(i, budget);
            i++;
        }
        return ab;
    }

    public static List<Budget> getTypicalBudgets() {
        return new ArrayList<>(Arrays.asList(ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE));
    }

    public static List<Budget> getMaximumNumberOfBudgets() {
        return new ArrayList<>(Arrays.asList(ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN,
                TWELVE, THIRTEEN, FOURTEEN, FIFTEEN, SIXTEEN, SEVENTEEN, EIGHTEEN, NINETEEN, TWENTY));
    }
}
