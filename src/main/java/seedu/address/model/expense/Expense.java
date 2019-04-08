package seedu.address.model.expense;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.model.expense.item.Item;

/**
 * Represents an expense in the expense book.
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
}
