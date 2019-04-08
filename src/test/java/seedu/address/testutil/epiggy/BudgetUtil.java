package seedu.address.testutil.epiggy;

import java.text.SimpleDateFormat;

import seedu.address.logic.commands.epiggy.AddBudgetCommand;
import seedu.address.logic.commands.epiggy.EditBudgetCommand;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.expense.Budget;

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_COST + String.valueOf(budget.getBudgetedAmount().getAmount()) + " ");
        sb.append(CliSyntax.PREFIX_PERIOD + budget.getPeriod().toString() + " ");
        sb.append(CliSyntax.PREFIX_DATE + sdf.format(budget.getStartDate()) + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditBudgetDetails}'s details.
     */
    public static String getEditBudgetDetailsDetails(EditBudgetCommand.EditBudgetDetails details) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        details.getAmount().ifPresent(amount -> sb.append(CliSyntax.PREFIX_COST)
                .append(String.valueOf(amount.getAmount())).append(" "));
        details.getPeriod().ifPresent(period -> sb.append(CliSyntax.PREFIX_PERIOD).append(period.toString()).append(" "));
        details.getStartDate().ifPresent(date -> sb.append(CliSyntax.PREFIX_DATE).append(sdf.format(date)).append(" "));
        return sb.toString();
    }
}
