package seedu.address.storage.epiggy;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.epiggy.JsonAdaptedGoal.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.epiggy.TypicalGoal.IPHONE;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Name;

public class JsonAdaptedGoalTest {
    private static final String INVALID_NAME = "@pple";
    private static final String INVALID_COST = "10.12345";

    private static final String VALID_NAME = IPHONE.getName().toString();
    private static final String VALID_COST = String.valueOf(IPHONE.getAmount().getAmount());

    @Test
    public void toModelType_validGoal_returnsGoal() throws Exception {
        JsonAdaptedGoal goal = new JsonAdaptedGoal(IPHONE);
        assertEquals(IPHONE, goal.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedGoal goal = new JsonAdaptedGoal(false, INVALID_NAME, VALID_COST);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedGoal goal = new JsonAdaptedGoal(false, null, VALID_COST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_invalidCost_throwsIllegalValueException() {
        JsonAdaptedGoal goal = new JsonAdaptedGoal(false, VALID_NAME, INVALID_COST);
        String expectedMessage = Cost.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullCost_throwsIllegalValueException() {
        JsonAdaptedGoal goal = new JsonAdaptedGoal(false, VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, goal::toModelType);
    }

    @Test
    public void toModelType_nullGoal_createsNull() throws Exception {
        JsonAdaptedGoal goal = new JsonAdaptedGoal(true, VALID_NAME, VALID_COST);
        assertEquals(null, goal.toModelType());
    }
}
