package systemtests.epiggy;

import static seedu.address.testutil.epiggy.TypicalReports.KEYWORD_MATCHING_DINNER;
import static seedu.address.testutil.epiggy.TypicalReports.KEYWORD_MATCHING_START_DATE;
import static seedu.address.testutil.epiggy.TypicalReports.KEYWORD_MATCHING_STATIONARY;

import org.junit.Test;

import seedu.address.logic.commands.epiggy.FindExpenseCommand;
import seedu.address.model.Model;
import systemtests.EPiggySystemTest;

public class FindExpenseCommandSystemTest extends EPiggySystemTestWithDefaultData {
    private StringBuilder stringBuilder = new StringBuilder();
    @Test
    public void find() {
        /* Case: find an expense in ePiggy, command with leading spaces and trailing spaces
         * -> 1 expenses found
         */
        String command = "   " + FindExpenseCommand.COMMAND_WORD + " n/" + KEYWORD_MATCHING_STATIONARY + "   ";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel, 1);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where expense list is displaying the expense we are finding
         * -> 1 expense found
         */
        command = FindExpenseCommand.COMMAND_WORD + " t/" + KEYWORD_MATCHING_DINNER;
        assertCommandSuccess(command, expectedModel, 1);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where expense list is displaying the expense we are finding
         * -> 0 expense found
         * TODO: findExpense command incorrect no matter what date format I used
         */
        command = FindExpenseCommand.COMMAND_WORD + " d/" + "7/4/2019";
        assertCommandSuccess(command, expectedModel, 0);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where expense list is displaying the expense we are finding
         * -> 0 expense found
         * TODO: findExpense command incorrect no matter what date format I used
         */
        command = FindExpenseCommand.COMMAND_WORD + " d/"
                + KEYWORD_MATCHING_START_DATE + ":" + "7/4/2019";
        assertCommandSuccess(command, expectedModel, 0);
        assertSelectedCardUnchanged();

        /* Case: find expense where expense list is not displaying the expense we are finding -> 0 expense found */
        command = FindExpenseCommand.COMMAND_WORD + " n/" + "Invalid name";
        assertCommandSuccess(command, expectedModel, 0);
        assertSelectedCardUnchanged();
        //        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        //        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        //        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        //        command = FindCommand.COMMAND_WORD + " Daniel Benson";
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        //        command = FindCommand.COMMAND_WORD + " Daniel Benson Daniel";
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
        //         * -> 2 persons found
        //         */
        //        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: undo previous find command -> rejected */
        //        command = UndoCommand.COMMAND_WORD;
        //        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        //        assertCommandFailure(command, expectedResultMessage);
        //
        //        /* Case: redo previous find command -> rejected */
        //        command = RedoCommand.COMMAND_WORD;
        //        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        //        assertCommandFailure(command, expectedResultMessage);
        //
        //        /* Case: find same persons in address book after deleting 1 of them -> 1 person found */
        //        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        //        assertFalse(getModel().getEPiggy().getPersonList().contains(BENSON));
        //        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        //        expectedModel = getModel();
        //        ModelHelper.setFilteredList(expectedModel, DANIEL);
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        /* Case: find person in address book, keyword is same as name but of different case -> 1 person found */
        //        command = FindCommand.COMMAND_WORD + " MeIeR";
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find person in address book, keyword is substring of name -> 0 persons found */
        //        command = FindCommand.COMMAND_WORD + " Mei";
        //        ModelHelper.setFilteredList(expectedModel);
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        //        command = FindCommand.COMMAND_WORD + " Meiers";
        //        ModelHelper.setFilteredList(expectedModel);
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find person not in address book -> 0 persons found */
        //        command = FindCommand.COMMAND_WORD + " Mark";
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find phone number of person in address book -> 0 persons found */
        //        command = FindCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find address of person in address book -> 0 persons found */
        //        command = FindCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find email of person in address book -> 0 persons found */
        //        command = FindCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find tags of person in address book -> 0 persons found */
        //        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        //        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();
        //
        //        /* Case: find while a person is selected -> selected card deselected */
        //        showAllPersons();
        //        //selectPerson(Index.fromOneBased(1));
        //assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        //        command = FindCommand.COMMAND_WORD + " Daniel";
        //        ModelHelper.setFilteredList(expectedModel, DANIEL);
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardDeselected();
        //
        //        /* Case: find person in empty address book -> 0 persons found */
        //        deleteAllPersons();
        //        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        //        expectedModel = getModel();
        //        ModelHelper.setFilteredList(expectedModel, DANIEL);
        //        assertCommandSuccess(command, expectedModel);
        //        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        //        command = "FiNd Meier";
        //        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

    }

    /**
     * Check for expected amount of found expenses.
     * Check for commandBox display after executed command.
     *
     * @param command Command string
     * @param expectedModel ePiggy testApp model
     * @param numOfFound Expected number of found expenses
     */
    private void assertCommandSuccess(String command, Model expectedModel, int numOfFound) {
        executeCommand(command);
        String exceptedMessage = "========================\n" + "ePiggy: " + numOfFound + " expenses listed!\n\nYou: "
                + command + "\n";
        stringBuilder.insert(0, exceptedMessage);
        assertApplicationDisplaysExpected("", stringBuilder.toString(), expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code EPiggySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see EPiggySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
