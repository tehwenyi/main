package seedu.address.testutil.epiggy;

import java.util.Set;

import seedu.address.logic.commands.epiggy.AddExpenseCommand;
import seedu.address.logic.commands.epiggy.EditExpenseCommand;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.expense.Expense;
import seedu.address.model.tag.Tag;

/**
 * A utility class for expense.
 */
public class ExpenseUtil {

    /**
     * Returns an add command string for adding the {@code expense}.
     */
    public static String getAddExpenseCommand(Expense expense) {
        return AddExpenseCommand.COMMAND_WORD + " " + getExpenseDetails(expense);
    }

    public static String getAddExpenseCommandAlias(Expense expense) {
        return AddExpenseCommand.COMMAND_ALIAS + " " + getExpenseDetails(expense);
    }

    /**
     * Returns the part of command string for the given {@code expense}'s details.
     */
    public static String getExpenseDetails(Expense expense) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + expense.getItem().getName().name + " ");
        sb.append(CliSyntax.PREFIX_COST + expense.getItem().getCost().toString() + " ");
        sb.append(CliSyntax.PREFIX_DATE + expense.getDate().toString() + " ");
        expense.getItem().getTags().stream().forEach(s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditExpenseDescriptor}'s details.
     */
    public static String getEditExpenseDescriptorDetails(EditExpenseCommand.EditExpenseDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name).append(" "));
        descriptor.getCost().ifPresent(cost -> sb.append(CliSyntax.PREFIX_COST).append(cost.toString()).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(CliSyntax.PREFIX_DATE).append(date.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(CliSyntax.PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(CliSyntax.PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
