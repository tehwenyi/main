package seedu.address.model.epiggy.comparators;

import java.util.Comparator;

import seedu.address.model.epiggy.Expense;

/**
 * Comparator function for sorting Expenses by name in lexicographical order.
 */
public class CompareExpenseByName implements Comparator<Expense> {
    @Override
    public int compare(Expense o1, Expense o2) {
        if (o1.getItem().getName() == null || o2.getItem().getName() == null) {
            return 0;
        }
        return o1.getItem().getName().name.compareToIgnoreCase(o2.getItem().getName().name);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
