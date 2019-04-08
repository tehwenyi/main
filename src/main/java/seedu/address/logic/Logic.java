package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.expense.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Goal;
import seedu.address.model.expense.item.Cost;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the EPiggy.
     */
    ReadOnlyEPiggy getEPiggy();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Expense> getFilteredExpenseList();

    ObservableList<Budget> getFilteredBudgetList();

    ObservableValue<Cost> getSavings();

    ObservableValue<Goal> getGoal();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getEPiggyFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected expense in the filtered expense list.
     * null if no expense is selected.
     */
    ReadOnlyProperty<Expense> selectedExpenseProperty();

    /**
     * Sets the current budget.
     */
    void setCurrentBudget(Budget budget);

    /**
     * Adds a new budget.
     */
    void addBudget(int index, Budget budget);

    /**
     * Sets the selected expense in the filtered person list.
     */
    void setSelectedExpense(Expense expense);
}
