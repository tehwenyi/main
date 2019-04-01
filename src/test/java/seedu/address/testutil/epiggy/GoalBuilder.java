package seedu.address.testutil.epiggy;

import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Name;

public class GoalBuilder {

    public static final String DEFAULT_NAME = "Adidas Shoes";
    public static final String DEFAULT_COST = "129";

    private Name name;
    private Cost cost;

    public GoalBuilder() {
        name = new Name(DEFAULT_NAME);
        cost = new Cost(DEFAULT_COST);
    }

    public GoalBuilder(Goal toCopy) {
        name = toCopy.getName();
        cost = toCopy.getAmount();
    }

    public GoalBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public GoalBuilder withCost(String cost) {
        this.cost = new Cost(cost);
        return this;
    }

    public Goal build() {
        return new Goal(name, cost);
    }
}
