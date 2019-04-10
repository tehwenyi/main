package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.expense.Allowance;

/**
 * Adds an allowance to ePiggy.
 */
public class AddAllowanceCommand extends Command {

    public static final String COMMAND_WORD = "addAllowance";
    public static final String COMMAND_ALIAS = "aA";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an allowance to the expense book. "
            + "Parameters: "
            + CliSyntax.PREFIX_NAME + "ALLOWANCE NAME "
            + CliSyntax.PREFIX_COST + "AMOUNT "
            + "[" + CliSyntax.PREFIX_TAG + "TAG]..."
            + "[" + CliSyntax.PREFIX_DATE + "DATE] \n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_NAME + "From Mummy "
            + CliSyntax.PREFIX_COST + "30 "
            + CliSyntax.PREFIX_TAG + "Weekly "
            + CliSyntax.PREFIX_DATE + "31/02/2019 ";

    public static final String MESSAGE_SUCCESS = "New allowance added: %1$s";

    private final Allowance toAdd;

    public AddAllowanceCommand(Allowance toAdd) {
        this.toAdd = toAdd;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.addAllowance(toAdd);
        model.commitEPiggy();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
