package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.expense.Allowance;
import seedu.address.model.expense.Expense;

/**
 * Edits the details of an existing allowance.
 */
public class EditAllowanceCommand extends EditExpenseCommand {

    public static final String COMMAND_WORD = "editAllowance";
    public static final String COMMAND_ALIAS = "eA";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the allowance identified "
            + "by the index number used in the displayed expense list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + CliSyntax.PREFIX_NAME + "NAME] "
            + "[" + CliSyntax.PREFIX_COST + "COST] "
            + "[" + CliSyntax.PREFIX_DATE + "DATE] "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + CliSyntax.PREFIX_COST + "5 ";

    public static final String MESSAGE_EDIT_ALLOWANCE_SUCCESS = "Edited Allowance: %1$s";

    private static final String MESSAGE_ITEM_NOT_ALLOWANCE = "The item selected is not an allowance. "
            + "Please use " + EditExpenseCommand.COMMAND_WORD + " to edit expenses and "
            + COMMAND_WORD + " to edit allowances.";

    /**
     * @param index                 of the person in the filtered person list to edit
     * @param editExpenseDescriptor details to edit the person with
     */
    public EditAllowanceCommand(Index index, EditExpenseDescriptor editExpenseDescriptor) {
        super(index, editExpenseDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenseList();

        if (super.index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense toEdit = lastShownList.get(index.getZeroBased());
        if (!(toEdit instanceof Allowance)) {
            throw new CommandException(MESSAGE_ITEM_NOT_ALLOWANCE);
        }
        Expense editedExpense = createEditedExpense(toEdit, editExpenseDescriptor);

        model.setExpense(toEdit, editedExpense);
        model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        model.commitEPiggy();
        return new CommandResult(String.format(MESSAGE_EDIT_ALLOWANCE_SUCCESS, editedExpense));
    }
}
