package seedu.address.testutil.epiggy;

import seedu.address.model.EPiggy;
import seedu.address.model.epiggy.Allowance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2019;

public class TypicalAllowances {
    public static final Allowance ALLOWANCE_FROM_MOM = new AllowanceBuilder().withName("From Mom")
            .withCost("20.00")
            .withDate(VALID_DATE_2019)
            .withTags("Allowance").build();

    public static final Allowance ALLOWANCE_FROM_DAD = new AllowanceBuilder().withName("From Mom")
            .withCost("30.00")
            .withDate(VALID_DATE_2019)
            .withTags("Allowance").build();

    private TypicalAllowances() { }

    public static EPiggy getTypicalEPiggy() {
        EPiggy ep = new EPiggy();
        for (Allowance allowance : getTypicalAllowances()) {
            ep.addAllowance(allowance);
        }
        return ep;
    }

    public static List<Allowance> getTypicalAllowances() {
        return new ArrayList<>(Arrays.asList(ALLOWANCE_FROM_MOM, ALLOWANCE_FROM_DAD));
    }
}
