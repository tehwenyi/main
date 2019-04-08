package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.exceptions.ExpenseNotFoundException;

//@@author rahulb99

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A expense is considered unique by comparing using {@code expense#isSamePerson(expense)}. As such,
 * adding and updating of persons uses expense#isSamePerson(expense) for equality so as to
 * ensure that the expense being added or updated is
 * unique in terms of identity in the ExpenseList. However, the removal of a expense uses expense#equals(Object) so
 * as to ensure that the expense with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class ExpenseList implements Iterable<Expense> {

    private final ObservableList<Expense> internalList = FXCollections.observableArrayList();
    private final ObservableList<Expense> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a expense to the list.
     */
    public void add(Expense toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the list.
     * The expense identity of {@code editedExpense} must not be the same as another existing expense in the list.
     */
    public void setExpense(Expense target,
                           Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExpenseNotFoundException();
        }

        internalList.set(index, editedExpense);
    }

    /**
     * Removes the equivalent expense from the list.
     * The expense must exist in the list.
     */
    public void remove(Expense toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ExpenseNotFoundException();
        }
    }

    /**
     * Removes the expense with the specific index from the list.
     * The expense of the index must exist in the list.
     * @param index of the expense to be removed.
     */
    public void remove(int index) {
        internalList.remove(index, index + 1);
    }


    /**
     * Replaces the contents of this list with {@code expenses}.
     * {@code expenses} must not contain duplicate expenses.
     */
    public void setExpenses(List<Expense> expenses) {
        requireAllNonNull(expenses);
        internalList.setAll(expenses);
    }

    public double getTotalExpenses() {
        double sum = internalUnmodifiableList.stream()
                .filter(expense -> !(expense instanceof Allowance))
                .mapToDouble(expense -> expense.getItem().getCost().getAmount())
                .sum();
        return sum;
    }

    public double getTotalAllowances() {
        double sum = internalUnmodifiableList.stream()
                .filter(allowance -> allowance instanceof Allowance)
                .mapToDouble(allowance -> allowance.getItem().getCost().getAmount())
                .sum();
        return sum;
    }

    public double getTotalSavings() {
        return getTotalAllowances() - getTotalExpenses();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expense> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Expense> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseList // instanceof handles nulls
                && internalList.equals(((ExpenseList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * @return size of {@param internalList}
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns list of expenses sorted by date
     * @return SortedList of expense
     */
    public SortedList<Expense> sortByDate() {
        return internalList.sorted(new Comparator<Expense>() {
            public int compare(Expense e1, Expense e2) {
                if (e1.getDate() == null || e2.getDate() == null) {
                    return 0;
                }
                return e1.getDate().compareTo(e2.getDate());
            }
        });
    }

    public void sort(Comparator<Expense> comparator) {
        FXCollections.sort(internalList, comparator);
    }

    public void reverse() {
        FXCollections.reverse(internalList);
    }

    /**
     * Calculates total cost of expenses.
     * @return total spending
     */
    public double getTotalSpendings() {
        List<Double> costList = internalList.stream().map(e -> e.getItem().getCost().getAmount())
                .collect(Collectors.toList());
        return costList.stream().mapToDouble(f -> f.doubleValue()).sum();
    }
}
