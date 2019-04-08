package seedu.address.model;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Item;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyEPiggy extends Observable {

    /**
     * Returns an unmodifiable view of the expense list.
     */
    ObservableList<Expense> getExpenseList();

    /**
     * Returns an unmodifiable view of the item list.
     */
    ObservableList<Item> getItemList();

    /**
     * Returns an unmodifiable view of the budget list.
     */
    ObservableList<Budget> getBudgetList();

    ObservableValue<Cost> getSavings();

    SimpleObjectProperty<Goal> getGoal();
}
