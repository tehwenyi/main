package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.epiggy.ExpenseContainsKeywordsPredicate;


//@@author rahulb99
/**
 * Finds and lists all expenses in EPiggy whose expense contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SortExpenseCommand extends Command {

    public static final String COMMAND_WORD = "sortExpense";
    public static final String COMMAND_ALIAS = "sE";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Sorts the expenses as specified by the user. "
            + " The keywords do not need to be in order.\n"
            + " Parameters: -[n/d/$]...\n"
            + " Example: " + COMMAND_WORD + " -n";
    public static final String MESSAGE_SUCCESS = "Sorting Expenses...\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredExpensesList(predicate);
        model.commitAddressBook();

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getFilteredExpenseList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortExpenseCommand // instanceof handles nulls
                && predicate.equals(((SortExpenseCommand) other).predicate)); // state check
    }

}
