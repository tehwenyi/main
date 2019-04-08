package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Adds a person to the toAdd book.
 */
public class AddExpenseCommand extends Command {

    public static final String COMMAND_WORD = "addExpense";
    public static final String COMMAND_ALIAS = "aE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the expense book. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "EXPENSE NAME "
            + CliSyntax.PREFIX_COST + "COST "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]..."
            + "[" + CliSyntax.PREFIX_DATE + "DATE] \n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "Chicken Rice "
            + CliSyntax.PREFIX_COST + "3.50 "
            + CliSyntax.PREFIX_TAG + "Lunch"
            + CliSyntax.PREFIX_DATE + "31/02/2019 ";

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";

    private final Expense toAdd;

    public AddExpenseCommand(Expense expense) {
        this.toAdd = expense;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.addExpense(toAdd);
        model.commitEPiggy();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
