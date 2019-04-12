package seedu.address.testutil.epiggy;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.text.SimpleDateFormat;

import seedu.address.logic.commands.epiggy.AddAllowanceCommand;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Allowance.
 */
public class AllowanceUtil {
    public static String getAddAllowanceCommand(Allowance allowance) {
        return AddAllowanceCommand.COMMAND_WORD + " " + getAllowanceDetails(allowance);
    }

    public static String getAllowanceDetails(Allowance allowance) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        sb.append(PREFIX_NAME + allowance.getItem().getName().toString() + " ");
        sb.append(PREFIX_COST + allowance.getItem().getCost().toString() + " ");
        sb.append(PREFIX_DATE + sdf.format(allowance.getDate()) + " ");
        for (Tag t : allowance.getItem().getTags()) {
            if (!(t.toString().equals("[Allowance]") || t.toString().equals("[Expense]"))) {
                sb.append(PREFIX_TAG + t.toString().substring(1, t.toString().length() - 1) + " ");
            }
        }
        return sb.toString();
    }
}
