package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Objects;

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
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Sorts the expenses as specified by the user, according to name, cost, date or tag. \n"
            + " Parameters: [n/d/$]/...\n"
            + " Example: " + COMMAND_WORD + " n/";
    public static final String MESSAGE_SUCCESS = "Sorted %1$d expenses.";

    private final Comparator<Expense> expenseComparator;

    public SortCommand(Comparator<Expense> expenseComparator) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SortCommand that = (SortCommand) o;
        return Objects.equals(expenseComparator, that.expenseComparator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseComparator);
    }

    @Override
    public String toString() {
        return expenseComparator.toString();
    }
}
