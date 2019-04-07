package seedu.address.testutil.epiggy;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

import seedu.address.logic.commands.epiggy.AddBudgetCommand;
import seedu.address.logic.commands.epiggy.EditBudgetCommand.EditBudgetDetails;
import seedu.address.model.epiggy.Budget;

/**
 * A utility class for Budget.
 */
public class BudgetUtil {

    /**
     * Returns an add command string for adding the {@code budget}.
     */
    public static String getAddBudgetCommand(Budget budget) {
        return AddBudgetCommand.COMMAND_WORD + " " + getBudgetDetails(budget);
    }

    /**
     * Returns the part of command string for the given {@code budget}'s details.
     */
    public static String getBudgetDetails(Budget budget) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_COST + budget.getBudgetedAmount().toString() + " ");
        sb.append(PREFIX_PERIOD + budget.getPeriod().toString() + " ");
        sb.append(PREFIX_DATE + budget.getStartDate().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditBudgetDetails}'s details.
     */
    public static String getEditBudgetDetailsDetails(EditBudgetDetails details) {
        StringBuilder sb = new StringBuilder();
        details.getAmount().ifPresent(amount -> sb.append(PREFIX_COST).append(amount.toString()).append(" "));
        details.getPeriod().ifPresent(period -> sb.append(PREFIX_PERIOD).append(period.toString()).append(" "));
        details.getStartDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.toString()).append(" "));
        return sb.toString();
    }
}
