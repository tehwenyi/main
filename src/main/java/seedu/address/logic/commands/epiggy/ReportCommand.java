package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
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
//@@author yunjun199321
public class ReportCommand extends Command {
    public static final String COMMAND_WORD = "report";
    public static final String COMMAND_ALIAS = "rp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " shows the report.\n"
            + "Parameters: "
            + PREFIX_DATE + "SPECIFY DATE, MONTH OR YEAR \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "21/03/2019\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "03/2019\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2019\n";

    public static final String MESSAGE_SUCCESS = "Report shown!";

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
