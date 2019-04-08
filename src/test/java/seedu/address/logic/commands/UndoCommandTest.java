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
public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalEPiggy(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        CommandTestUtil.deleteFirstPerson(model);
        CommandTestUtil.deleteFirstPerson(model);

        CommandTestUtil.deleteFirstPerson(expectedModel);
        CommandTestUtil.deleteFirstPerson(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoEPiggy();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoEPiggy();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        CommandTestUtil.assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
