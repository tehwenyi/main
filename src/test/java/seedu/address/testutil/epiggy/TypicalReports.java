package seedu.address.testutil.epiggy;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2018;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2019;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIENDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EPiggy;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * A utility class containing a list of {@code budget}, {@code expense},
 * {@code allowance} objects to be used in tests.
 */
public class TypicalReports {

    // valid test budgets
    // Date is in the form MM/dd/yyyy
    public static final Budget VALID_BUDGET_ONE = new BudgetBuilder().withAmount("100")
            .withPeriod("7").withDate("02/04/2019").build();
    public static final Budget VALID_BUDGET_TWO = new BudgetBuilder().withAmount("300")
            .withPeriod("18").withDate("02/11/2019").build();
    public static final Budget VALID_BUDGET_THREE = new BudgetBuilder().withAmount("500")
            .withDate("03/01/2019").withPeriod("30").build();

    // invalid budgets
    public static final Budget INVALID_BUDGET_ONE = new BudgetBuilder().withAmount("10")
            .withPeriod("1").withDate("02/04/2020").build();
    public static final Budget INVALID_BUDGET_TWO = new BudgetBuilder().withAmount("30")
            .withPeriod("1").withDate("02/05/2020").build();

    // test expenses
    public static final Expense DUMPLING_SOUP = new ExpensesBuilder().withName("Dumpling Soup")
            .withCost("5.00")
            .withDate(VALID_DATE_2019)
            .withTags(VALID_TAG_FOOD, "lunch").build();
    public static final Expense STATIONARY = new ExpensesBuilder().withName("Stationary")
            .withCost("3.00")
            .withDate("03/06/2019")
            .withTags("school", "misc").build();
    public static final Expense MOVIE_AVENGERS = new ExpensesBuilder().withName("Avengers : Endgame movie")
            .withDate("04/26/2019")
            .withCost("8.50")
            .withTags("movie", "entertainment", VALID_TAG_FRIENDS).build();
    public static final Expense KARAOKE = new ExpensesBuilder().withName("Karaoke: KTV")
            .withCost("10.00")
            .withDate(VALID_DATE_2018)
            .withTags(VALID_TAG_FRIENDS).build();
    public static final Expense CLOTHES = new ExpensesBuilder().withName("Clothes shopping")
            .withCost("21.80")
            .withTags("shopping").build();
    public static final Expense KFC = new ExpensesBuilder().withName("KFC")
            .withDate("04/26/2019")
            .withCost("13.00")
            .withTags(VALID_TAG_FOOD, "dinner").build();

    private TypicalReports() {} // prevents instantiation

    /**
     * Returns an {@code EPiggy} with all the typical persons.
     */
    public static EPiggy getTypicalEPiggy() {
        // todo: change the method here
        EPiggy ePiggy = new EPiggy();

        //add budgets into ePiggy
        int i = 0;
        for (Budget budget : getTypicalBudgets()) {
            ePiggy.addBudget(i, budget);
            i++;
        }

        //add expenses into ePiggy
        for (Expense expense : getTypicalExpenses()) {
            ePiggy.addExpense(expense);
        }

        return ePiggy;
    }

    public static List<Budget> getTypicalBudgets() {
        return new ArrayList<>(Arrays.asList(VALID_BUDGET_ONE, VALID_BUDGET_TWO,
                VALID_BUDGET_THREE, INVALID_BUDGET_ONE, INVALID_BUDGET_TWO));
    }

    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(DUMPLING_SOUP, STATIONARY, MOVIE_AVENGERS, KARAOKE, CLOTHES, KFC));
    }
}

