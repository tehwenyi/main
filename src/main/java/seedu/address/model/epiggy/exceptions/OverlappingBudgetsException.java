package seedu.address.model.epiggy.exceptions;

/**
 * Signals that this will cause budgets to overlap.
 */
public class OverlappingBudgetsException extends RuntimeException {
    public OverlappingBudgetsException() {
        super("Operation will cause budget to overlap. Please enter a start date later "
                + "than the previous budget's end date.");
    }
}
