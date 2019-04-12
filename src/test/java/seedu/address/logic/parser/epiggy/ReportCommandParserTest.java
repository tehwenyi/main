package seedu.address.logic.parser.epiggy;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.time.LocalDate;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.epiggy.ReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteBudgetCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteBudgetCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ReportCommandParserTest {

    private static final String USER_INPUT = " d/21/03/2019";
    private ReportCommandParser parser = new ReportCommandParser();
    private LocalDate date = LocalDate.of(2019, 03, 21);

    @Test
    public void parse_validArgs_returnReportCommand() {
        try {
            Command command = parser.parse(USER_INPUT);
            assertTrue(command instanceof ReportCommand);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
    @Test
    public void parse_validEmptyArgs_returnReportCommand() {
        try {
            Command command = parser.parse("");
            assertTrue(command instanceof ReportCommand);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ReportCommand.MESSAGE_USAGE));
    }
}
