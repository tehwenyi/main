package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.PERIOD_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.PERIOD_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_SECONDEXTRA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBudgets.FIRST_EXTRA;
import static seedu.address.testutil.TypicalBudgets.SECOND_EXTRA;

import org.junit.Test;

import seedu.address.logic.commands.epiggy.AddBudgetCommand;
import seedu.address.model.epiggy.Budget;
import seedu.address.testutil.epiggy.BudgetBuilder;

public class AddBudgetCommandParserTest {
    private AddBudgetCommandParser parser = new AddBudgetCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Budget expectedBudget = new BudgetBuilder(SECOND_EXTRA).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA
                + DATE_DESC_SECONDEXTRA, new AddBudgetCommand(expectedBudget));

        // multiple amounts - last amount accepted
        assertParseSuccess(parser, AMOUNT_DESC_FIRSTEXTRA + AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA
                        + DATE_DESC_SECONDEXTRA, new AddBudgetCommand(expectedBudget));

        // multiple periods - last period accepted
        assertParseSuccess(parser, AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_FIRSTEXTRA + PERIOD_DESC_SECONDEXTRA
                + DATE_DESC_SECONDEXTRA, new AddBudgetCommand(expectedBudget));

        // multiple dates - last date accepted
        assertParseSuccess(parser, AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA + DATE_DESC_FIRSTEXTRA
                + DATE_DESC_SECONDEXTRA, new AddBudgetCommand(expectedBudget));
    }

//    @Test
//    public void parse_optionalFieldsMissing_success() {
//        // zero tags
//        Budget expectedBudget = new BudgetBuilder(AMY).withTags().build();
//        assertParseSuccess(parser, AMOUNT_DESC_FIRSTEXTRA + PERIOD_DESC_FIRSTEXTRA + DATE_DESC_FIRSTEXTRA + ADDRESS_DESC_AMY,
//                new AddBudgetCommand(expectedBudget));
//    }
//
//    @Test
//    public void parse_compulsoryFieldMissing_failure() {
//        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE);
//
//        // missing name prefix
//        assertParseFailure(parser, VALID_AMOUNT_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA,
//                        expectedMessage);
//
//        // missing phone prefix
//        assertParseFailure(parser, AMOUNT_DESC_SECONDEXTRA + VALID_PERIOD_SECONDEXTRA + DATE_DESC_SECONDEXTRA,
//                expectedMessage);
//
//        // missing email prefix
//        assertParseFailure(parser, AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA + VALID_DATE_SECONDEXTRA,
//                expectedMessage);
//
//        // missing address prefix
//        assertParseFailure(parser, AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA,
//                expectedMessage);
//
//        // all prefixes missing
//        assertParseFailure(parser, VALID_AMOUNT_SECONDEXTRA + VALID_PERIOD_SECONDEXTRA + VALID_DATE_SECONDEXTRA,
//                expectedMessage);
//    }
//
//    @Test
//    public void parse_invalidValue_failure() {
//        // invalid name
//        assertParseFailure(parser, INVALID_NAME_DESC + PERIOD_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA + ADDRESS_DESC_BOB
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Cost.MESSAGE_CONSTRAINTS);
//
//        // invalid phone
//        assertParseFailure(parser, AMOUNT_DESC_SECONDEXTRA + INVALID_PHONE_DESC + DATE_DESC_SECONDEXTRA + ADDRESS_DESC_BOB
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);
//
//        // invalid email
//        assertParseFailure(parser, AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);
//
//        // invalid address
//        assertParseFailure(parser, AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA + INVALID_ADDRESS_DESC
//                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);
//
//        // invalid tag
//        assertParseFailure(parser, AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA + ADDRESS_DESC_BOB
//                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);
//
//        // two invalid values, only first invalid value reported
//        assertParseFailure(parser, INVALID_NAME_DESC + PERIOD_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA + INVALID_ADDRESS_DESC,
//                Cost.MESSAGE_CONSTRAINTS);
//
//        // non-empty preamble
//        assertParseFailure(parser, PREAMBLE_NON_EMPTY + AMOUNT_DESC_SECONDEXTRA + PERIOD_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA
//                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
//                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));
//    }
}
