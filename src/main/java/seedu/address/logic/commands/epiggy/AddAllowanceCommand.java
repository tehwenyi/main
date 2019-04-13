package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Allowance;

/**
 * Adds an allowance to ePiggy.
 */
public class AddAllowanceCommand extends Command {

    public static final String COMMAND_WORD = "addAllowance";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an allowance to the epiggy. "
            + "Parameters: "
            + PREFIX_NAME + "ALLOWANCE NAME "
            + PREFIX_COST + "AMOUNT "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_DATE + "DATE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "From Mummy "
            + PREFIX_COST + "30 "
            + PREFIX_TAG + "Weekly "
            + PREFIX_DATE + "21/03/2019 ";

    public static final String MESSAGE_SUCCESS = "New allowance added.\nAdded allowance's details:\n%1$s";

    private final Allowance toAdd;

    public AddAllowanceCommand(Allowance toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.addAllowance(toAdd);
        model.commitEPiggy();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAllowanceCommand // instanceof handles nulls
                && toAdd.equals(((AddAllowanceCommand) other).toAdd));
    }
}
