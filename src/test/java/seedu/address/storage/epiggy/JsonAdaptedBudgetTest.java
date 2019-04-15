package seedu.address.storage.epiggy;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.storage.epiggy.JsonAdaptedBudget.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Date;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Period;
import seedu.address.testutil.epiggy.BudgetBuilder;

//@@author kev-inc

public class JsonAdaptedBudgetTest {

    private static final String INVALID_AMOUNT = "-42";
    private static final String INVALID_DATE = "31/20/2019";
    private static final String INVALID_PERIOD = "-5";
    private static final String INVALID_REMAINING = "3.14158";

    private static final String VALID_AMOUNT = "42.00";
    private static final String VALID_DATE = "04/02/2019";
    private static final String VALID_PERIOD = "3";
    private static final String VALID_REMAINING = "24.00";

    private static final Budget VALID_BUDGET = new BudgetBuilder().withAmount(VALID_AMOUNT)
            .withDate(VALID_DATE).withPeriod(VALID_PERIOD).build();




    @Test
    public void toModelType_validBudget_returnsBudget() throws IllegalValueException {
        VALID_BUDGET.setRemainingAmount(new Cost(VALID_REMAINING));
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_BUDGET);
        assertEquals(VALID_BUDGET, budget.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(INVALID_AMOUNT, VALID_DATE, VALID_PERIOD, VALID_REMAINING);
        String expectedMessage = Cost.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_AMOUNT, INVALID_DATE, VALID_PERIOD, VALID_REMAINING);
        String expectedMessage = MESSAGE_INVALID_DATE;
        assertThrows(ParseException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidPeriod_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_AMOUNT, VALID_DATE, INVALID_PERIOD, VALID_REMAINING);
        String expectedMessage = Period.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidAmountRemaining_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_AMOUNT, VALID_DATE, VALID_PERIOD, INVALID_REMAINING);
        String expectedMessage = Cost.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(null, VALID_DATE, VALID_PERIOD, VALID_REMAINING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_AMOUNT, null, VALID_PERIOD, VALID_REMAINING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullPeriod_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_AMOUNT, VALID_DATE, null, VALID_REMAINING);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Period.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullAmountRemaining_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_AMOUNT, VALID_DATE, VALID_PERIOD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

}
