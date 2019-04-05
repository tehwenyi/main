package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;


public class ChatListPanel extends UiPart<Region> {
    private static final String FXML = "ChatList.fxml";

    @FXML
    private ListView<String> chatListView;

    public ChatListPanel(ObservableList<String> messages) {
        super(FXML);
//        chatListView.setItems();
    }
}
