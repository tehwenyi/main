package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.testutil.TypicalBudgets.SECOND_EXTRA;

import org.junit.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.epiggy.AddBudgetCommand;
import seedu.address.logic.parser.CommandParserTestUtil;
import seedu.address.model.expense.Budget;
import seedu.address.model.expense.item.Cost;
import seedu.address.model.expense.item.Period;
import seedu.address.testutil.epiggy.BudgetBuilder;

public class AddBudgetCommandParserTest {
    private AddBudgetCommandParser parser = new AddBudgetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Budget expectedBudget = new BudgetBuilder(SECOND_EXTRA).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE
                + CommandTestUtil.AMOUNT_DESC_SECONDEXTRA + CommandTestUtil.PERIOD_DESC_SECONDEXTRA
                + CommandTestUtil.DATE_DESC_SECONDEXTRA, new AddBudgetCommand(expectedBudget));

        // multiple amounts - last amount accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.AMOUNT_DESC_FIRSTEXTRA
                + CommandTestUtil.AMOUNT_DESC_SECONDEXTRA + CommandTestUtil.PERIOD_DESC_SECONDEXTRA
                + CommandTestUtil.DATE_DESC_SECONDEXTRA, new AddBudgetCommand(expectedBudget));

        // multiple periods - last period accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.AMOUNT_DESC_SECONDEXTRA
                + CommandTestUtil.PERIOD_DESC_FIRSTEXTRA + CommandTestUtil.PERIOD_DESC_SECONDEXTRA
                + CommandTestUtil.DATE_DESC_SECONDEXTRA, new AddBudgetCommand(expectedBudget));

        // multiple dates - last date accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.AMOUNT_DESC_SECONDEXTRA
                + CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.DATE_DESC_FIRSTEXTRA
                + CommandTestUtil.DATE_DESC_SECONDEXTRA, new AddBudgetCommand(expectedBudget));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE);

        // missing amount prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_AMOUNT_SECONDEXTRA
                        + CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.DATE_DESC_SECONDEXTRA,
                expectedMessage);

        // missing period prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.AMOUNT_DESC_SECONDEXTRA
                        + CommandTestUtil.VALID_PERIOD_SECONDEXTRA + CommandTestUtil.DATE_DESC_SECONDEXTRA,
                expectedMessage);

        // missing date prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.AMOUNT_DESC_SECONDEXTRA
                        + CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.VALID_DATE_SECONDEXTRA,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_AMOUNT_SECONDEXTRA
                        + CommandTestUtil.VALID_PERIOD_SECONDEXTRA + CommandTestUtil.VALID_DATE_SECONDEXTRA,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid cost
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_AMOUNT_DESC
                        + CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.DATE_DESC_SECONDEXTRA,
                Cost.MESSAGE_CONSTRAINTS);

        // invalid period
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.AMOUNT_DESC_SECONDEXTRA
                        + CommandTestUtil.INVALID_PERIOD_DESC + CommandTestUtil.DATE_DESC_SECONDEXTRA,
                Period.MESSAGE_CONSTRAINTS);

        // invalid date
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.AMOUNT_DESC_SECONDEXTRA
                        + CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.INVALID_DATE_DESC,
                MESSAGE_INVALID_DATE);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_AMOUNT_DESC
                        + CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.INVALID_DATE_DESC,
                Cost.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY
                        + CommandTestUtil.AMOUNT_DESC_SECONDEXTRA + CommandTestUtil.PERIOD_DESC_SECONDEXTRA
                        + CommandTestUtil.DATE_DESC_SECONDEXTRA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));
    }
}
