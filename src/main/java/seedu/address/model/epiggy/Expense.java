package seedu.address.model.epiggy;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.model.epiggy.item.Item;

/**
 * Represents an Expense in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense {
    private final Item item;
    private final Date date;

    public Expense(Item item, Date date) {
        this.item = item;
        this.date = date;
    }

    public Item getItem() {
        return item;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, ''yy");
        builder.append(item)
                .append("\nAdded on: ")
                .append(format.format(date));
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Expense)) {
            return false;
        }
        Expense otherExpense = (Expense) other;
        return otherExpense.getItem().equals(getItem())
                && otherExpense.getDate().equals(getDate());
    }
}
