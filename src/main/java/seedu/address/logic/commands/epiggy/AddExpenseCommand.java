package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expense.Expense;

/**
 * Adds a person to the toAdd book.
 */
public class AddExpenseCommand extends Command {

    public static final String COMMAND_WORD = "addExpense";
    public static final String COMMAND_ALIAS = "aE";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expense to the expense book. ";

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";

    public static final String MESSAGE_INSUFFICIENT_AMOUNT = "You have insufficient funds. "
            + "You have $%1$s available in ePiggy.\n"
            + "Please check that you have entered the correct amount or updated ePiggy with any new allowances.";


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
