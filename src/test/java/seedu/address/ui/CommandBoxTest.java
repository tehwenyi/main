package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CommandBoxHandle;
import javafx.scene.input.KeyCode;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;

public class CommandBoxTest extends GuiUnitTest {

    private static final String COMMAND_THAT_SUCCEEDS = ListCommand.COMMAND_WORD;
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;
    private final ArrayList<String> history = new ArrayList<>();

    private CommandBoxHandle commandBoxHandle;

    @Before
    public void setUp() {
        CommandBox commandBox = new CommandBox(commandText -> {
            history.add(commandText);
            if (commandText.equals(COMMAND_THAT_SUCCEEDS)) {
                return new CommandResult("Command successful");
            }
            throw new CommandException("Command failed");
        }, history);
        commandBoxHandle = new CommandBoxHandle(getChildNode(commandBox.getRoot(),
                CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfCommandBox = new ArrayList<>(commandBoxHandle.getStyleClass());

        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_handleKeyPress() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    @Test
    public void handleKeyPress_startingWithUp() {
        // empty history
        assertInputHistory(KeyCode.UP, "");
        assertInputHistory(KeyCode.DOWN, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");

        // two commands (latest command is failure)
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.UP, thirdCommand);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
    }

    @Test
    public void handleKeyPress_startingWithDown() {
        // empty history
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, "");

        // one command
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);

        // two commands
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        commandBoxHandle.run(thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, thirdCommand);
    }

    @Test
    public void handleKeyPress_tab() {

        // test for more than 1 matched commands
        commandBoxHandle.run("a");
        assertInputHistory(KeyCode.TAB, "addAllowance n/ $/ t/ d/");
        commandBoxHandle.run("a");
        assertInputHistory(KeyCode.TAB, "addBudget $/ p/ d/");
        commandBoxHandle.run("a");
        assertInputHistory(KeyCode.TAB, "addExpense n/ $/ t/ d/");

        // test autocomplete with all valid commands
        commandBoxHandle.run("adda");
        assertInputHistory(KeyCode.TAB, "addAllowance n/ $/ t/ d/");

        commandBoxHandle.run("addb");
        assertInputHistory(KeyCode.TAB, "addBudget $/ p/ d/");

        commandBoxHandle.run("adde");
        assertInputHistory(KeyCode.TAB, "addExpense n/ $/ t/ d/");

        commandBoxHandle.run("c");
        assertInputHistory(KeyCode.TAB, "clear");

        commandBoxHandle.run("deletea");
        assertInputHistory(KeyCode.TAB, "deleteAllowance ");

        commandBoxHandle.run("deleteb");
        assertInputHistory(KeyCode.TAB, "deleteBudget ");

        commandBoxHandle.run("deletee");
        assertInputHistory(KeyCode.TAB, "deleteExpense ");

        commandBoxHandle.run("edita");
        assertInputHistory(KeyCode.TAB, "editAllowance  n/ $/ d/ t/");

        commandBoxHandle.run("ex");
        assertInputHistory(KeyCode.TAB, "exit");

        commandBoxHandle.run("f");
        assertInputHistory(KeyCode.TAB, "findExpense n/ t/ d/ $/");

        commandBoxHandle.run("h");
        assertInputHistory(KeyCode.TAB, "help");

        commandBoxHandle.run("l");
        assertInputHistory(KeyCode.TAB, "list");

        commandBoxHandle.run("red");
        assertInputHistory(KeyCode.TAB, "redo");

        commandBoxHandle.run("rep");
        assertInputHistory(KeyCode.TAB, "report d/");

        commandBoxHandle.run("rev");
        assertInputHistory(KeyCode.TAB, "reverseList");

        commandBoxHandle.run("rev");
        assertInputHistory(KeyCode.TAB, "reverseList");

        commandBoxHandle.run("se");
        assertInputHistory(KeyCode.TAB, "setGoal n/ $/");

        commandBoxHandle.run("so");
        assertInputHistory(KeyCode.TAB, "sortExpense n/ d/ $/");

        commandBoxHandle.run("u");
        assertInputHistory(KeyCode.TAB, "undo");

        // test for empty string
        commandBoxHandle.run("");
        assertInputHistory(KeyCode.TAB, "");

        // test for keyword at the end of user input
        commandBoxHandle.run("hello adde");
        assertInputHistory(KeyCode.TAB, "hello addExpense n/ $/ t/ d/");

        // test for keyword at the beginning of user input
        commandBoxHandle.run("adde hello");
        assertInputHistory(KeyCode.TAB, "adde hello");

        // test for keyword at the middle of user input
        commandBoxHandle.run("hello adde hello");
        assertInputHistory(KeyCode.TAB, "hello adde hello");
    }

    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, commandBoxHandle.getInput());
    }
}
