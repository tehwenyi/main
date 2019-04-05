package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;


/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    private String messages = "";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.setText("Welcome to ePiggy! "
                + "Enter a command to get started, or enter 'help' to view all the available commands!");
    }

    public void setFeedbackToUser(String feedbackToUser, String commandEntered) {
        requireNonNull(feedbackToUser);

        messages = "ePiggy: " + feedbackToUser + "\n\n" + "You: " + commandEntered + "\n\n" + messages;

        resultDisplay.setText(messages);
    }
}
