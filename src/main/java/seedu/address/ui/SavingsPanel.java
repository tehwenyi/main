package seedu.address.ui;

import java.util.function.Supplier;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Goal;
import seedu.address.model.expense.item.Cost;

/**
 * Panel containing savings information.
 */
public class SavingsPanel extends UiPart<Region> {

    private static final String FXML = "SavingsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SavingsPanel.class);

    @FXML
    private Label currentSavings;

    @FXML
    private Label currentGoal;

    @FXML
    private Label amountDifferenceTitle;

    @FXML
    private Label amountDifference;

    public SavingsPanel(ObservableList<Expense> expense, ObservableValue<Goal> goal,
                        Supplier<ObservableValue<Cost>> onSavingsChange) {
        super(FXML);

        expense.addListener((ListChangeListener<? super Expense>) observable -> {
            refreshPanel(goal, onSavingsChange);
        });

        goal.addListener(observable -> {
            refreshPanel(goal, onSavingsChange);
        });

        refreshPanel(goal, onSavingsChange);
    }

    /**
     * Updates the savings panel with new changes.
     * @param goal
     * @param onSavingsChange
     */
    private void refreshPanel(ObservableValue<Goal> goal, Supplier<ObservableValue<Cost>> onSavingsChange) {
        currentSavings.setText(onSavingsChange.get().getValue().toString());
        if (goal.getValue() == null) {
            currentGoal.setText("(None set)");
            amountDifference.setText("$0.00");
        } else {
            currentGoal.setText(goal.getValue().toString());
            double diff = goal.getValue().getAmount().getAmount() - onSavingsChange.get().getValue().getAmount();
            if (diff > 0) {
                amountDifferenceTitle.setVisible(true);
                amountDifference.setText("$" + diff);
            } else {
                amountDifferenceTitle.setVisible(false);
                amountDifference.setText("Congratulations!\nYou've reached your\nsavings goal!");
            }
        }
    }
}
