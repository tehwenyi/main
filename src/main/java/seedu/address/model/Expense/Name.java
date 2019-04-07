package seedu.address.model.Expense;

/**
 * Represents an Item's name in the expense book.
 * Guarantees: immutable}
 */
public class Name {
    public final String name;

    public Name(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
