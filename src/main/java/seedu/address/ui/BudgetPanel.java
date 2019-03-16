package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the budget status.
 */
public class BudgetPanel extends UiPart<Region> {
    private static final String FXML = "BudgetPanel.fxml";
    //private final Logger logger = LogsCenter.getLogger(BudgetPanel.class);

    @FXML
    private ListView<String> budgetView;

    public BudgetPanel() {
        super(FXML);
        List<String> world = new ArrayList<>();
        ObservableList<String> string = FXCollections.observableList(world);
        string.add("Hello");
        string.add("World");
        budgetView.setItems(string);
    }

}
