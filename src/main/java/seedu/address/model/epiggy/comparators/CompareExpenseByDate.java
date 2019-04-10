package seedu.address.model.epiggy.comparators;

import java.util.Comparator;

import seedu.address.model.epiggy.Expense;

/**
 * Comparator function for sorting Expenses by date in with the latest date being first.
 */
public class CompareExpenseByDate implements Comparator<Expense> {

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compare(Expense o1, Expense o2) {
        if (o1.getDate() == null || o2.getDate() == null) {
            return 0;
        }
        return o2.getDate().compareTo(o1.getDate());
    }
}
