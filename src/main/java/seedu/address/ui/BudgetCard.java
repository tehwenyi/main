package seedu.address.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.epiggy.Budget;

/**
 * A UI component that displays information of a {@code Budget}.
 */
public class BudgetCard extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(BudgetCard.class);

    private static final String FXML = "BudgetListCard.fxml";

    public final Budget budget;

    @FXML
    private HBox cardPane;
    @FXML
    private Label budgetTitle;
    @FXML
    private Label budgetedAmount;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label period;
    @FXML
    private Label currentStatus;
    @FXML
    private Label remainingAmount;
    @FXML
    private Label remainingDays;

    public BudgetCard(int displayedIndex, Budget budget) {
        super(FXML);
        this.budget = budget;
        budgetTitle.setText(displayedIndex + ". " + budget.getStatus() + " Budget");
        budgetedAmount.setText("Amount: $" + budget.getPrice().toString());

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy (E)");
        startDate.setText("Start Date: " + dateFormat.format(budget.getStartDate()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(budget.getEndDate());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        endDate.setText("End Date: " + dateFormat.format(calendar.getTime()));

        period.setText("Period of Budget: " + budget.getPeriod().toString() + " days");
        currentStatus.setText("Status");
        remainingAmount.setText("Amount remaining: $" + budget.getRemainingAmount().toString());
        remainingDays.setText("Days remaining: " + budget.getRemainingDays().toString() + " days");
    }

    //    /**
    //     * Returns the color style for {@code tagName}'s label.
    //     */
    //    private String getTagColorStyleFor(String tagName) {
    //        // generate a random color from the hash code of the tag so the color remain consistent
    //        // between different runs of the program while still making it random enough between tags.
    //        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    //    }

    //    /**
    //     * Creates the tag labels for {@code person}.
    //     */
    //    private void initialiseTags(Budget budget) {
    //        budget.getItem().getTags().forEach(tag -> {
    //            Label tagLabel = new Label(tag.tagName);
    //            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
    //            tags.getChildren().add(tagLabel);
    //        });
    //    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BudgetCard)) {
            return false;
        }

        // need to change
        return false;
        // state check
        //        BudgetCard card = (BudgetCard) other;
        //        return remainingDays.getText().equals(card.remainingDays.getText())
        //                && budget.equals(card.budget);
    }
}
