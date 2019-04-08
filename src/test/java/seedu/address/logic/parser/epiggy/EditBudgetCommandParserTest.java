package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;

import org.junit.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.epiggy.EditBudgetCommand;
import seedu.address.logic.parser.CommandParserTestUtil;
import seedu.address.model.expense.item.Cost;
import seedu.address.testutil.epiggy.EditBudgetDetailsBuilder;
import seedu.address.model.expense.item.Period;

public class EditBudgetCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBudgetCommand.MESSAGE_USAGE);

    private EditBudgetCommandParser parser = new EditBudgetCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        CommandParserTestUtil.assertParseFailure(parser, "1", EditBudgetCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "some random string", EditBudgetCommand.MESSAGE_NOT_EDITED);

        // invalid prefix being parsed as preamble
        CommandParserTestUtil.assertParseFailure(parser, "i/ string", EditBudgetCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidValue_failure() {
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_AMOUNT_DESC, Cost.MESSAGE_CONSTRAINTS); // invalid amount
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_PERIOD_DESC, Period.MESSAGE_CONSTRAINTS); // invalid period
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_DATE_DESC, MESSAGE_INVALID_DATE); // invalid date

        // invalid period followed by valid date
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_PERIOD_DESC + CommandTestUtil.DATE_DESC_FIRSTEXTRA, Period.MESSAGE_CONSTRAINTS);

        // valid period followed by invalid period. The test case for invalid period followed by valid period
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.INVALID_PERIOD_DESC, Period.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        CommandParserTestUtil.assertParseFailure(parser, "1" + CommandTestUtil.INVALID_AMOUNT_DESC + CommandTestUtil.INVALID_DATE_DESC + CommandTestUtil.VALID_PERIOD_FIRSTEXTRA,
                Cost.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.DATE_DESC_FIRSTEXTRA
                + CommandTestUtil.AMOUNT_DESC_FIRSTEXTRA;

        EditBudgetCommand.EditBudgetDetails details = new EditBudgetDetailsBuilder().withAmount(CommandTestUtil.VALID_AMOUNT_FIRSTEXTRA)
                .withPeriod(CommandTestUtil.VALID_PERIOD_SECONDEXTRA).withDate(CommandTestUtil.VALID_DATE_FIRSTEXTRA).build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(details);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.DATE_DESC_FIRSTEXTRA;

        EditBudgetCommand.EditBudgetDetails details = new EditBudgetDetailsBuilder()
                .withPeriod(CommandTestUtil.VALID_PERIOD_SECONDEXTRA).withDate(CommandTestUtil.VALID_DATE_FIRSTEXTRA).build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(details);

        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // amount
        String userInput = CommandTestUtil.AMOUNT_DESC_FIRSTEXTRA;
        EditBudgetCommand.EditBudgetDetails details = new EditBudgetDetailsBuilder().withAmount(CommandTestUtil.VALID_AMOUNT_FIRSTEXTRA).build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(details);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // period
        userInput = CommandTestUtil.PERIOD_DESC_FIRSTEXTRA;
        details = new EditBudgetDetailsBuilder().withPeriod(CommandTestUtil.VALID_PERIOD_FIRSTEXTRA).build();
        expectedCommand = new EditBudgetCommand(details);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = CommandTestUtil.DATE_DESC_FIRSTEXTRA;
        details = new EditBudgetDetailsBuilder().withDate(CommandTestUtil.VALID_DATE_FIRSTEXTRA).build();
        expectedCommand = new EditBudgetCommand(details);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = CommandTestUtil.PERIOD_DESC_FIRSTEXTRA + CommandTestUtil.DATE_DESC_FIRSTEXTRA + CommandTestUtil.PERIOD_DESC_FIRSTEXTRA + CommandTestUtil.DATE_DESC_FIRSTEXTRA
                + CommandTestUtil.PERIOD_DESC_SECONDEXTRA + CommandTestUtil.DATE_DESC_SECONDEXTRA;

        EditBudgetCommand.EditBudgetDetails details = new EditBudgetDetailsBuilder().withPeriod(CommandTestUtil.VALID_PERIOD_SECONDEXTRA)
                .withDate(CommandTestUtil.VALID_DATE_SECONDEXTRA).build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(details);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = CommandTestUtil.INVALID_PERIOD_DESC + CommandTestUtil.PERIOD_DESC_SECONDEXTRA;
        EditBudgetCommand.EditBudgetDetails details = new EditBudgetDetailsBuilder().withPeriod(CommandTestUtil.VALID_PERIOD_SECONDEXTRA).build();
        EditBudgetCommand expectedCommand = new EditBudgetCommand(details);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = CommandTestUtil.DATE_DESC_SECONDEXTRA + CommandTestUtil.INVALID_PERIOD_DESC + CommandTestUtil.PERIOD_DESC_SECONDEXTRA;
        details = new EditBudgetDetailsBuilder().withPeriod(CommandTestUtil.VALID_PERIOD_SECONDEXTRA)
                .withDate(CommandTestUtil.VALID_DATE_SECONDEXTRA).build();
        expectedCommand = new EditBudgetCommand(details);
        CommandParserTestUtil.assertParseSuccess(parser, userInput, expectedCommand);
    }
}
