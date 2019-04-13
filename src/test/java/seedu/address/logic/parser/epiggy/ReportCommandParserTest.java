package seedu.address.logic.parser.epiggy;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.time.LocalDate;

import org.junit.Test;

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

    private static final String USER_INPUT_DAY = " d/21/03/2019";
    private static final String USER_INPUT_MONTH = " d/03/2019";
    private static final String USER_INPUT_YEAR = " d/2019";
    private ReportCommandParser parser = new ReportCommandParser();
    private LocalDate date = LocalDate.of(2019, 03, 21);

    @Test
    public void parse_validArgs_returnReportCommandWithCorrectDateAndType() {
        try {
            ReportCommand command = parser.parse(USER_INPUT_DAY);
            assertTrue(command.getDate().equals(date));
            assertTrue(command.getType().equals("DAY"));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
    @Test
    public void parse_validEmptyArgs_returnReportCommandWithCorrectAllType() {
        try {
            ReportCommand command = parser.parse("");
            assertTrue(command.getType().equals("ALL"));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
    @Test
    public void parse_validArgs_returnReportCommandWithMonthType() {
        try {
            ReportCommand command = parser.parse(USER_INPUT_MONTH);
            assertTrue(command.getType().equals("MONTH"));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
    @Test
    public void parse_validArgs_returnReportCommandWithYearType() {
        try {
            ReportCommand command = parser.parse(USER_INPUT_YEAR);
            assertTrue(command.getType().equals("YEAR"));
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
