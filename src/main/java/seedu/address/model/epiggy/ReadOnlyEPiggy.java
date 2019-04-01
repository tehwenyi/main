package seedu.address.model.epiggy;

import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an epiggy.
 */
public interface ReadOnlyEPiggy extends Observable {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

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

    SimpleObjectProperty<Savings> getSavings();

    SimpleObjectProperty<Goal> getGoal();
}
