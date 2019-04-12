package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;

public class ResultDisplayTest extends GuiUnitTest {

    private ResultDisplay resultDisplay;
    private ResultDisplayHandle resultDisplayHandle;

    @Before
    public void setUp() {
        resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("Welcome to ePiggy! "
                + "Enter a command to get started, or enter 'help' to view all the available commands!",
                resultDisplayHandle.getText());

        // new result received
        guiRobot.interact(() -> resultDisplay.setFeedbackToUser("Dummy feedback to user",
                "Dummy command from user"));
        guiRobot.pauseForHuman();
        assertEquals("========================\nePiggy: Dummy feedback to user\n\n"
                + "You: Dummy command from user\n", resultDisplayHandle.getText());
    }
}
