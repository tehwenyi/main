package seedu.address.logic.commands.epiggy;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalPersons.getTypicalEPiggy;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ReverseListCommand
 */
public class ReverseListCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        expectedModel = new ModelManager(model.getEPiggy(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ReverseListCommand(), model, commandHistory,
                ReverseListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    @Ignore
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ReverseListCommand(), model, commandHistory,
                ReverseListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
