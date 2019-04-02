package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.Savings;

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

    public SavingsPanel(ObservableValue<Savings> savings, ObservableValue<Goal> goal) {
        super(FXML);

        savings.addListener(observable -> {
            currentSavings.setText(savings.getValue().toString());
            if (goal.getValue() == null) {
                currentGoal.setText("(None set)");
                amountDifference.setText("$0.00");
            } else {
                currentGoal.setText(goal.getValue().toString());
                double diff = goal.getValue().getAmount().getAmount() - savings.getValue().getSavings();
                if (diff > 0) {
                    amountDifferenceTitle.setVisible(true);
                    amountDifference.setText("$" + diff);
                } else {
                    amountDifferenceTitle.setVisible(false);
                    amountDifference.setText("Congratulations!\nYou've reached your\nsavings goal!");
                }
            }
        });
        goal.addListener(observable -> {
            currentGoal.setText(goal.getValue().toString());
            double diff = goal.getValue().getAmount().getAmount() - savings.getValue().getSavings();
            if (diff > 0) {
                amountDifferenceTitle.setVisible(true);
                amountDifference.setText("$" + diff);
            } else {
                amountDifferenceTitle.setVisible(false);
                amountDifference.setText("Congratulations!\nYou've reached your\nsavings goal!");
            }
        });

        currentSavings.setText(savings.getValue().toString());
        if (goal.getValue() == null) {
            currentGoal.setText("(None set)");
            amountDifference.setText("$0.00");
        } else {
            currentGoal.setText(goal.getValue().toString());
            double diff = goal.getValue().getAmount().getAmount() - savings.getValue().getSavings();
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
