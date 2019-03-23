package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.commands.epiggy.EditBudgetCommand.MESSAGE_OVERLAPPING_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.address.model.epiggy.UniqueBudgetList.MAXIMUM_SIZE;

import java.util.Date;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Budget;

/**
 * Sets a budget for the total expenses.
 */
public class SetBudgetCommand extends Command {
    public static final String COMMAND_WORD = "setBudget";
    public static final String COMMAND_ALIAS = "sb";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets a budget for ePiggy. "
            + "Parameters: "
            + PREFIX_COST + "BUDGET_IN_DOLLARS "
            + PREFIX_PERIOD + "TIME_PERIOD_IN_DAYS "
            + PREFIX_DATE + "START_DATE_IN_DD/MM/YYYY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COST + "500 "
            + PREFIX_PERIOD + "28 "
            + PREFIX_DATE + "01/02/2019";

    public static final String MESSAGE_SUCCESS = "Budget is set at: %1$s";
    public static final String MESSAGE_FAIL = "Budget date is too old to be added. "
            + "The budget list can only accommodate a maximum of " + MAXIMUM_SIZE
            + " budgets, please delete one of the old budgets if you wish to add this budget.";
    //    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    // public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in the address book";

    private final Budget toSet;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SetBudgetCommand(Budget budget) {
        requireNonNull(budget);
        toSet = budget;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Date startDate = toSet.getStartDate();
        Date endDate = toSet.getEndDate();

        List<Budget> currentList = model.getFilteredBudgetList();
        if (currentList.size() == 0) {
            model.addBudget(0, toSet);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toSet));
        }

        int index = 0;
        Budget earlierBudget;
        Budget laterBudget = currentList.get(0);
        while (index < currentList.size()) {
            earlierBudget = currentList.get(index);
            if (index > 0) {
                laterBudget = currentList.get(index - 1);
            }
            // Check for overlaps
            if (!startDate.after(earlierBudget.getStartDate()) && endDate.after(earlierBudget.getStartDate())) {
                throw new CommandException(MESSAGE_OVERLAPPING_BUDGET);
            }
            if (!startDate.before(earlierBudget.getStartDate()) && !endDate.after(earlierBudget.getEndDate())) {
                throw new CommandException(MESSAGE_OVERLAPPING_BUDGET);
            }
            if (startDate.before(earlierBudget.getEndDate()) && !endDate.before(earlierBudget.getEndDate())) {
                throw new CommandException(MESSAGE_OVERLAPPING_BUDGET);
            }

            if (!startDate.before(earlierBudget.getEndDate())) {
                if (index == 0 || !endDate.after(laterBudget.getStartDate())) {
                    model.addBudget(index, toSet);
                    model.commitAddressBook();
                    return new CommandResult(String.format(MESSAGE_SUCCESS, toSet));
                } else {
                    throw new CommandException(MESSAGE_OVERLAPPING_BUDGET);
                }
            }
            index++;
        }
        if (index < MAXIMUM_SIZE) {
            model.addBudget(index, toSet);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toSet));
        }
        return new CommandResult(MESSAGE_FAIL);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetBudgetCommand // instanceof handles nulls
                && toSet.equals(((SetBudgetCommand) other).toSet));
    }
}
