package systemtests.epiggy;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.EpiggyTestApp;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.epiggy.DeleteBudgetCommand;
import seedu.address.model.EPiggy;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Budget;
import seedu.address.testutil.epiggy.TypicalReports;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.CommandBox;
import systemtests.ClockRule;

/**
 * A system test class for EPiggy, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class EPiggySystemTestWithEmptyData {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private static final String WELCOME_MESSAGE =
            "Welcome to ePiggy! Enter a command to get started, "
                    + "or enter 'help' to view all the available commands!";
    private MainWindowHandle mainWindowHandle;
    private EpiggyTestApp testApp;
    private EpiggySystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        EpiggySystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new EpiggySystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialEmptyData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();
        waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect(); // remove this line if displayList removed
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the empty data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected EPiggy getInitialEmptyData() {
        return TypicalReports.getTypicalEPiggyWithEmptyData();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return EpiggyTestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all persons in the address book.
     */
    protected void showAllPersons() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getEPiggy().getPersonList().size(), getModel().getFilteredPersonList().size());
    }

    /**
     * Displays all persons with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPersonsWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredPersonList().size() < getModel().getEPiggy().getPersonList().size());
    }

    /**
     * Deletes all persons in the address book.
     */
    protected void deleteAllPersons() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getEPiggy().getPersonList().size());
    }

    /**
     * Deletes all persons in the address book.
     */
    protected String deleteAllBudgets() {
        String messageHistory = "";
        List<Budget> budgetList = getModel().getBudgetList();
        int budgetListSize = budgetList.size();
        for (int i = 0; i < budgetListSize; i++) {
            executeCommand(DeleteBudgetCommand.COMMAND_WORD + " 1");
            messageHistory = "========================\n" + "ePiggy: " + String.format(DeleteBudgetCommand.MESSAGE_DELETE_BUDGET_SUCCESS,
                    budgetList.get(i)) + "\n\n" + "You: " + DeleteBudgetCommand.COMMAND_WORD + " 1" + "\n"
                    + messageHistory;
        }
        assertEquals(0, getModel().getEPiggy().getBudgetList().size());
        return messageHistory;
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new EPiggy(expectedModel.getEPiggy()), testApp.readStorageEPiggy());
        //        assertListMatching(getPersonListPanel(), expectedModel.getFilteredPersonList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        //        assertFalse(getPersonListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the person in the person list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */

    /**
     * Asserts that the browser's url and the selected card in the person list panel remain unchanged.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        //assertFalse(getPersonListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals(WELCOME_MESSAGE, getResultDisplay().getText());
        //        assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());
        assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}

