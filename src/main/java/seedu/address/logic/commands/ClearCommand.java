package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.EPiggy;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clearExpense";
    public static final String COMMAND_ALIAS = "cE";
    public static final String MESSAGE_SUCCESS = "ePiggy's expense list has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setEPiggy(new EPiggy());
        model.commitEPiggy();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
