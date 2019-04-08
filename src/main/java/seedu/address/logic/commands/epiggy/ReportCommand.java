package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.ui.ReportWindow;

/**
 * Shows summary to the user.
 */
public class ReportCommand extends Command {
    public static final String COMMAND_WORD = "report";
    public static final String COMMAND_ALIAS = "rp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows report to the user.\n"
            + "Parameters: "
            + CliSyntax.PREFIX_DATE + "SPECIFY DATE, MONTH OR YEAR \n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "21/03/2019\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "03/2019\n"
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_DATE + "2019\n";

    public static final String MESSAGE_SUCCESS = "Showed report.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private String type = "ALL";
    private LocalDate date;

    /**
     * Constructor with chart type.
     * @param date
     */
    public ReportCommand(LocalDate date, String type) {
        this.date = date;
        this.type = type;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ReportWindow summaryWindow = new ReportWindow();
        summaryWindow.displayReportController(model, date, type);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
