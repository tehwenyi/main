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

    private final ObservableList<Budget> internalList = FXCollections.observableArrayList();
    private final ObservableList<Budget> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent budget as the given argument.
     */
    public boolean contains(Budget toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a new budget to the list.
     */
    public void add(Budget toAdd) {
        requireNonNull(toAdd);
        // if (contains(toAdd)) {
        //     throw new DuplicateBudgetException();
        // }
        internalList.add(toAdd);
    }

    /**
     * Gets the last budget on the internal list, which is the previous budget.
     * There must be at least one budget in {@code internalList}
     */
    public Budget getPreviousBudget() {
        requireNonNull(internalList);
        return internalList.get(internalList.size() - 1);
    }

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

    public void setBudgetList(UniqueBudgetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code budgetList}.
     * {@code budgetList} must not contain duplicate budgetList.
     */
    public void setBudgetList(List<Budget> budgetList) {
        requireAllNonNull(budgetList);
        if (!budgetsAreUnique(budgetList)) {
            throw new DuplicateBudgetException();
        }

        internalList.setAll(budgetList);
    }

    /**
     * Replaces the previous budget in the list with {@code editedBudget}.
     * {@code target} must exist in the list.
     * The budget identity of {@code editedBudget} must not be the same as another existing budget in the list.
     */
    public void replacePreviousBudgetWith(Budget editedBudget) {
        requireNonNull(editedBudget);

        int index = internalList.size() - 1;
//        int index = internalList.indexOf(target);
//        if (index == -1) {
//            throw new PersonNotFoundException();
//        }
//
//        if (!target.equals(editedBudget) && contains(editedBudget)) {
//            throw new DuplicateBudgetException();
//        }
        internalList.set(index, editedBudget);
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
