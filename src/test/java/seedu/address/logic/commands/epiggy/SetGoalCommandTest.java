package seedu.address.logic.commands.epiggy;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.util.Date;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.epiggy.Savings;
import seedu.address.model.person.Person;
import seedu.address.testutil.epiggy.GoalBuilder;

public class SetGoalCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullGoal_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetGoalCommand(null);
    }

    @Test
    public void execute_goalAcceptedByModel_setSuccessful() throws Exception {
        ModelStubAcceptingGoal modelStub = new ModelStubAcceptingGoal();
        Goal validGoal = new GoalBuilder().build();
        CommandResult commandResult = new SetGoalCommand(validGoal).execute(modelStub, commandHistory);

        assertEquals(String.format(SetGoalCommand.MESSAGE_SUCCESS, validGoal), commandResult.getFeedbackToUser());
        assertEquals(validGoal, modelStub.goal.get());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

    }

    private class ModelStub implements Model {

        @Override
        public void sortExpenses(String keyword) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Budget> getBudgetList() {
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
        public void setEPiggyFilePath(Path ePiggyFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
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
        public SimpleObjectProperty<Savings> getSavings() {
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
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentBudget(Budget editedBudget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
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
        public void updateFilteredPersonList(Predicate<Person> predicate) {
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
        public ReadOnlyProperty<Person> selectedPersonProperty() {
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

        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
    }



    private class ModelStubAcceptingGoal extends ModelStub {
        final SimpleObjectProperty<Goal> goal = new SimpleObjectProperty<>();

        @Override
        public void setGoal(Goal goal) {
            this.goal.setValue(goal);
        }

        @Override
        public SimpleObjectProperty<Goal> getGoal() {
            return this.goal;
        }

        @Override
        public void commitEPiggy() {
            // Called by {@code SetCommand#execute()}
        }
    }
}
