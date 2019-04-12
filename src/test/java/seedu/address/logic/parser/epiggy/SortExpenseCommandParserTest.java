package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.epiggy.SortExpenseCommand;
import seedu.address.model.epiggy.comparators.CompareExpenseByCost;
import seedu.address.model.epiggy.comparators.CompareExpenseByDate;
import seedu.address.model.epiggy.comparators.CompareExpenseByName;

public class SortExpenseCommandParserTest {

    private SortExpenseCommandParser parser = new SortExpenseCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortExpenseCommand() {
        assertParseSuccess(parser, " " + PREFIX_NAME.toString(),
                new SortExpenseCommand(new CompareExpenseByName()));
        assertParseSuccess(parser, " " + PREFIX_COST.toString(),
                new SortExpenseCommand(new CompareExpenseByCost()));
        assertParseSuccess(parser, " " + PREFIX_DATE.toString(),
                new SortExpenseCommand(new CompareExpenseByDate()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " -n",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpenseCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " /d ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpenseCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "/c/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortExpenseCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "/c/n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortExpenseCommand.MESSAGE_USAGE));
    }
}
