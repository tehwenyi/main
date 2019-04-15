package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SECONDEXTRA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ALLOWANCE;

import org.junit.Test;

import seedu.address.logic.commands.epiggy.EditAllowanceCommand;
import seedu.address.logic.commands.epiggy.EditAllowanceCommand.EditAllowanceDescriptor;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Name;
import seedu.address.testutil.epiggy.EditAllowanceDescriptorBuilder;

//@@author kev-inc

public class EditAllowanceCommandParserTest {
    private EditAllowanceCommandParser parser = new EditAllowanceCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "1", EditAllowanceCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments
        assertParseFailure(parser, "random string", EditAllowanceCommand.MESSAGE_NOT_EDITED);
        // invalid prefix
        assertParseFailure(parser, "i/fake string", EditAllowanceCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        // invalid cost
        assertParseFailure(parser, "1" + INVALID_AMOUNT_DESC, Cost.MESSAGE_CONSTRAINTS);
        // invalid date
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = "1" + NAME_DESC_SECONDEXTRA + AMOUNT_DESC_SECONDEXTRA
                + DATE_DESC_SECONDEXTRA + TAG_DESC_SECONDEXTRA;

        EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withName(VALID_NAME_SECONDEXTRA)
                .withCost(VALID_AMOUNT_SECONDEXTRA)
                .withDate(VALID_DATE_SECONDEXTRA)
                .withTags(VALID_TAG_SECONDEXTRA).build();
        EditAllowanceCommand expectedCommand = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = "1" + NAME_DESC_SECONDEXTRA + AMOUNT_DESC_SECONDEXTRA;
        EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withName(VALID_NAME_SECONDEXTRA).withCost(VALID_AMOUNT_SECONDEXTRA).build();
        EditAllowanceCommand expectedCommand = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = "1" + NAME_DESC_SECONDEXTRA;
        EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withName(VALID_NAME_SECONDEXTRA).build();
        EditAllowanceCommand expectedCommand = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // amount
        userInput = "1" + AMOUNT_DESC_SECONDEXTRA;
        descriptor = new EditAllowanceDescriptorBuilder()
                .withCost(VALID_AMOUNT_SECONDEXTRA).build();
        expectedCommand = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = "1" + DATE_DESC_SECONDEXTRA;
        descriptor = new EditAllowanceDescriptorBuilder()
                .withDate(VALID_DATE_SECONDEXTRA).build();
        expectedCommand = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = "1" + TAG_DESC_SECONDEXTRA;
        descriptor = new EditAllowanceDescriptorBuilder()
                .withTags(VALID_TAG_SECONDEXTRA).build();
        expectedCommand = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = "1" + NAME_DESC_FIRSTEXTRA + AMOUNT_DESC_FIRSTEXTRA + DATE_DESC_FIRSTEXTRA
                + NAME_DESC_SECONDEXTRA + AMOUNT_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA;
        EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withName(VALID_NAME_SECONDEXTRA).withCost(VALID_AMOUNT_SECONDEXTRA)
                .withDate(VALID_DATE_SECONDEXTRA).build();
        EditAllowanceCommand expectedCommand = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = "1" + INVALID_NAME_DESC + NAME_DESC_SECONDEXTRA;
        EditAllowanceDescriptor descriptor = new EditAllowanceDescriptorBuilder()
                .withName(VALID_NAME_SECONDEXTRA).build();
        EditAllowanceCommand expectedCommand = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = "1" + AMOUNT_DESC_SECONDEXTRA + INVALID_NAME_DESC + NAME_DESC_SECONDEXTRA;
        descriptor = new EditAllowanceDescriptorBuilder().withName(VALID_NAME_SECONDEXTRA)
                .withCost(VALID_AMOUNT_SECONDEXTRA).build();
        expectedCommand = new EditAllowanceCommand(INDEX_FIRST_ALLOWANCE, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
