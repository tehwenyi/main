package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.ui.ReportWindow;

/**
 * Shows summary to the user.
 */
public class ReportCommand extends Command {
    public static final String COMMAND_WORD = "report";
    public static final String COMMAND_ALIAS = "rp";

    public static final String COMMAND_USAGE_DAILY = COMMAND_WORD
            + ": Shows daily report to the user.";
    public static final String COMMAND_USAGE_MONTHLY = COMMAND_WORD
            + ": Shows monthly report to the user.";
    public static final String COMMAND_USAGE_YEARLY = COMMAND_WORD
            + ": Shows yearly report to the user.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows report to the user.\n"
            + "Parameters: "
            + PREFIX_TYPE + "TYPE OF REPORT \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "monthly ";

    public static final String MESSAGE_SUCCESS = "Showed report.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private String type = "ALL";

    /**
     * Constructor with chart type.
     *
     * @param type
     */
    public ReportCommand(String type) {
        this.type = type;
    }

    /**
     * Default constructor
     */
    public ReportCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ReportWindow summaryWindow = new ReportWindow();
        logger.info("Creates Report window");
        // set a dummy value. TODO: Changes it when other command done.
        ExpenseDisplayType expenseDisplayType = ExpenseDisplayType.valueOf(type);
        switch (expenseDisplayType) {
        case ALL:
            summaryWindow.dispalyAllSummary(model);
            break;
        case DAILY:
            summaryWindow.displayDailyReport(model);
            break;
        case MONTHLY:
            summaryWindow.displayMonthlyReport(model);
            break;
        case YEARLY:
            summaryWindow.dispalyYearlySummary(model);
            break;
        case PERCENTAGE:
            summaryWindow.displayExpensePercentageReport(model);
            break;
        default:
            summaryWindow.displayMonthlyReport(model);
            break;
        }

        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }

    /**
     * Chart will be displayed according to expense display type.
     */
    public enum ExpenseDisplayType {
        MONTHLY, DAILY, TOTAL, YEARLY, PERCENTAGE, ALL
    }
}
