package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;

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
        Text text1 = new Text("Budget");
        // text1.setFill(Color.RED);
        text1.setFont(Font.font("Helvetica", FontPosture.ITALIC, 40));
        Text text2 = new Text(" trytry");
        // text2.setFill(Color.BLUE);
        text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
        TextFlow textFlow = new TextFlow(text1, text2);

    }

}
