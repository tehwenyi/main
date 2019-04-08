package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.expense.Allowance;
import seedu.address.model.expense.Budget;
import seedu.address.model.expense.Goal;
import seedu.address.model.expense.item.Cost;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.epiggy.ExpensesBuilder;



public class AddExpenseCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_expenseAcceptedByModel_addSuccessful() throws CommandException {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Expense validExpense = new ExpensesBuilder().build();
        CommandResult commandResult = new AddExpenseCommand(validExpense).execute(modelStub, commandHistory);
        assertEquals(String.format(AddExpenseCommand.MESSAGE_SUCCESS, validExpense),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validExpense), modelStub.expenseAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void reverseFilteredExpensesList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Budget> getBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortExpenses(Comparator<Expense> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getEPiggyFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEPiggyFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAllowance(Allowance allowance) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Expense> selectedExpenseProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBudget(int index, Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean budgetsOverlap(Date startDate, Date endDate, Budget earlierBudget) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteBudgetAtIndex(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExpense(Expense toDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getCurrentBudgetIndex() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SimpleObjectProperty<Cost> getSavings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SimpleObjectProperty<Goal> getGoal() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGoal(Goal goal) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEPiggy(ReadOnlyEPiggy newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEPiggy getEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentBudget(Budget editedBudget) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBudgetList(Predicate<Budget> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpensesList(Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpense(Expense target, Expense editedExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Expense getSelectedExpense() {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubAcceptingExpenseAdded extends ModelStub {
        final ArrayList<Expense> expenseAdded = new ArrayList<>();

        @Override
        public void addExpense(Expense expense) {
            requireNonNull(expense);
            expenseAdded.add(expense);
        }

        @Override
        public void commitEPiggy() {
            // called by {@code AddExpense#execute()}
        }
    }
}
