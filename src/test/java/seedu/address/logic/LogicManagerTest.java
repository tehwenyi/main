package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EXPENSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SECONDEXTRA;
import static seedu.address.logic.commands.HistoryCommand.MESSAGE_NO_HISTORY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.epiggy.AddExpenseCommand;
import seedu.address.logic.commands.epiggy.DeleteExpenseCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.UserPrefs;
import seedu.address.model.epiggy.Expense;
import seedu.address.storage.JsonEPiggyStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.epiggy.ExpensesBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Model model = new ModelManager();
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        JsonEPiggyStorage addressBookStorage = new JsonEPiggyStorage(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
        assertHistoryCorrect(invalidCommand);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deleteExpense 9";
        assertCommandException(deleteCommand, DeleteExpenseCommand.MESSAGE_INDEX_OUT_OF_BOUNDS);
        assertHistoryCorrect(deleteCommand);
    }

    @Test
    public void execute_validCommand_success() {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listCommand);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() throws Exception {
        // Setup LogicManager with JsonEPiggyIoExceptionThrowingStub
        JsonEPiggyStorage addressBookStorage =
                new JsonEPiggyIoExceptionThrowingStub(temporaryFolder.newFile().toPath());
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.newFile().toPath());
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add expense
        String addExpenseCommand = AddExpenseCommand.COMMAND_WORD + NAME_DESC_SECONDEXTRA + AMOUNT_DESC_SECONDEXTRA
                + TAG_DESC_SECONDEXTRA + DATE_DESC_SECONDEXTRA;
        Expense expectedExpense = new ExpensesBuilder().withName(VALID_NAME_SECONDEXTRA)
                .withCost(VALID_AMOUNT_SECONDEXTRA)
                .withTags(VALID_TAG_SECONDEXTRA, VALID_TAG_EXPENSE)
                .withDate(VALID_DATE_SECONDEXTRA).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addExpense(expectedExpense);
        expectedModel.commitEPiggy();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandBehavior(CommandException.class, addExpenseCommand, expectedMessage, expectedModel);
        assertHistoryCorrect(addExpenseCommand);

    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getFilteredPersonList().remove(0);
    }

    @Test
    public void execute_emptyCommand_throwsParseException() throws Exception {
        String invalidCommand = "";
        assertParseException(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
        assertEquals(result.getFeedbackToUser(), MESSAGE_NO_HISTORY);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getEPiggy(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s address book was saved to the storage file.
     */
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
    }

    /**
     * Asserts that the result display shows all the {@code expectedCommands} upon the execution of
     * {@code HistoryCommand}.
     */
    private void assertHistoryCorrect(String... expectedCommands) {
        try {
            CommandResult result = logic.execute(HistoryCommand.COMMAND_WORD);
            String expectedMessage = String.format(
                    HistoryCommand.MESSAGE_SUCCESS, String.join("\n", expectedCommands));
            assertEquals(expectedMessage, result.getFeedbackToUser());
        } catch (ParseException | CommandException e) {
            throw new AssertionError("Parsing and execution of HistoryCommand.COMMAND_WORD should succeed.", e);
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonEPiggyIoExceptionThrowingStub extends JsonEPiggyStorage {
        private JsonEPiggyIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveEPiggy(ReadOnlyEPiggy ePiggy, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
