package seedu.address.model.epiggy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_TWO;
import static seedu.address.testutil.TypicalBudgets.ONE;
import static seedu.address.testutil.TypicalBudgets.TWO;

import java.util.Calendar;
import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Period;
import seedu.address.testutil.Assert;
import seedu.address.testutil.epiggy.BudgetBuilder;


public class BudgetTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        Cost nullCost = null;
        Period nullPeriod = null;
        Date nullDate = null;
        Assert.assertThrows(NullPointerException.class, () -> new Budget(nullCost, nullPeriod, nullDate));
    }

    @Test
    public void constructor_createCurrentBudget_success() {
        Date todaysDate = new Date();
        Budget validBudget = new BudgetBuilder().withDate(todaysDate).build();
        Calendar c = Calendar.getInstance();
        c.setTime(todaysDate);
        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(BudgetBuilder.DEFAULT_PERIOD));
        Date expectedEndDate = c.getTime();
        assertEquals(validBudget.getCost().getAmount(), Double.parseDouble(BudgetBuilder.DEFAULT_AMOUNT));
        assertEquals(validBudget.getPeriod().getTimePeriod(), Integer.parseInt(BudgetBuilder.DEFAULT_PERIOD));
        assertEquals(validBudget.getStartDate(), todaysDate);
        assertEquals(validBudget.getEndDate(), expectedEndDate);
        assertEquals(validBudget.getRemainingAmount().getAmount(), Double.parseDouble(BudgetBuilder.DEFAULT_AMOUNT));
        assertEquals(validBudget.getRemainingDays().getTimePeriod(), Integer.parseInt(BudgetBuilder.DEFAULT_PERIOD));
        assertEquals(validBudget.getStatus(), Budget.CURRENT_BUDGET);
    }

    @Test
    public void setRemainingDays_success() {
        Budget validBudget = new BudgetBuilder().build();
        validBudget.setRemainingDays(new Period(2));
        assertEquals(validBudget.getRemainingDays().getTimePeriod(), 2);
    }

    @Test
    public void resetRemainingAmount_success() {
        Budget validBudget = new BudgetBuilder().build();
        validBudget.resetRemainingAmount();
        assertEquals(validBudget.getRemainingAmount().getAmount(), Integer.parseInt(BudgetBuilder.DEFAULT_AMOUNT));
    }

    @Test
    public void deductRemainingAmount() {
        Budget budget = new BudgetBuilder().build();
        // deduct positive cost
        Cost positiveCost = new Cost(10);
        budget.deductRemainingAmount(positiveCost);
        Cost resultingCost = new Cost(BudgetBuilder.DEFAULT_AMOUNT).deduct(positiveCost);
        assertEquals(budget.getRemainingAmount().getAmount(), resultingCost.getAmount());

        // deduct negative cost
        Cost negativeCost = new Cost(-10);
        budget = new BudgetBuilder().build();
        resultingCost = new Cost(BudgetBuilder.DEFAULT_AMOUNT).deduct(negativeCost);
        budget.deductRemainingAmount(negativeCost);
        assertEquals(budget.getRemainingAmount().getAmount(), resultingCost.getAmount());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Budget oneCopy = new BudgetBuilder(ONE).build();
        assertTrue(ONE.equals(oneCopy));

        // same object -> returns true
        assertTrue(ONE.equals(ONE));

        // null -> returns false
        assertFalse(ONE.equals(null));

        // different type -> returns false
        assertFalse(ONE.equals(5));

        // different budget -> returns false
        assertFalse(ONE.equals(TWO));

        // different name -> returns false
        Budget editedOne = new BudgetBuilder(ONE).withAmount(VALID_AMOUNT_TWO).build();
        assertFalse(ONE.equals(editedOne));

        // different phone -> returns false
        editedOne = new BudgetBuilder(ONE).withPeriod(VALID_PERIOD_TWO).build();
        assertFalse(ONE.equals(editedOne));

        // different email -> returns false
        editedOne = new BudgetBuilder(ONE).withDate(VALID_DATE_TWO).build();
        assertFalse(ONE.equals(editedOne));
    }

    @Test
    public void toStringTest() {
        // same values -> returns true
        Budget oneCopy = new BudgetBuilder(ONE).build();
        String oneString = "$100 for 7 days starting from 04/02/2019 till 11/02/2019. "
                + "0 days remaining and $100 remaining.";

        // same budget
        assertEquals(ONE.toString(), oneCopy.toString());

        // same object
        assertEquals(ONE.toString(), ONE.toString());

        // different budget
        assertNotEquals(ONE.toString(), TWO.toString());

        // different name
        Budget editedOne = new BudgetBuilder(ONE).withAmount(VALID_AMOUNT_TWO).build();
        assertNotEquals(ONE.toString(), editedOne.toString());

        // different phone -> returns false
        editedOne = new BudgetBuilder(ONE).withPeriod(VALID_PERIOD_TWO).build();
        assertNotEquals(ONE.toString(), editedOne.toString());

        // different email -> returns false
        editedOne = new BudgetBuilder(ONE).withDate(VALID_DATE_TWO).build();
        assertNotEquals(ONE.toString(), editedOne.toString());
    }
}
