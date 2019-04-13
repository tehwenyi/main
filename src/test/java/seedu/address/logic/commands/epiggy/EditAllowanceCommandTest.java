package seedu.address.logic.commands.epiggy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALLOWANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.address.logic.commands.epiggy.EditAllowanceCommand.createEditedAllowance;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ALLOWANCE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ALLOWANCE;
import static seedu.address.testutil.epiggy.TypicalAllowances.getTypicalEPiggy;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Expense;
import seedu.address.testutil.epiggy.EditAllowanceDescriptorBuilder;
import seedu.address.testutil.epiggy.TypicalExpenses;

class EditAllowanceCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalEPiggy(), new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        EditAllowanceCommand.EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withName(VALID_NAME_SECONDEXTRA).withCost(VALID_AMOUNT_SECONDEXTRA)
                .withDate(VALID_DATE_SECONDEXTRA).withTags(VALID_TAG_ALLOWANCE, VALID_TAG_SECONDEXTRA).build();
        Allowance allowanceToEdit = (Allowance) model.getFilteredExpenseList()
                .get(INDEX_FIRST_ALLOWANCE.getZeroBased());
        Allowance editedAllowance = createEditedAllowance(allowanceToEdit, descriptor);
        CommandResult commandResult = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor)
                .execute(model, commandHistory);

        assertEquals(String.format(EditAllowanceCommand.MESSAGE_EDIT_ALLOWANCE_SUCCESS, editedAllowance),
                commandResult.getFeedbackToUser());
        assertEquals(editedAllowance, model.getFilteredExpenseList().get(INDEX_FIRST_ALLOWANCE.getZeroBased()));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_nameFieldSpecified_success() throws CommandException {
        EditAllowanceCommand.EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withName(VALID_NAME_SECONDEXTRA).build();
        Allowance allowanceToEdit = (Allowance) model.getFilteredExpenseList()
                .get(INDEX_FIRST_ALLOWANCE.getZeroBased());
        Allowance editedAllowance = createEditedAllowance(allowanceToEdit, descriptor);
        CommandResult commandResult = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor)
                .execute(model, commandHistory);

        assertEquals(String.format(EditAllowanceCommand.MESSAGE_EDIT_ALLOWANCE_SUCCESS, editedAllowance),
                commandResult.getFeedbackToUser());
        assertEquals(editedAllowance, model.getFilteredExpenseList().get(INDEX_FIRST_ALLOWANCE.getZeroBased()));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_amountFieldSpecified_success() throws CommandException {
        EditAllowanceCommand.EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withCost(VALID_AMOUNT_SECONDEXTRA).build();
        Allowance allowanceToEdit = (Allowance) model.getFilteredExpenseList()
                .get(INDEX_FIRST_ALLOWANCE.getZeroBased());
        Allowance editedAllowance = createEditedAllowance(allowanceToEdit, descriptor);
        CommandResult commandResult = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor)
                .execute(model, commandHistory);

        assertEquals(String.format(EditAllowanceCommand.MESSAGE_EDIT_ALLOWANCE_SUCCESS, editedAllowance),
                commandResult.getFeedbackToUser());
        assertEquals(editedAllowance, model.getFilteredExpenseList().get(INDEX_FIRST_ALLOWANCE.getZeroBased()));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_dateFieldSpecified_success() throws CommandException {
        EditAllowanceCommand.EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withDate(VALID_DATE_SECONDEXTRA).build();
        Allowance allowanceToEdit = (Allowance) model.getFilteredExpenseList()
                .get(INDEX_FIRST_ALLOWANCE.getZeroBased());
        Allowance editedAllowance = createEditedAllowance(allowanceToEdit, descriptor);
        CommandResult commandResult = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor)
                .execute(model, commandHistory);

        assertEquals(String.format(EditAllowanceCommand.MESSAGE_EDIT_ALLOWANCE_SUCCESS, editedAllowance),
                commandResult.getFeedbackToUser());
        assertEquals(editedAllowance, model.getFilteredExpenseList().get(INDEX_FIRST_ALLOWANCE.getZeroBased()));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_tagFieldSpecified_success() throws CommandException {
        EditAllowanceCommand.EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withTags(VALID_TAG_ALLOWANCE, VALID_TAG_SECONDEXTRA).build();
        Allowance allowanceToEdit = (Allowance) model.getFilteredExpenseList()
                .get(INDEX_FIRST_ALLOWANCE.getZeroBased());
        Allowance editedAllowance = createEditedAllowance(allowanceToEdit, descriptor);
        CommandResult commandResult = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor)
                .execute(model, commandHistory);

        assertEquals(String.format(EditAllowanceCommand.MESSAGE_EDIT_ALLOWANCE_SUCCESS, editedAllowance),
                commandResult.getFeedbackToUser());
        assertEquals(editedAllowance, model.getFilteredExpenseList().get(INDEX_FIRST_ALLOWANCE.getZeroBased()));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_invalidIndex_failure() {
        showExpenseAtIndex(model, INDEX_FIRST_ALLOWANCE);
        Index outOfBoundIndex = INDEX_SECOND_ALLOWANCE;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEPiggy().getExpenseList().size());
        EditAllowanceCommand.EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withName(VALID_NAME_FIRSTEXTRA).build();
        EditAllowanceCommand editAllowanceCommand = new EditAllowanceCommand(outOfBoundIndex, descriptor);
        assertCommandFailure(editAllowanceCommand, model, commandHistory,
                MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_indexIsExpense_failure() {
        Model tempModel = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        Expense toEdit = tempModel.getFilteredExpenseList()
                .get(INDEX_FIRST_EXPENSE.getZeroBased());
        assertFalse(toEdit instanceof Allowance);

        EditAllowanceCommand.EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder().build();
        EditAllowanceCommand editAllowanceCommand = new EditAllowanceCommand(INDEX_FIRST_EXPENSE, descriptor);

        assertCommandFailure(editAllowanceCommand, tempModel, commandHistory,
                EditAllowanceCommand.MESSAGE_ITEM_NOT_ALLOWANCE);
    }

}
