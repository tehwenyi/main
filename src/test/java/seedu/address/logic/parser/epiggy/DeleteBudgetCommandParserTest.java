package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.epiggy.DeleteBudgetCommand;
import seedu.address.logic.parser.CommandParserTestUtil;
import seedu.address.testutil.TypicalIndexes;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteBudgetCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteBudgetCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteBudgetCommandParserTest {

    private DeleteBudgetCommandParser parser = new DeleteBudgetCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new DeleteBudgetCommand(TypicalIndexes.INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteBudgetCommand.MESSAGE_USAGE));
    }
}
