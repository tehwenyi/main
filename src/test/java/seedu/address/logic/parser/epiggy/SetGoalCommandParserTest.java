package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_IPHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.epiggy.SetGoalCommand;
import seedu.address.model.epiggy.Goal;
import seedu.address.testutil.epiggy.GoalBuilder;

//@@author kev-inc

public class SetGoalCommandParserTest {
    private SetGoalCommandParser parser = new SetGoalCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Goal expectedGoal = new GoalBuilder()
                .withName(VALID_NAME_IPHONE)
                .withCost(VALID_COST_IPHONE).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_IPHONE + COST_DESC_IPHONE,
                new SetGoalCommand(expectedGoal));

        // multiple names - last accepted
        assertParseSuccess(parser,
                NAME_DESC_FIRSTEXTRA + NAME_DESC_IPHONE + COST_DESC_IPHONE,
                new SetGoalCommand(expectedGoal));

        // multiple costs - last accepted
        assertParseSuccess(parser,
                NAME_DESC_IPHONE + AMOUNT_DESC_FIRSTEXTRA + COST_DESC_IPHONE,
                new SetGoalCommand(expectedGoal));
    }

    @Test
    public void parseCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SetGoalCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, COST_DESC_IPHONE, expectedMessage);

        // missing cost prefix
        assertParseFailure(parser, NAME_DESC_IPHONE, expectedMessage);
    }
}
