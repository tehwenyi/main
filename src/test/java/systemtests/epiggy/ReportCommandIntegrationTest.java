package systemtests.epiggy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.epiggy.ReportWindowHandle;
import seedu.address.logic.commands.epiggy.AddAllowanceCommand;
import seedu.address.logic.commands.epiggy.AddBudgetCommand;
import seedu.address.logic.commands.epiggy.AddExpenseCommand;
import seedu.address.logic.commands.epiggy.EditAllowanceCommand;
import seedu.address.logic.commands.epiggy.EditExpenseCommand;
import seedu.address.logic.commands.epiggy.ReportCommand;
import seedu.address.logic.parser.CliSyntax;

public class ReportCommandIntegrationTest extends EPiggySystemTestWithEmptyData {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";
    private static final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openReportWindowAfterAddNewData() {

        String messageHistory;
        String command;
        // add expense 1
        command = AddExpenseCommand.COMMAND_WORD + " n/expense1 $/100 t/breakfast d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add expense 2
        command = AddExpenseCommand.COMMAND_WORD + " n/expense2 $/200 t/breakfast d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add expense 3
        command = AddExpenseCommand.COMMAND_WORD + " n/expense3 $/300 t/dinner d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add expense 4
        command = AddExpenseCommand.COMMAND_WORD + " n/expense4 $/400 t/dinner d/22/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add expense 5
        command = AddExpenseCommand.COMMAND_WORD + " n/expense1 $/100 t/breakfast d/21/03/2018";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add expense 6
        command = AddExpenseCommand.COMMAND_WORD + " n/expense2 $/200 t/breakfast d/21/03/2018";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add expense 7
        command = AddExpenseCommand.COMMAND_WORD + " n/expense3 $/300 t/dinner d/21/03/2018";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add expense 8
        command = AddExpenseCommand.COMMAND_WORD + " n/expense4 $/400 t/dinner d/22/03/2018";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add allowance 1
        command = AddAllowanceCommand.COMMAND_WORD + " n/allowance1 $/500 t/ThanksMum d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add allowance 2
        command = AddAllowanceCommand.COMMAND_WORD + " n/allowance2 $/1000 t/ThanksGod d/22/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add allowance 3
        command = AddAllowanceCommand.COMMAND_WORD + " n/allowance1 $/500 t/ThanksMum d/21/03/2018";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add allowance 4
        command = AddAllowanceCommand.COMMAND_WORD + " n/allowance2 $/1000 t/ThanksGod d/22/03/2018";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add budget 1
        command = AddBudgetCommand.COMMAND_WORD + " $/1000 p/10 d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add budget 2
        command = AddBudgetCommand.COMMAND_WORD + " $/1000 p/10 d/21/03/2018";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add budget 3
        command = AddBudgetCommand.COMMAND_WORD + " $/1000 p/10 d/21/03/2020";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // check report window for specified day
        getMainMenu().cleanTextareaUsingAccelerator();
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "21/03/2019";
        executeCommand(command);
        getMainWindowHandle().focus();
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n";
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified month
        getMainMenu().cleanTextareaUsingAccelerator();
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "03/2019";
        executeCommand(command);
        getMainWindowHandle().focus();
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n";
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified year
        getMainMenu().cleanTextareaUsingAccelerator();
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "2019";
        executeCommand(command);
        getMainWindowHandle().focus();
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n";
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified date
        getMainMenu().cleanTextareaUsingAccelerator();
        command = ReportCommand.COMMAND_WORD;
        executeCommand(command);
        getMainWindowHandle().focus();
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n";
        assertEquals(messageHistory, getResultDisplay().getText());
    }

    @Test
    public void openReportWindowAfterEditingExpense() {

        String messageHistory;
        String command;
        // add expense 1
        command = AddExpenseCommand.COMMAND_WORD + " n/expense1 $/100 t/breakfast d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add expense 2
        command = AddExpenseCommand.COMMAND_WORD + " n/expense2 $/200 t/breakfast d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add expense 3
        command = AddExpenseCommand.COMMAND_WORD + " n/expense3 $/300 t/dinner d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add allowance 1
        command = AddAllowanceCommand.COMMAND_WORD + " n/allowance1 $/500 t/ThanksMum d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add allowance 4
        command = AddAllowanceCommand.COMMAND_WORD + " n/allowance2 $/1000 t/ThanksGod d/22/03/2018";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // add budget 1
        command = AddBudgetCommand.COMMAND_WORD + " $/1000 p/10 d/21/03/2019";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // edit expense
        command = EditExpenseCommand.COMMAND_WORD + " 1 n/expense change 1";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // edit allowance
        command = EditAllowanceCommand.COMMAND_WORD + " 4 n/allowance change 1";
        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();

        // check report window for specified day
        getMainMenu().cleanTextareaUsingAccelerator();
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "21/03/2019";
        executeCommand(command);
        getMainWindowHandle().focus();
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n";
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified month
        getMainMenu().cleanTextareaUsingAccelerator();
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "03/2019";
        executeCommand(command);
        getMainWindowHandle().focus();
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n";
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified year
        getMainMenu().cleanTextareaUsingAccelerator();
        command = ReportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_DATE + "2019";
        executeCommand(command);
        getMainWindowHandle().focus();
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n";
        assertEquals(messageHistory, getResultDisplay().getText());

        // check report window for specified date
        getMainMenu().cleanTextareaUsingAccelerator();
        command = ReportCommand.COMMAND_WORD;
        executeCommand(command);
        getMainWindowHandle().focus();
        assertReportWindowOpen();
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        messageHistory = "========================\n" + "ePiggy: " + ReportCommand.MESSAGE_SUCCESS + "\n\n"
                + "You: " + command + "\n";
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
