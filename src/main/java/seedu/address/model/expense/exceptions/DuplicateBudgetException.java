package seedu.address.model.expense.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateBudgetException extends RuntimeException {
    public DuplicateBudgetException() {
        super("Operation would result in duplicate budgets");
    }
}
