package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.Savings;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Budget> PREDICATE_SHOW_ALL_BUDGETS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getEPiggyFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setEPiggyFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code ePiggy}.
     */
    void setEPiggy(ReadOnlyEPiggy ePiggy);

    /** Returns the EPiggy */
    ReadOnlyEPiggy getEPiggy();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given expense.
     * {@code person} must not already exist in the address book.
     */
    void addExpense(Expense expense);

    /**
     * Adds the given allowance.
     */
    void addAllowance(Allowance allowance);

    /**
     * Adds a new budget.
     */
    void addBudget(int index, Budget budget);

    /**
     * Checks if there are any overlapping budgets.
     */
    boolean budgetsOverlap(Date startDate, Date endDate, Budget earlierBudget);

    /**
     * Deletes the budget at the specific index.
     */
    void deleteBudgetAtIndex(int index);

    /**
     * Deletes the expense {@code toDelete}.
     * @param toDelete the expense to be deleted.
     */
    void deleteExpense(Expense toDelete);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the address book.
     * The expense identity of {@code editedExpense} must not be the same as
     * another existing expense in the address book.
     */
    void setExpense(Expense target, Expense editedExpense);

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpensesList(Predicate<seedu.address.model.epiggy.Expense> predicate);

    /**
<<<<<<< HEAD
     * Gets the current budget list.
     */
    ObservableList<Budget> getBudgetList();

    /**
     * Gets the Expense list.
     */
    ObservableList<Expense> getExpenseList();

    /**
=======
>>>>>>> 8083632d0f8f287afe6b8a7985e25ad145ba8dc5
     * Gets the current budget's index.
     * @return -1 if there is no current budget.
     */
    int getCurrentBudgetIndex();


    /**
     * Get the current savings.
     */
    SimpleObjectProperty<Savings> getSavings();

    /**
     * Get the savings goal.
     */
    SimpleObjectProperty<Goal> getGoal();

    /**
     * Sets the savings goal.
     */
    void setGoal(Goal goal);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the current budget with {@code editedBudget}.
     */
    void setCurrentBudget(Budget editedBudget);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Expense> getFilteredExpenseList();

    /** Returns an unmodifiable view of the filtered budget list */
    ObservableList<Budget> getFilteredBudgetList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered budget list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBudgetList(Predicate<Budget> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoEPiggy();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoEPiggy();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoEPiggy();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoEPiggy();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitEPiggy();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    ReadOnlyProperty<Expense> selectedExpenseProperty();

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    Expense getSelectedExpense();

    /**
     * Sets the selected person in the filtered person list.
     */
    void setSelectedPerson(Person person);

    /**
     * Sets the selected expense in the filtered expense list.
     */
    void setSelectedExpense(Expense expense);

    /**
     * Sorts the expenses according to the specified {@param expenseComparator}.
     */
    void sortExpenses(Comparator<Expense> expenseComparator);
}
