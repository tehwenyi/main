package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.Savings;
import seedu.address.model.epiggy.exceptions.ExpenseNotFoundException;
import seedu.address.model.expense.Expense;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedEPiggy versionedEPiggy;
    private final UserPrefs userPrefs;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Budget> filteredBudget;
    private final SimpleObjectProperty<Expense> selectedExpense = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given ePiggy and userPrefs.
     */
    public ModelManager(ReadOnlyEPiggy ePiggy, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(ePiggy, userPrefs);

        logger.fine("Initializing with address book: " + ePiggy + " and user prefs " + userPrefs);

        versionedEPiggy = new VersionedEPiggy(ePiggy);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredExpenses = new FilteredList<>(versionedEPiggy.getExpenseList());
        //        filteredExpenses.addListener(this::ensureSelectedPersonIsValid);
        filteredBudget = new FilteredList<>(versionedEPiggy.getBudgetList());
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
    public Path getEPiggyFilePath() {
        return userPrefs.getEPiggyFilePath();
    }

    @Override
    public void setEPiggyFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setEPiggyFilePath(addressBookFilePath);
    }

    //=========== EPiggy ================================================================================

    @Override
    public void setEPiggy(ReadOnlyEPiggy ePiggy) {
        versionedEPiggy.resetData(ePiggy);
    }

    @Override
    public ReadOnlyEPiggy getEPiggy() {
        return versionedEPiggy;
    }

    @Override
    public void setExpense(Expense target,
                           Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        versionedEPiggy.setExpense(target, editedExpense);
    }

    @Override
    public void addExpense(Expense expense) {
        versionedEPiggy.addExpense(expense);
        updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void addAllowance(Allowance allowance) {
        versionedEPiggy.addAllowance(allowance);
        updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void addBudget(int index, Budget budget) {
        versionedEPiggy.addBudget(index, budget); }

    /**
     * Checks if there are any overlapping budgets.
     */
    public boolean budgetsOverlap(Date startDate, Date endDate, Budget earlierBudget) {
        return versionedEPiggy.budgetsOverlap(startDate, endDate, earlierBudget);
    }

    @Override
    public void deleteBudgetAtIndex(int index) {
        versionedEPiggy.deleteBudgetAtIndex(index);
    }

    @Override
    public void deleteExpense(Expense toDelete) {
        versionedEPiggy.deleteExpense(toDelete);
    }

    @Override
    public ObservableList<Budget> getBudgetList() {
        return versionedEPiggy.getBudgetList();
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return versionedEPiggy.getExpenseList();
    }

    @Override
    public int getCurrentBudgetIndex() {
        return versionedEPiggy.getCurrentBudgetIndex();
    }

    @Override
    public SimpleObjectProperty<Savings> getSavings() {
        return versionedEPiggy.getSavings();
    }

    @Override
    public SimpleObjectProperty<Goal> getGoal() {
        return versionedEPiggy.getGoal();
    }

    @Override
    public void setGoal(Goal goal) {
        versionedEPiggy.setGoal(goal);
    }

    @Override
    public void setCurrentBudget(Budget editedBudget) {
        requireNonNull(editedBudget);

        versionedEPiggy.setCurrentBudget(editedBudget);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code expense} backed by the internal list of
     * {@code versionedEPiggy}
     */
    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return filteredExpenses;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Budget} backed by the internal list of
     * {@code versionedEPiggy}
     */
    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return filteredBudget;
    }

    //@@author rahulb99
    @Override
    public void updateFilteredExpensesList(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
    }

    //@@author rahulb99
    /**
     * Sorts the expenses according to the keyword.
     * @param comparator expense comparator
     */
    public void sortExpenses(Comparator<Expense> comparator) {
        requireAllNonNull(comparator);
        versionedEPiggy.sortExpense(comparator);
    }

    @Override
    public void updateFilteredBudgetList(Predicate<Budget> predicate) {
        requireNonNull(predicate);
        filteredBudget.setPredicate(predicate);
    }

    //@@author rahulb99

    /**
     * Reveres the {@code filteredExpenses} list.
     */
    public void reverseFilteredExpensesList() {
        versionedEPiggy.reverseExpenseList();
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoEPiggy() {
        return versionedEPiggy.canUndo();
    }

    @Override
    public boolean canRedoEPiggy() {
        return versionedEPiggy.canRedo();
    }

    @Override
    public void undoEPiggy() {
        versionedEPiggy.undo();
    }

    @Override
    public void redoEPiggy() {
        versionedEPiggy.redo();
    }

    @Override
    public void commitEPiggy() {
        versionedEPiggy.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Expense> selectedExpenseProperty() {
        return selectedExpense;
    }

    @Override
    public Expense getSelectedExpense() {
        return selectedExpense.getValue();
    }

    @Override
    public void setSelectedExpense(Expense expense) {
        if (expense != null && !filteredExpenses.contains(expense)) {
            throw new ExpenseNotFoundException(); //TODO
        }
        selectedExpense.setValue(expense);
    }

    /**
     * Ensures {@code selectedExpense} is a valid person in {@code filteredPersons}.
     */
    //    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends expense> change) {
    //        while (change.next()) {
    //            if (selectedExpense.getValue() == null) {
    //                // null is always a valid selected person, so we do not need to check that it is valid anymore.
    //                return;
    //            }
    //
    //            boolean wasSelectedPersonReplaced = change.wasReplaced() &&
    //            change.getAddedSize() == change.getRemovedSize()
    //                    && change.getRemoved().contains(selectedExpense.getValue());
    //            if (wasSelectedPersonReplaced) {
    //                // Update selectedExpense to its new value.
    //                int index = change.getRemoved().indexOf(selectedExpense.getValue());
    //                selectedExpense.setValue(change.getAddedSubList().get(index));
    //                continue;
    //            }
    //
    //            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
    //                    .anyMatch(removedPerson -> selectedExpense.getValue().isSameExpense(removedPerson));
    //            if (wasSelectedPersonRemoved) {
    //                // Select the person that came before it in the list,
    //                // or clear the selection if there is no such person.
    //                selectedExpense.setValue(change.getFrom() > 0 ? change.getList()
    //                .get(change.getFrom() - 1) : null);
    //            }
    //        }
    //    }

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
        return versionedEPiggy.equals(other.versionedEPiggy)
                && userPrefs.equals(other.userPrefs)
                && filteredExpenses.equals(other.filteredExpenses)
                && Objects.equals(selectedExpense.get(), other.selectedExpense.get());
    }

    @Override
    public String toString() {
        return "ModelManager{"
                + "versionedAddressBook=" + versionedEPiggy
                + ", userPrefs=" + userPrefs
                + ", filteredExpenses=" + filteredExpenses
                + ", filteredBudget=" + filteredBudget
                + ", selectedExpense=" + selectedExpense
                + ", selectedExpense=" + selectedExpense
                + '}';
    }
}
