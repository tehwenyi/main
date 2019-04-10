package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.item.Cost;

/**
 * Views the current goal set.
 */
public class ViewGoalCommand extends Command {

    public static final String COMMAND_WORD = "viewGoal";
    public static final String COMMAND_ALIAS = "vg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View your goal set. ";

    public static final String MESSAGE_SUCCESS = "Your current goal is: %1$s\n";

    public static final String MESSAGE_SAVINGS_LESS_THAN_GOAL = "$%2$s more to go!";
    public static final String MESSAGE_SAVINGS_MORE_THAN_GOAL = "You have reached your savings goal! Congratulations!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        SimpleObjectProperty<Cost> savings = model.getSavings();
        SimpleObjectProperty<Goal> goal = model.getGoal();

        double goalAmount = goal.getValue().getAmount().getAmount();
        double savingsAmount = savings.getValue().getAmount();

        double diff = goalAmount - savingsAmount;

        if (diff < 0) {
            return new CommandResult(String.format(MESSAGE_SUCCESS
                    + MESSAGE_SAVINGS_MORE_THAN_GOAL, goal.getValue()));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS
                    + MESSAGE_SAVINGS_LESS_THAN_GOAL, goal.getValue(), diff));
        }
    }
}
