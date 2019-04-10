package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.epiggy.TypicalExpenses.getTypicalEPiggy;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

@Ignore
public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalEPiggy(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        CommandTestUtil.deleteFirstPerson(model);
        CommandTestUtil.deleteFirstPerson(model);
        model.undoEPiggy();
        model.undoEPiggy();

        CommandTestUtil.deleteFirstPerson(expectedModel);
        CommandTestUtil.deleteFirstPerson(expectedModel);
        expectedModel.undoEPiggy();
        expectedModel.undoEPiggy();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoEPiggy();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoEPiggy();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
