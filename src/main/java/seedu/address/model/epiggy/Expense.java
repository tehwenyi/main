package seedu.address.model.epiggy;

import seedu.address.model.epiggy.item.Date;
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
        builder.append(item)
                .append(" (")
                .append(date)
                .append(")");
        return builder.toString();
    }
}
