package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class ChatBubble extends UiPart<Region> {

    private static final String FXML = "ChatBubble.fxml";

    @FXML
    private Label messageText;

    public ChatBubble(String message) {
        super(FXML);
        messageText.setText(message);
    }
}
