package seedu.address.model.epiggy.item;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Item's name in the expense book.
 * Guarantees: immutable}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Name should have at least 1 alphanumeric character and less than 50 characters in length.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9 ]{1,50}$";
    public final String name;

    public Name(String name) {
        requireNonNull(name);
        this.name = name;
    }

    public static boolean isValidName(String name) {
        return name.trim().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Name)) {
            return false;
        }
        return name.equals(((Name) other).name);
    }
}
