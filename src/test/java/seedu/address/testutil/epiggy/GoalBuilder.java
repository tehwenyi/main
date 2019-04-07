package seedu.address.testutil.epiggy;

import seedu.address.model.epiggy.Goal;
import seedu.address.model.Expense.Cost;
import seedu.address.model.Expense.Name;

/**
 * A utility class to help with building Goal objects.
 */
public class GoalBuilder {

    public static final String DEFAULT_NAME = "Adidas Shoes";
    public static final String DEFAULT_COST = "129";

    private Name name;
    private Cost cost;

    public GoalBuilder() {
        name = new Name(DEFAULT_NAME);
        cost = new Cost(DEFAULT_COST);
    }

    public Goal build() {
        return new Goal(name, cost);
    }
}
