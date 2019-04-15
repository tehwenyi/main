package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.epiggy.DeleteAllowanceCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author kev-inc

/**
 * Parses input arguments and creates a new DeleteAllowanceCommand object
 */
public class DeleteAllowanceCommandParser implements Parser<DeleteAllowanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAllowanceCommand
     * and returns an DeleteAllowanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteAllowanceCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteAllowanceCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAllowanceCommand.MESSAGE_USAGE), pe);
        }
    }
}
