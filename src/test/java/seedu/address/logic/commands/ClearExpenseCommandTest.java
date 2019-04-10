package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalEPiggy;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.EPiggy;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearExpenseCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyEPiggy_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitEPiggy();

        assertCommandSuccess(new ClearExpenseCommand(), model, commandHistory, ClearExpenseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEPiggy_success() {
        Model model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        expectedModel.setEPiggy(new EPiggy());
        expectedModel.commitEPiggy();

        assertCommandSuccess(new ClearExpenseCommand(), model, commandHistory, ClearExpenseCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
