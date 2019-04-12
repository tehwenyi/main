package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.epiggy.AddAllowanceCommand;
import seedu.address.logic.commands.epiggy.AddBudgetCommand;
import seedu.address.logic.commands.epiggy.AddExpenseCommand;
import seedu.address.logic.commands.epiggy.DeleteAllowanceCommand;
import seedu.address.logic.commands.epiggy.DeleteBudgetCommand;
import seedu.address.logic.commands.epiggy.DeleteExpenseCommand;
import seedu.address.logic.commands.epiggy.EditAllowanceCommand;
import seedu.address.logic.commands.epiggy.EditBudgetCommand;
import seedu.address.logic.commands.epiggy.EditExpenseCommand;
import seedu.address.logic.commands.epiggy.FindExpenseCommand;
import seedu.address.logic.commands.epiggy.ReportCommand;
import seedu.address.logic.commands.epiggy.ReverseListCommand;
import seedu.address.logic.commands.epiggy.SetGoalCommand;
import seedu.address.logic.commands.epiggy.SortExpenseCommand;
import seedu.address.logic.parser.epiggy.AddAllowanceCommandParser;
import seedu.address.logic.parser.epiggy.AddBudgetCommandParser;
import seedu.address.logic.parser.epiggy.AddExpenseCommandParser;
import seedu.address.logic.parser.epiggy.DeleteAllowanceCommandParser;
import seedu.address.logic.parser.epiggy.DeleteBudgetCommandParser;
import seedu.address.logic.parser.epiggy.DeleteExpenseCommandParser;
import seedu.address.logic.parser.epiggy.EditAllowanceCommandParser;
import seedu.address.logic.parser.epiggy.EditBudgetCommandParser;
import seedu.address.logic.parser.epiggy.EditExpenseCommandParser;
import seedu.address.logic.parser.epiggy.FindExpenseCommandParser;
import seedu.address.logic.parser.epiggy.ReportCommandParser;
import seedu.address.logic.parser.epiggy.SetGoalCommandParser;
import seedu.address.logic.parser.epiggy.SortExpenseCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class EPiggyParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddExpenseCommand.COMMAND_WORD:
        case AddExpenseCommand.COMMAND_ALIAS:
            return new AddExpenseCommandParser().parse(arguments);

        case EditExpenseCommand.COMMAND_WORD:
        case EditExpenseCommand.COMMAND_ALIAS:
            return new EditExpenseCommandParser().parse(arguments);

        case DeleteExpenseCommand.COMMAND_WORD:
        case DeleteExpenseCommand.COMMAND_ALIAS:
            return new DeleteExpenseCommandParser().parse(arguments);

        case AddAllowanceCommand.COMMAND_WORD:
        case AddAllowanceCommand.COMMAND_ALIAS:
            return new AddAllowanceCommandParser().parse(arguments);

        case EditAllowanceCommand.COMMAND_WORD:
        case EditAllowanceCommand.COMMAND_ALIAS:
            return new EditAllowanceCommandParser().parse(arguments);

        case DeleteAllowanceCommand.COMMAND_WORD:
        case DeleteAllowanceCommand.COMMAND_ALIAS:
            return new DeleteAllowanceCommandParser().parse(arguments);

        case FindExpenseCommand.COMMAND_WORD:
        case FindExpenseCommand.COMMAND_ALIAS:
            return new FindExpenseCommandParser().parse(arguments);

        case SortExpenseCommand.COMMAND_WORD:
        case SortExpenseCommand.COMMAND_ALIAS:
            return new SortExpenseCommandParser().parse(arguments);

        case ReverseListCommand.COMMAND_WORD:
        case ReverseListCommand.COMMAND_ALIAS:
            return new ReverseListCommand();

        case ClearCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case ListCommand.COMMAND_WORD:
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_ALIAS:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_ALIAS:
            return new HelpCommand();

        case AddBudgetCommand.COMMAND_WORD:
        case AddBudgetCommand.COMMAND_ALIAS:
            return new AddBudgetCommandParser().parse(arguments);

        case EditBudgetCommand.COMMAND_WORD:
        case EditBudgetCommand.COMMAND_ALIAS:
            return new EditBudgetCommandParser().parse(arguments);

        case DeleteBudgetCommand.COMMAND_WORD:
        case DeleteBudgetCommand.COMMAND_ALIAS:
            return new DeleteBudgetCommandParser().parse(arguments);

        case SetGoalCommand.COMMAND_WORD:
        case SetGoalCommand.COMMAND_ALIAS:
            return new SetGoalCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case ReportCommand.COMMAND_WORD:
        case ReportCommand.COMMAND_ALIAS:
            if (arguments.equals("")) {
                return new ReportCommandParser().parse("");
            } else {
                return new ReportCommandParser().parse(arguments);
            }

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
