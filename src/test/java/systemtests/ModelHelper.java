package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Expense> PREDICATE_MATCHING_NO_EXPENSE = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Expense> toDisplay) {
        Optional<Predicate<Expense>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredExpensesList(predicate.orElse(PREDICATE_MATCHING_NO_EXPENSE));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Expense... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code expense} equals to {@code other}.
     */
    private static Predicate<Expense> getPredicateMatching(Expense other) {
        return person -> person.equals(other);
    }
}
