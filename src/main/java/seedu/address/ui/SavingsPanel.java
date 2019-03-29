package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.Savings;

import java.util.logging.Logger;

public class SavingsPanel extends UiPart<Region> {

    private static final String FXML = "SavingsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SavingsPanel.class);

    @FXML
    private Label currentSavings;

    @FXML
    private Label goalName;

    @FXML
    private Label goalPrice;

    @FXML
    private Label amountDifference;

    public SavingsPanel(ObservableValue<Savings> savings, ObservableValue<Goal> goal) {
        super(FXML);


    }
}
