package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
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

    private String[] commandArr = { "addExpense n/ $/ t/ d/", "deleteExpense ", "editExpense",
        "report d/", "list ", "help ", "edit ", "exit", "view ",
        "editBudget $/ p/ d/", "percentage", "addBudget $/ p/ d/",
        "addAllowance $/", "deleteBudget ", "setGoal n/ $/",
        "viewGoal", "viewSavings ", "sE ", "findExpense ", "fE ", "sortExpense ",
        "reverseList ", "rl ", "select ", "clear ", "undo ", "redo ", "history" };

    @FXML
    private TextField commandTextField;

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
            } else if (stringTryToMatch.equals(commandInArray[i].substring(0, stringTryToMatch.trim().length()))) {
                resultArr.add(commandInArray[i]);
            }
        }
        //TODO: this part is for 2101 demo, remove it after demo
        switch (stringTryToMatch) {
        case "1":
            resultArr.add("addAllowance n/From Mom $/300");
            break;
        case "2":
            resultArr.add("setGoal n/Apple Watch $/600");
            break;
        case "3":
            resultArr.add("addBudget $/200 p/30 d/8/4/2019");
            break;
        case "4":
            resultArr.add("addExpense n/Chicken Rice $/4 t/Lunch");
            break;
        case "5":
            resultArr.add("addExpense n/Roti Prata $/5 t/TeaBreak");
            break;
        case "6":
            resultArr.add("addExpense n/Beef Pho $/6 t/Dinner");
            break;
        case "7":
            resultArr.add("addExpense n/Gift $/180 t/gf");
            break;
        case "8":
            resultArr.add("findExpense n/pho");
            break;
        case "9":
            resultArr.add("addExpense n/Pho $/6");
            break;
        case "10":
            resultArr.add("sortExpense $/");
            break;
        case "11":
            resultArr.add("report");
            break;
        case "12":
        default:
            resultArr.add("addAllowance n/new life $/600");
            break;
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
                stringBuilder.append(results[tabCount]); // adds found keyword
            } else {
                stringBuilder.append(results[tabCount]); // adds found keyword
            }
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
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
