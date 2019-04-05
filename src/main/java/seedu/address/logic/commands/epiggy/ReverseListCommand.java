package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

//@@author rahulb99
/**
 * Lists all Expenses in reverse order to the user.
 */
public class ReverseListCommand extends Command {

    public static final String COMMAND_WORD = "reverseList";
    public static final String COMMAND_ALIAS = "rl";

    public static final String MESSAGE_SUCCESS = "Listed all expenses in reverse.";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.reverseFilteredExpensesList();
        model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
