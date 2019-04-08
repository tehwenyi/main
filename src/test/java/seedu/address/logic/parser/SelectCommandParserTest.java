package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.testutil.TypicalIndexes;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    @Ignore
    public void parse_validArgs_returnsSelectCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new SelectCommand(TypicalIndexes.INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
