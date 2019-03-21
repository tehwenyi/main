package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

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
    public static final String MESSAGE_FAIL = "Budget has already been set,"
            + "if you want to edit your budget please type editBudget";
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
        if (!model.hasBudget()) {
            model.addBudget(toSet);
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
