package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

import org.junit.Test;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.Goal;

import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.person.Person;
import seedu.address.testutil.epiggy.GoalBuilder;

//@@author kev-inc

public class ViewGoalCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_viewSuccessful_savingsLessThanGoal() throws Exception {
        Goal validGoal = new GoalBuilder().build();
        Cost validSavings = new Cost(100);
        double diff = validGoal.getAmount().getAmount() - validSavings.getAmount();
        ModelStubWithGoalAndSavings modelStub = new ModelStubWithGoalAndSavings(validGoal, validSavings);
        CommandResult commandResult = new ViewGoalCommand().execute(modelStub, commandHistory);

        assertEquals(String.format(ViewGoalCommand.MESSAGE_SUCCESS
                + ViewGoalCommand.MESSAGE_SAVINGS_LESS_THAN_GOAL, validGoal, diff), commandResult.getFeedbackToUser());
        assertEquals(validGoal, modelStub.goal.get());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_viewSuccessful_savingsMoreThanGoal() throws Exception {
        Goal validGoal = new GoalBuilder().build();
        Cost validSavings = new Cost(300);
        ModelStubWithGoalAndSavings modelStub = new ModelStubWithGoalAndSavings(validGoal, validSavings);
        CommandResult commandResult = new ViewGoalCommand().execute(modelStub, commandHistory);

        assertEquals(String.format(ViewGoalCommand.MESSAGE_SUCCESS
                + ViewGoalCommand.MESSAGE_SAVINGS_MORE_THAN_GOAL, validGoal), commandResult.getFeedbackToUser());
        assertEquals(validGoal, modelStub.goal.get());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }



    private class ModelStub implements Model {
        @Override
        public void reverseFilteredExpensesList() {
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
        public ObservableList<Budget> getBudgetList() {
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

    private class ModelStubWithGoalAndSavings extends ModelStub {

        final SimpleObjectProperty<Goal> goal = new SimpleObjectProperty<>();
        final SimpleObjectProperty<Cost> savings = new SimpleObjectProperty<>();

        ModelStubWithGoalAndSavings(Goal goal, Cost savings) {
            requireNonNull(goal);
            requireNonNull(savings);
            this.goal.setValue(goal);
            this.savings.setValue(savings);
        }

        @Override
        public SimpleObjectProperty<Goal> getGoal() {
            return this.goal;
        }

        @Override
        public SimpleObjectProperty<Cost> getSavings() {
            return this.savings;
        }
    }
}
