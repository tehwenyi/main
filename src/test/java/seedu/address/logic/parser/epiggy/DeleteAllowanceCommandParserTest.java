package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ALLOWANCE;

import org.junit.Test;

import seedu.address.logic.commands.epiggy.DeleteAllowanceCommand;

public class DeleteAllowanceCommandParserTest {
    private DeleteAllowanceCommandParser parser = new DeleteAllowanceCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAllowanceCommand() {
        assertParseSuccess(parser, "1", new DeleteAllowanceCommand(INDEX_FIRST_ALLOWANCE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAllowanceCommand.MESSAGE_USAGE));
    }
}
