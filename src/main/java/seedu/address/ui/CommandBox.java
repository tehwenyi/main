package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static int tabCount = 0; // count for "tab" pressed
    private final CommandExecutor commandExecutor;
    private final List<String> history;
    private ListElementPointer historySnapshot;

    private String[] commandArr = {
        "addAllowance n/ $/ t/ d/",
        "addBudget $/ p/ d/",
        "addExpense n/ $/ t/ d/",
        "clear",
        "deleteAllowance ",
        "deleteBudget ",
        "deleteExpense ",
        "editAllowance  n/ $/ d/ t/",
        "editBudget $/ p/ d/",
        "editExpense  n/ $/ d/ t/",
        "exit",
        "findExpense n/ t/ d/ $/",
        "help",
        "history",
        "list",
        "redo",
        "report d/",
        "reverseList",
        "setGoal n/ $/",
        "sortExpense n/ d/ $/",
        "undo",
    };

    @FXML
    private TextField commandTextField;

    @FXML
    private TextArea resultDisplay;

    public CommandBox(CommandExecutor commandExecutor, List<String> history) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.history = history;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = new ListElementPointer(history);
    }

    /**
     * Find set of similar prefix keywords.
     *
     * @param stringTryToMatch User enters prefix
     * @param commandInArray   Command checklist
     * @return Set of same prefix commands.
     */
    private static String[] findString(String stringTryToMatch, String[] commandInArray) {

        if (stringTryToMatch.length() == 0) {
            // partOfString is null, return empty array
            return new String[0];
        }
        ArrayList<String> resultArr = new ArrayList<>();
        // for each elements in strings, compare with part of the string.
        for (int i = 0; i < commandInArray.length; i++) {
            if (stringTryToMatch.length() > commandInArray[i].length()) {
                // do nothing if the length of user input string longer than keyword.
                continue;
            } else if (stringTryToMatch.equalsIgnoreCase((commandInArray[i]
                    .substring(0, stringTryToMatch.trim().length())))) {
                resultArr.add(commandInArray[i]);
            }
        }
        return resultArr.toArray(new String[resultArr.size()]);
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();

            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;

        case TAB:
            keyEvent.consume();
            autoCompleteText();
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * autocomplete text.
     * Compare user input with the commandArr in the commands list.
     * commandBox shows found command
     */
    private void autoCompleteText() {
        String[] inputString = commandTextField.getText().split(" "); //split with white space
        String partOfString = inputString[inputString.length - 1]; // get the last element of input string
        String[] results = findString(partOfString, commandArr);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputString.length - 1; i++) {
            /* group user inputs */
            stringBuilder.append(inputString[i].trim());
            stringBuilder.append(" ");
        }
        // updates user + found keyword
        if (results.length == 0) {
            /* if result is null, nothing match */
            stringBuilder.append(inputString[inputString.length - 1]); // adds back last element of input string
        } else {
            // append match keyword to user input
            if (tabCount >= results.length) {
                tabCount = 0; // reset tabCount when tabCount bigger than the number of match commandArr
            }
            stringBuilder.append(results[tabCount]); // adds found keyword
        }
        commandTextField.requestFocus(); // set focus back to the textfield
        commandTextField.setText(stringBuilder.toString()); // updates the textfield
        commandTextField.positionCaret(stringBuilder.length()); // set caret after the new text
        tabCount++;
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            initHistory();
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = new ListElementPointer(history);
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
