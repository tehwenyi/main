package seedu.address.model.epiggy;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.epiggy.exceptions.DuplicateBudgetException;


/**
 * A list of budgetList that enforces uniqueness between its elements and does not allow nulls.
 * A budget is considered unique by comparing using {@code Budget#equals(Budget)}. As such, adding and updating of
 * budgetList uses Budget#equals(Budget) for equality so as to ensure that the budget being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a budget uses Budget#equals(Object) so
 * as to ensure that the budget with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueBudgetList implements Iterable<Budget> {

    // TODO: max 10/20 budget per list
    public static final int MAXIMUM_SIZE = 20;
    private final ObservableList<Budget> internalList = FXCollections.observableArrayList();
    private final ObservableList<Budget> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent budget as the given argument.
     * @param toCheck if the budget exists in the internalList already.
     * @return true if internalList already contains the budget.
     */
    public boolean contains(Budget toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a new budget to the top of the list.
     * Only called when a new budget is added.
     * @param toAdd the budget to be added.
     */
    public void addAtIndex(int index, Budget toAdd) {
        requireNonNull(toAdd);
        // if (contains(toAdd)) {
        //     throw new DuplicateBudgetException();
        // }
        internalList.add(index, toAdd);
        limitSize();
    }

    /**
     * Gets the last budget on the internal list, which is the previous budget.
     * There must be at least one budget in {@code internalList}
     * @return the latest budget in {@code internalList}.
     */
    public Budget getLatestBudget() {
        requireNonNull(internalList);
        return internalList.get(0);
    }

    /**
     * Gets the size of internal list.
     * @return the size of {@code internalList}.
     */
    public int getBudgetListSize() {
        return internalList.size();
    }

    //    /**
    //     * Removes the equivalent budget from the list.
    //     * The budget must exist in the list.
    //     */
    //    public void remove(Budget toRemove) {
    //        requireNonNull(toRemove);
    //        if (!internalList.remove(toRemove)) {
    //            throw new PersonNotFoundException();
    //        }
    //    }

    /**
     * Sets internalList to a new list of the same type.
     * @param replacement list.
     */
    public void setBudgetList(UniqueBudgetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        limitSize();
    }

    /**
     * Replaces the contents of this list with {@code budgetList}.
     * {@code budgetList} must not contain duplicate budgetList.
     * @param newBudgetList to replace.
     */
    public void setBudgetList(List<Budget> newBudgetList) {
        requireAllNonNull(newBudgetList);
        if (!budgetsAreUnique(newBudgetList)) {
            throw new DuplicateBudgetException();
        }

        this.internalList.setAll(newBudgetList);
        limitSize();
    }

    /**
     * Replaces the previous budget in the list with {@code editedBudget}.
     * The budget identity of {@code editedBudget} must not be the same as another existing budget in the list.
     */
    public void replaceLatestBudgetWith(Budget editedBudget) {
        requireAllNonNull(internalList, editedBudget);
        internalList.set(0, editedBudget);
    }

    /**
     * Ensures the size of budgetList does not exceed {@code MAXIMUM_SIZE}.
     * Deletes all budgets after the {@code MAXIMUM_SIZE} has exceeded.
     */
    public void limitSize() {
        requireNonNull(internalList);
        int budgetListSize = internalList.size();
        if (budgetListSize > MAXIMUM_SIZE) {
            for (int i = budgetListSize; i > MAXIMUM_SIZE; i--) {
                internalList.remove(MAXIMUM_SIZE);
            }
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Budget> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Budget> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBudgetList // instanceof handles nulls
                        && internalList.equals(((UniqueBudgetList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code budgetList} contains only unique budgetList.
     */
    private boolean budgetsAreUnique(List<Budget> budgetList) {
        for (int i = 0; i < budgetList.size() - 1; i++) {
            for (int j = i + 1; j < budgetList.size(); j++) {
                if (budgetList.get(i).equals(budgetList.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
