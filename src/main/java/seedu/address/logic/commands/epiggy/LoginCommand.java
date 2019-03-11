package seedu.address.logic.commands.epiggy;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Login with a username and password
 * TODO: implement in future
 */
public class LoginCommand extends Command {


    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "l";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
