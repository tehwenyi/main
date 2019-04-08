package seedu.address.logic.parser.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.epiggy.SortExpenseCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author rahulb99
/**
 * Parses input arguments and creates a new SortExpenseCommand object.
 */
public class SortExpenseCommandParser implements Parser<SortExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortExpenseCommand
     * and returns an SortExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpenseCommand.MESSAGE_USAGE));
        }

        //Check whether the user follow the pattern
        if (!trimmedArgs.contains("/")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpenseCommand.MESSAGE_USAGE));
        }

        String[] splitTrimmedArgs = trimmedArgs.split("/");
        if (splitTrimmedArgs[0].equals("")) {
            //Ensure args contains at least one prefix
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpenseCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap keywordsMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COST, PREFIX_DATE);

        return new SortExpenseCommand(keywordsMap);
    }

}
