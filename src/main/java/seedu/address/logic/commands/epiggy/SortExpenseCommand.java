package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Expense;


//@@author rahulb99
/**
 * Finds and lists all expenses in EPiggy whose expense contains any of the argument keywordss.
 * keywords matching is case insensitive.
 */
public class SortExpenseCommand extends Command {

    public static final String COMMAND_WORD = "sortExpense";
    public static final String COMMAND_ALIAS = "sE";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Sorts the expenses as specified by the user, according to name, cost, date or tag. \n"
            + " Parameters: -[n/d/$]/...\n"
            + " Example: " + COMMAND_WORD + " n/";
    public static final String MESSAGE_SUCCESS = "Sorted %1$d Expenses...\n";

    private final Comparator<Expense> expenseComparator;

    public SortExpenseCommand(Comparator<Expense> expenseComparator) {
        assert expenseComparator != null : "keywords should not be null.";
        this.expenseComparator = expenseComparator;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortExpenses(expenseComparator);
        model.updateFilteredExpensesList(Model.PREDICATE_SHOW_ALL_EXPENSES);
        model.commitEPiggy();

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, model.getFilteredExpenseList().size()));
    }

    public Comparator<Expense> getExpenseComparator() {
        return expenseComparator;
    }
}
