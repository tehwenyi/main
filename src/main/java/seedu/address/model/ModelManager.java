package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.epiggy.Savings;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedEPiggy versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Budget> filteredBudget;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Expense> selectedExpense = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given ePiggy and userPrefs.
     */
    public ModelManager(ReadOnlyEPiggy ePiggy, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(ePiggy, userPrefs);

        logger.fine("Initializing with address book: " + ePiggy + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedEPiggy(ePiggy);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);

        filteredExpenses = new FilteredList<>(versionedAddressBook.getExpenseList());
        filteredBudget = new FilteredList<>(versionedAddressBook.getBudgetList());
        //TODO
    }

    public ModelManager() {
        this(new EPiggy(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path ePiggy) {
        requireNonNull(ePiggy);
        userPrefs.setAddressBookFilePath(ePiggy);
    }

    //=========== EPiggy ================================================================================

    @Override
    public void setAddressBook(ReadOnlyEPiggy ePiggy) {
        versionedAddressBook.resetData(ePiggy);
    }

    @Override
    public ReadOnlyEPiggy getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public void setExpense(seedu.address.model.epiggy.Expense target,
                           seedu.address.model.epiggy.Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        versionedAddressBook.setExpense(target, editedExpense);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addExpense(Expense expense) {
        versionedAddressBook.addExpense(expense);
    }

    @Override
    public void addAllowance(Allowance allowance) {
        versionedAddressBook.addAllowance(allowance);
    }

    @Override
    public void addBudget(int index, Budget budget) {
        versionedAddressBook.addBudget(index, budget); }

    /**
     * Checks if there are any overlapping budgets.
     */
    public boolean budgetsOverlap(Date startDate, Date endDate, Budget earlierBudget) {
        return versionedAddressBook.budgetsOverlap(startDate, endDate, earlierBudget);
    }

    @Override
    public void deleteBudgetAtIndex(int index) {
        versionedAddressBook.deleteBudgetAtIndex(index);
    }

    @Override
    public void deleteExpense(Expense toDelete) {
        versionedAddressBook.deleteExpense(toDelete);
    }

    @Override
    public ObservableList<Budget> getBudgetList() {
        return versionedAddressBook.getBudgetList();
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return versionedAddressBook.getExpenseList();
    }

    @Override
    public int getCurrentBudgetIndex() {
        return versionedAddressBook.getCurrentBudgetIndex();
    }

    @Override
    public SimpleObjectProperty<Savings> getSavings() {
        return versionedAddressBook.getSavings();
    }

    @Override
    public SimpleObjectProperty<Goal> getGoal() {
        return versionedAddressBook.getGoal();
    }

    @Override
    public void setGoal(Goal goal) {
        versionedAddressBook.setGoal(goal);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setCurrentBudget(Budget editedBudget) {
        requireNonNull(editedBudget);

        versionedAddressBook.setCurrentBudget(editedBudget);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return filteredExpenses;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Budget} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return filteredBudget;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //@@author rahulb99
    @Override
    public void updateFilteredExpensesList(Predicate<seedu.address.model.epiggy.Expense> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
    }

    //@@author rahulb99
    /**
     * Sorts the expenses according to the keyword.
     * @param keyword
     */
    public void sortExpenses(String keyword) {
        SortedList<Expense> sortedExpenses;
        switch(keyword) {
        case "n": {
            sortedExpenses = versionedAddressBook.sortExpensesByName();
            break;
        }
        case "d": {
            sortedExpenses = versionedAddressBook.sortExpensesByDate();
            break;
        }
        case "$": {
            sortedExpenses = versionedAddressBook.sortExpensesByAmount();
            break;
        } default: return;
        }
        FilteredList<Expense> fl = new FilteredList<>(sortedExpenses);
        fl.setPredicate(PREDICATE_SHOW_ALL_EXPENSES);
        logger.fine("sorted list");
        versionedAddressBook.getExpenseList();
        versionedAddressBook.indicateModified();
    }

    @Override
    public void updateFilteredBudgetList(Predicate<Budget> predicate) {
        requireNonNull(predicate);
        filteredBudget.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public ReadOnlyProperty<Expense> selectedExpenseProperty() {
        return selectedExpense;
    }

    @Override
    public Expense getSelectedExpense() {
        return selectedExpense.getValue();
    }

    @Override
    public void setSelectedPerson(Person person) {
        if (person != null && !filteredPersons.contains(person)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(person);
    }

    @Override
    public void setSelectedExpense(Expense expense) {
        if (expense != null && !filteredExpenses.contains(expense)) {
            throw new PersonNotFoundException(); //TODO
        }
        selectedExpense.setValue(expense);
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
