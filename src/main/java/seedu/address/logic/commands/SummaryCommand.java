package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Shows summary to the user.
 */
public class SummaryCommand extends Command {
    public static final String COMMAND_WORD = "summary";
    public static final String COMMAND_ALIAS = "y";
    public static final String COMMAND_USAGE = COMMAND_WORD
            + ": Shows summary to the user.";

    public static final String MESSAGE_SUCCESS = "Showed summary.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
