package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.item.Period;
import seedu.address.model.epiggy.item.Price;

import static seedu.address.logic.commands.epiggy.SetBudgetCommand.MESSAGE_OVERLAPPING_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUDGETS;


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

    private static final String MESSAGE_DELETE_BUDGET_SUCCESS = "Deleted budget: %1$s";
    private static final String MESSAGE_INDEX_OUT_OF_BOUNDS = "The index does not exist on the budget list.";

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
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_BUDGET_SUCCESS, budgetToDelete));
    }
}
