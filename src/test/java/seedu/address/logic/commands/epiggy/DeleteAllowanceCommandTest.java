package seedu.address.logic.commands.epiggy;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.TypicalIndexes;
import seedu.address.testutil.epiggy.TypicalAllowances;
import seedu.address.testutil.epiggy.TypicalExpenses;

public class DeleteAllowanceCommandTest {
    private Model model = new ModelManager(TypicalAllowances.getTypicalEPiggy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexIsAllowance_deleteSuccess() {
        Allowance allowanceToDelete = (Allowance) model.getFilteredExpenseList()
                .get(TypicalIndexes.INDEX_FIRST_ALLOWANCE.getZeroBased());
        DeleteAllowanceCommand deleteAllowanceCommand = new DeleteAllowanceCommand(TypicalIndexes.INDEX_FIRST_ALLOWANCE);

        String expectedMessage = String.format(DeleteAllowanceCommand.MESSAGE_DELETE_ALLOWANCE_SUCCESS,
                allowanceToDelete);

        ModelManager expectedModel = new ModelManager(model.getEPiggy(), new UserPrefs());
        expectedModel.deleteExpense(allowanceToDelete);
        expectedModel.commitEPiggy();

        assertCommandSuccess(deleteAllowanceCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexIsExpense_throwsCommandException() {
        Model tempModel = new ModelManager(TypicalExpenses.getTypicalEPiggy(),
                new UserPrefs());
        Expense expenseToDelete = (Expense) tempModel.getFilteredExpenseList()
                .get(TypicalIndexes.INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteAllowanceCommand deleteAllowanceCommand = new DeleteAllowanceCommand(TypicalIndexes.INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(DeleteAllowanceCommand.MESSAGE_ITEM_NOT_ALLOWANCE,
                expenseToDelete);

        ModelManager expectedModel = new ModelManager(tempModel.getEPiggy(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitEPiggy();

        CommandTestUtil.assertCommandFailure(deleteAllowanceCommand, tempModel, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        CommandTestUtil.showExpenseAtIndex(model, TypicalIndexes.INDEX_FIRST_EXPENSE);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_EXPENSE;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEPiggy().getExpenseList().size());
        DeleteAllowanceCommand deleteAllowanceCommand = new DeleteAllowanceCommand(outOfBoundIndex);
        CommandTestUtil.assertCommandFailure(deleteAllowanceCommand, model, commandHistory,
                DeleteExpenseCommand.MESSAGE_INDEX_OUT_OF_BOUNDS);
    }
}
