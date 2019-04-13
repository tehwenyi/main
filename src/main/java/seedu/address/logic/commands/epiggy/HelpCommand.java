package seedu.address.logic.commands.epiggy;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

//@@author rahulb99
/**
 * Lists all the commands with their brief description and syntax.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_ALIAS = "hp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows ePiggy's usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window!";

    /**
     * Execute `help` command.
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return {@code CommandResult}
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }

}
