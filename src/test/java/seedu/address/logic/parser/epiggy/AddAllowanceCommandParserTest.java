package seedu.address.logic.parser.epiggy;

import org.junit.Test;
import seedu.address.logic.commands.epiggy.AddAllowanceCommand;
import seedu.address.model.epiggy.Allowance;
import seedu.address.testutil.epiggy.AllowanceBuilder;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALLOWANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SECONDEXTRA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class AddAllowanceCommandParserTest {
    private AddAllowanceCommandParser parser = new AddAllowanceCommandParser();

    @Test
    public void parser_allFieldsPresent_success() {
        Allowance expectedAllowance = new AllowanceBuilder()
                .withName(VALID_NAME_SECONDEXTRA)
                .withCost(VALID_AMOUNT_SECONDEXTRA)
                .withDate(VALID_DATE_SECONDEXTRA)
                .withTags(VALID_TAG_ALLOWANCE, VALID_TAG_SECONDEXTRA)
                .build();



        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_SECONDEXTRA + AMOUNT_DESC_SECONDEXTRA
                + TAG_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA, new AddAllowanceCommand(expectedAllowance));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_FIRSTEXTRA + NAME_DESC_SECONDEXTRA + AMOUNT_DESC_SECONDEXTRA
                + TAG_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA, new AddAllowanceCommand(expectedAllowance));

        // multiple amounts - last amount accepted
        assertParseSuccess(parser, NAME_DESC_SECONDEXTRA + AMOUNT_DESC_FIRSTEXTRA + AMOUNT_DESC_SECONDEXTRA
                + TAG_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA, new AddAllowanceCommand(expectedAllowance));

        // multiple dates - last date accepted
        assertParseSuccess(parser, NAME_DESC_SECONDEXTRA + AMOUNT_DESC_SECONDEXTRA + TAG_DESC_SECONDEXTRA
                + DATE_DESC_FIRSTEXTRA + DATE_DESC_SECONDEXTRA, new AddAllowanceCommand(expectedAllowance));

    }

    @Test
    public void parser_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAllowanceCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser,
                AMOUNT_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA + TAG_DESC_SECONDEXTRA,
                expectedMessage);

        // missing cost
        assertParseFailure(parser,
                NAME_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA + TAG_DESC_SECONDEXTRA,
                expectedMessage);
    }
}