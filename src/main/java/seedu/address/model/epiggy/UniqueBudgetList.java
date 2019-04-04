package seedu.address.model.epiggy;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.epiggy.Budget.CURRENT_BUDGET;

import java.util.Date;
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
    public static final int MAXIMUM_SIZE = 20;
    private final ObservableList<Budget> internalList = FXCollections.observableArrayList();
    private final ObservableList<Budget> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a new budget to the top of the list.
     * Only called when a new budget is added.
     * @param toAdd the budget to be added.
     */
    public void addAtIndex(int index, Budget toAdd) throws IndexOutOfBoundsException {
        requireAllNonNull(index, toAdd);
        if (index > MAXIMUM_SIZE) {
            throw new IndexOutOfBoundsException();
        }
        internalList.add(index, toAdd);
        limitSize();
    }

    /**
     * Replaces the budget at index {@code index} with budget {@code toSet}.
     * @param toSet the budget to be added.
     */
    public void replaceAtIndex(int index, Budget toSet) throws IndexOutOfBoundsException {
        requireNonNull(toSet);
        if (index < 0 || index >= internalList.size()) {
            throw new IndexOutOfBoundsException();
        }
        internalList.set(index, toSet);
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
     * Gets the budget on the internal list with the corresponding index.
     * There must be at least one budget in {@code internalList}
     * @return the corresponding budget in {@code internalList}.
     */
    public Budget getBudgetAtIndex(int index) {
        requireNonNull(internalList);
        return internalList.get(index);
    }

    /**
     * Gets the index of the budget based on the date.
     * @return the index of the budget or -1 if the expense date is not in any of the budgets.
     */
    public int getBudgetIndexBasedOnDate(Date date) {
        requireNonNull(internalList);

        for (int i = 0; i < internalList.size(); i++) {
            Budget toCheck = internalList.get(i);
            if ((!toCheck.getStartDate().after(date)) && (!toCheck.getEndDate().before(date))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets the current budget's index.
     * @return -1 if there is no current budget.
     */
    public int getCurrentBudgetIndex() {
        int index = 0;
        while (index < internalList.size()) {
            if (internalList.get(index).getStatus().equals(CURRENT_BUDGET)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Gets the size of internal list.
     * @return the size of {@code internalList}.
     */
    public int getBudgetListSize() {
        return internalList.size();
    }

    /**
     * Removes the budget with the specific index from the list.
     * The budget of the index must exist in the list.
     * @param index of the budget to be removed.
     */
    public void remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= internalList.size()) {
            throw new IndexOutOfBoundsException();
        }
        internalList.remove(index, index + 1);
    }

    /**
     * Replaces the contents of this list with {@code budgetList}.
     * {@code budgetList} must not contain duplicate budgetList.
     * @param newBudgetList to replace.
     */
    public void addBudgetList(List<Budget> newBudgetList) {
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
