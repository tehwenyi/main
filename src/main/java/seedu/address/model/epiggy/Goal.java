package seedu.address.model.epiggy;

import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Name;

/**
 * Represents a Goal in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Goal {
    private final Name name;
    private final Cost amount;

    public Goal(Name name, Cost amount) {
        this.name = name;
        this.amount = amount;
    }

    public Name getName() {
        return name;
    }

    public Cost getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%s - $%2s", name, amount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Goal)) {
            return false;
        }
        Goal otherGoal = (Goal) other;
        return otherGoal.getName().equals(getName())
                && otherGoal.getAmount().equals(getAmount());
    }
}
