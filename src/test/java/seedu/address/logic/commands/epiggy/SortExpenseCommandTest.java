package seedu.address.logic.commands.epiggy;

import static seedu.address.testutil.epiggy.TypicalExpenses.getTypicalEpiggy;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SortExpenseCommand}.
 */
public class SortExpenseCommandTest {
    private Model model = new ModelManager(getTypicalEpiggy(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEpiggy(), new UserPrefs());

    
}
