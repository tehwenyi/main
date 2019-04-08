package seedu.address.testutil;

import seedu.address.model.EPiggy;
import seedu.address.model.expense.Expense;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ePiggy ab = new EPiggyBuilder().withExpense("John", "Doe").build();}
 */
public class EPiggyBuilder {

    private EPiggy ePiggy;

    public EPiggyBuilder() {
        ePiggy = new EPiggy();
    }

    public EPiggyBuilder(EPiggy ePiggy) {
        this.ePiggy = ePiggy;
    }

    /**
     * Adds a new {@code Person} to the {@code ePiggy} that we are building.
     */
    public EPiggyBuilder withExpense(Expense expense) {
        ePiggy.addExpense(expense);
        return this;
    }

    public EPiggy build() {
        return ePiggy;
    }
}
