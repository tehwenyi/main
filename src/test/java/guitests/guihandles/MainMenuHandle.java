package guitests.guihandles;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends NodeHandle<Node> {
    public static final String MENU_BAR_ID = "#menuBar";

    public MainMenuHandle(Node mainMenuNode) {
        super(mainMenuNode);
    }

    /**
     * Opens the {@code HelpWindow} using the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingMenu() {
        clickOnMenuItemsSequentially("How To Use", "User Manual");
    }

    /**
     * Opens the {@code ReportWindow} using the menu bar in {@code MainWindow}.
     */
    public void openReportWindowUsingMenu() {
        clickOnMenuItemsSequentially("Report", "Completed Report");
    }

    /**
     * cleans up the {@code Textarea} using the menu bar in {@code MainWindow}.
     */
    public void cleanUpTextareaUsingMenu() {
        clickOnMenuItemsSequentially("Clean", "Clean TextArea");
    }

    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F1);
    }

    /**
     * Cleans up the {@code Textarea} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void cleanTextareaUsingAccelerator() {
        guiRobot.push(KeyCode.F2);
    }

    /**
     * Clicks on {@code menuItems} in order.
     */
    private void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(guiRobot::clickOn);
    }
}
