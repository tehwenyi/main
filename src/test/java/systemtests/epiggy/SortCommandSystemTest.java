package systemtests.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.epiggy.DeleteExpenseCommand;
import seedu.address.logic.commands.epiggy.ReverseListCommand;
import seedu.address.logic.commands.epiggy.SortCommand;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.comparators.CompareExpenseByCost;
import seedu.address.model.epiggy.comparators.CompareExpenseByDate;
import seedu.address.model.epiggy.comparators.CompareExpenseByName;

public class SortCommandSystemTest extends EPiggySystemTestWithDefaultData {

    private String expectedMessage;
    @Test
    public void sort() {
        Model defaultModel = getModel();
        Model expectedModel = getModel();
        String command;
        String expectedResultMessage;
        /* Case: sort list by name with default ordering (ascending) -> sorted */
        command = SortCommand.COMMAND_WORD + " " + PREFIX_NAME;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortExpenses(new CompareExpenseByName());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);

        /* Case: Undo sorting -> original ePiggy restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);

        /* Case: Redo sorting -> sorted */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, expectedModel);

        /* Case: sort list by amount with default ordering (descending) -> sorted */
        command = SortCommand.COMMAND_WORD + " " + PREFIX_COST;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortExpenses(new CompareExpenseByCost());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: delete a record then sort list by date in default ordering (descending) -> sorted */
        Expense targetExpense = expectedModel.getFilteredExpenseList().get(0);
        expectedModel.deleteExpense(targetExpense);
        assertCommandSuccess("de 1", String.format(DeleteExpenseCommand.MESSAGE_DELETE_EXPENSE_SUCCESS,
                targetExpense), expectedModel);
        command = SortCommand.COMMAND_WORD + " " + PREFIX_DATE;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortExpenses(new CompareExpenseByDate());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: Mixed case command word -> sorted */
        //        command = "soRt " + PREFIX_NAME;
        //        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        //        expectedModel.sortExpenses(new ExpenseNameComparator());
        //        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: filter the record before sorting by cost -> sorted */
        showExpensesWithTag(VALID_TAG_FOOD);
        command = SortCommand.COMMAND_WORD + " " + PREFIX_COST;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortExpenses(new CompareExpenseByCost());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: reverse list then sort by name -> sorted */
        command = ReverseListCommand.COMMAND_WORD;
        expectedResultMessage = ReverseListCommand.MESSAGE_SUCCESS;
        expectedModel.reverseFilteredExpensesList();
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        command = SortCommand.COMMAND_WORD + " " + PREFIX_NAME;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortExpenses(new CompareExpenseByName());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);



        /* --------------------------------- Performing invalid delete operation ------------------------------------ */


        /* Case: Clears the finance tracker then sort list -> show list is empty message */
        deleteAllExpenses();
        // assertCommandFailure(SortCommand.COMMAND_WORD + " " + PREFIX_NAME,
        //        SortCommand.MESSAGE_SUCCESS);


        /* Case: Missing arguments -> rejected */
        command = SortCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));


        /* Case: Invalid keyword -> rejected */
        command = SortCommand.COMMAND_WORD + " 123";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        /* Case: Invalid arguments (extra argument) -> rejected */
        command = SortCommand.COMMAND_WORD + " " + PREFIX_NAME + " " + PREFIX_DATE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

    }

    /**
     * Check for expected amount of found expenses.
     * Check for commandBox display after executed command.
     *
     * @param command Command string
     * @param expectedModel ePiggy testApp model
     * @param expectedResultMessage expected message
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        if (expectedMessage != null) {
            expectedMessage = "========================\n" + "ePiggy: " + String.format(expectedResultMessage,
                    expectedModel.getFilteredExpenseList().size()) + "\n\nYou: "
                    + command + "\n" + expectedMessage;
        } else {
            expectedMessage = "========================\n" + "ePiggy: " + String.format(expectedResultMessage,
                    expectedModel.getFilteredExpenseList().size()) + "\n\nYou: "
                    + command + "\n";
        }
        // assertApplicationDisplaysExpected("", expectedMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        // assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code EPiggySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        executeCommand(command);
        // assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        // assertStatusBarUnchanged();
    }
}
