package guitests.guihandles.epiggy;

import guitests.guihandles.NodeHandle;

import javafx.scene.Node;
import javafx.scene.control.Label;

import seedu.address.model.expense.Budget;

/**
 * Provides a handle to a budget card in the budget list panel.
 */
public class BudgetCardHandle extends NodeHandle<Node> {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String AMOUNT_FIELD_ID = "#amount";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String ENDDATE_FIELD_ID = "#endDate";
    private static final String PERIOD_FIELD_ID = "#period";
    private static final String STATUS_FIELD_ID = "#status";
    private static final String REMAININGAMOUNT_FIELD_ID = "#remainingAmount";
    private static final String REMAININGDAYS_FIELD_ID = "#remainingDays";
    private static final String NOTIFICATION_FIELD_ID = "#notification";

    private final Label titleLabel;
    private final Label amountLabel;
    private final Label startDateLabel;
    private final Label endDateLabel;
    private final Label periodLabel;
    private final Label statusLabel;
    private final Label remainingAmountLabel;
    private final Label remainingDaysLabel;
    private final Label notificationLabel;

    public BudgetCardHandle(Node cardNode) {
        super(cardNode);

        titleLabel = getChildNode(TITLE_FIELD_ID);
        amountLabel = getChildNode(AMOUNT_FIELD_ID);
        startDateLabel = getChildNode(STARTDATE_FIELD_ID);
        endDateLabel = getChildNode(ENDDATE_FIELD_ID);
        periodLabel = getChildNode(PERIOD_FIELD_ID);
        statusLabel = getChildNode(STATUS_FIELD_ID);
        remainingAmountLabel = getChildNode(REMAININGAMOUNT_FIELD_ID);
        remainingDaysLabel = getChildNode(REMAININGDAYS_FIELD_ID);
        notificationLabel = getChildNode(NOTIFICATION_FIELD_ID);
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getAmount() {
        return amountLabel.getText();
    }

    public String getStartDate() {
        return startDateLabel.getText();
    }

    public String getEndDate() {
        return endDateLabel.getText();
    }

    public String getPeriod() {
        return periodLabel.getText();
    }

    public String getStatus() {
        return statusLabel.getText();
    }

    public String getRemainingAmount() {
        return remainingAmountLabel.getText();
    }

    public String getRemainingDays() {
        return remainingDaysLabel.getText();
    }

    public String getNotification() {
        return notificationLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code budget}.
     */
    public boolean equals(Budget budget) {
        return getAmount().equals(budget.getBudgetedAmount().toString())
                && getStartDate().equals(budget.getStartDate().toString())
                && getPeriod().equals(budget.getPeriod().toString());
    }
}
