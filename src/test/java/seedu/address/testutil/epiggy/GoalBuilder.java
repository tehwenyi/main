package seedu.address.testutil.epiggy;

import seedu.address.model.epiggy.Goal;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Name;

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
    /**
     * Sets the {@code Name} of the {@code Goal} that we are building.
     */
    public GoalBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }
    /**
     * Sets the {@code cost} of the {@code Goal} that we are building.
     */
    public GoalBuilder withCost(String cost) {
        this.cost = new Cost(cost);
        return this;
    }

    public Goal build() {
        return new Goal(name, cost);
    }
}
