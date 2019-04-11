package systemtests.epiggy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.epiggy.ReportWindowHandle;
import seedu.address.logic.commands.epiggy.ReportCommand;
import seedu.address.logic.parser.CliSyntax;

public class ReportCommandSystemTest extends EPiggySystemTestWithDefaultData {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";
    private static final GuiRobot guiRobot = new GuiRobot();
    private static final String INVALID_DATE_WORD = "invalid";
    private static final String INVALID_DATE_LARGE_DAY = "99/03/2019";
    private static final String INVALID_DATE_LARGE_MONTH = "21/99/2019";
    private static final String INVALID_DATE_LARGE_YEAR = "21/03/999999999999999";
    private static final String INVALID_DATE_NEGATIVE_DAY = "-21/99/2019";
    private static final String INVALID_DATE_NEGATIVE_MONTH = "21/-03/2019";
    //private static final String INVALID_DATE_NEGATIVE_YEAR = "21/03/-2019";

    private static final String INVALID_MONTH_LARGE_MONTH = "99/2019";
    private static final String INVALID_MONTH_LARGE_YEAR = "99/999999999999999999";
    private static final String INVALID_MONTH_NEGATIVE_MONTH = "-99/2019";
    //private static final String INVALID_MONTH_NEGATIVE_YEAR = "03/-2019";

    private static final String INVALID_YEAR_LARGE_YEAR = "999999999999";
    //private static final String INVALID_YEAR_NEGATIVE_YEAR = "-2019";

    @Test
    public void openReportWindow() {

        String messageHistory = "";
        String command = "";

        //use menu button
        //TODO: this test case may fail sometime, comment it if it happens.
        getMainMenu().openReportWindowUsingMenu();
        assertReportWindowOpen(); // close window if report window open
        getMainWindowHandle().focus();

        //use command box
        executeCommand(ReportCommand.COMMAND_WORD);
        assertReportWindowOpen();
        getMainWindowHandle().focus();

        // open report window and give it focus
        executeCommand(ReportCommand.COMMAND_WORD);
        getMainWindowHandle().focus();

        // check report window for specified day
        //TODO: some Java internal error here. Remove this test case will make the error.
        executeCommand(ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "21/03/2019");
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n" + "You: "
                + ReportCommand.COMMAND_WORD + "\n" + messageHistory;
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n" + "You: "
                + ReportCommand.COMMAND_WORD + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified month
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "03/2019";
        executeCommand(command);
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified year
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "2019";
        executeCommand(command);
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified date
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "21/03/2019";
        executeCommand(command);
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_DATE_WORD;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_DATE_LARGE_DAY;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_DATE_LARGE_MONTH;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_DATE_LARGE_YEAR;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_DATE_NEGATIVE_DAY;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_DATE_NEGATIVE_MONTH;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        // check report window for invalid date format
        /*
        executeCommand(ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_DATE_NEGATIVE_YEAR);
        assertEquals(MESSAGE_INVALID_DATE, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();
        */

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_MONTH_LARGE_MONTH;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_MONTH_LARGE_YEAR;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_MONTH_NEGATIVE_MONTH;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        /*
        // check report window for invalid date format
        executeCommand(ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_MONTH_NEGATIVE_YEAR);
        assertEquals(MESSAGE_INVALID_DATE, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();
        */

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_YEAR_LARGE_YEAR;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        /*
        // check report window for invalid date format
        executeCommand(ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_YEAR_NEGATIVE_YEAR);
        assertEquals(MESSAGE_INVALID_DATE, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();
        */

        // check report window for invalid date format
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + INVALID_MONTH_LARGE_MONTH;
        executeCommand(command);
        messageHistory = "========================\n" + "ePiggy: " + MESSAGE_INVALID_DATE + "\n\n"
                + "You: " + command + "\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
        assertCommandBoxShowsErrorStyle();

        // check report window for invalid command
        command = ReportCommand.COMMAND_WORD + " invalid";
        executeCommand(command);
        String s1 = "========================\n" + "ePiggy: Invalid command format! \n" + ReportCommand.MESSAGE_USAGE
                + "\n\n" + "You: " + command + "\n" + messageHistory;
        String s2 = getResultDisplay().getText();
        assertEquals(s1, s2);
        assertCommandBoxShowsErrorStyle();
    }

    /**
     * Asserts that the report window is open, and closes it after checking.
     */
    private void assertReportWindowOpen() {
        assertTrue(ERROR_MESSAGE, ReportWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new ReportWindowHandle(guiRobot.getStage(ReportWindowHandle.REPORT_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the report window isn't open.
     */
    private void assertReportWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, ReportWindowHandle.isWindowPresent());
    }

}
