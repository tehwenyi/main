package seedu.address.testutil.epiggy;

import seedu.address.model.epiggy.Goal;

//@@author kev-inc

/**
 * A utility class containing a list of {@code Goal} objects to be used in tests.
 */
public class TypicalGoal {
    public static final Goal IPHONE = new GoalBuilder()
            .withName("IPHONE X")
            .withCost("999")
            .build();

    public static final Goal ROLEX = new GoalBuilder()
            .withName("Rolex Watch")
            .withCost("12000")
            .build();

    private TypicalGoal() { }

}
