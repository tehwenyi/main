package seedu.address.model.epiggy.comparators;

import java.util.Comparator;

import seedu.address.model.epiggy.Expense;

/**
 * Comparator function for sorting Expenses by cost in descending order.
 */
public class CompareExpenseByCost implements Comparator<Expense> {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CompareExpenseByCost;
    }

    @Override
    public int compare(Expense o1, Expense o2) {
        if (o1.getItem().getCost() == null || o2.getItem().getCost() == null) {
            return 0;
        }
        return o1.getItem().getCost().getAmount() < o2.getItem().getCost().getAmount() ? 1 : -1;
    }
}
