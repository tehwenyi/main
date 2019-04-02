package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Budget;

/**
 * Edits the current budget in ePiggy.
 */
public class DeleteBudgetCommand extends Command {

    public static final String COMMAND_WORD = "deleteBudget";
    public static final String COMMAND_ALIAS = "db";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the budget identified by the index number used in the displayed budget list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BUDGET_SUCCESS = "Deleted budget: %1$s";
    public static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The index does not exist on the budget list.";

    private final Index targetIndex;

    public DeleteBudgetCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Budget> lastShownBudgetList = model.getFilteredBudgetList();

        if (targetIndex.getZeroBased() >= lastShownBudgetList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BUDGET_DISPLAYED_INDEX);
        }

        Budget budgetToDelete = lastShownBudgetList.get(this.targetIndex.getZeroBased());
        model.deleteBudgetAtIndex(this.targetIndex.getZeroBased());
        model.commitEPiggy();
        return new CommandResult(String.format(MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBudgetCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteBudgetCommand) other).targetIndex)); // state check
    }
}
