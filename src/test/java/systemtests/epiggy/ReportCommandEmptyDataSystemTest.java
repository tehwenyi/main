package systemtests.epiggy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.epiggy.ReportWindowHandle;
import seedu.address.logic.commands.epiggy.ReportCommand;
import seedu.address.logic.parser.CliSyntax;

public class ReportCommandEmptyDataSystemTest extends EPiggySystemTestWithEmptyData {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";
    private static final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openReportWindow() {

        String messageHistory = "";
        String command = "";

        //use menu button to open report window
        // TODO: this test case may fail sometime. Comment it if it happens.
        //getMainMenu().openReportWindowUsingMenu();
        //assertReportWindowOpen(); // close window if report window open

        //use command box
        executeCommand(ReportCommand.COMMAND_WORD);
        assertReportWindowOpen();

        // open report window and give it focus
        executeCommand(ReportCommand.COMMAND_WORD);
        getMainWindowHandle().focus();

        // check report window for specified day
        //TODO: some Java internal error here. Remove this test case will make the error.
        executeCommand(ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "21/03/2019");
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + ReportCommand.COMMAND_WORD + "\n\n";
        messageHistory = "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + ReportCommand.COMMAND_WORD + "\n\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified month
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "03/2019";
        executeCommand(command);
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified year
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "2019";
        executeCommand(command);
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified date
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "21/03/2019";
        executeCommand(command);
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n\n" + messageHistory;
        assertEquals(messageHistory, getResultDisplay().getText());
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
