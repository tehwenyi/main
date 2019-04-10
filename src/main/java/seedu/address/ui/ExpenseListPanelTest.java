package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.expense.Expense;

/**
 * Panel containing the list of expenses.
 */
public class ExpenseListPanelTest extends UiPart<Region> {
    private static final String FXML = "ExpenseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExpenseListPanel.class);

    @FXML
    private ListView<Expense> personListView;

    public ExpenseListPanelTest(ObservableList<Expense> personList, ObservableValue<Expense> selectedExpense,
                           Consumer<Expense> onSelectedExpenseChange) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new ExpenseListViewCell());
        personListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in person list panel changed to : '" + newValue + "'");
            onSelectedExpenseChange.accept(newValue);
        });
        selectedExpense.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected person changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected person,
            // otherwise we would have an infinite loop.
            if (Objects.equals(personListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                personListView.getSelectionModel().clearSelection();
            } else {
                int index = personListView.getItems().indexOf(newValue);
                personListView.scrollTo(index);
                personListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code expense} using a {@code ExpenseCard}.
     */
    class ExpenseListViewCell extends ListCell<Expense> {
        @Override
        protected void updateItem(Expense person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ExpenseCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
