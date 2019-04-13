package seedu.address.testutil.epiggy;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2019;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SECONDEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALLOWANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FIRSTEXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SECONDEXTRA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EPiggy;
import seedu.address.model.epiggy.Allowance;

/**
 * A utility class containing a list of {@code Allowance} objects to be used in tests.
 */
public class TypicalAllowances {
    public static final Allowance ALLOWANCE_FROM_MOM = new AllowanceBuilder().withName("From Mom")
            .withCost("20.00")
            .withDate(VALID_DATE_2019)
            .withTags(VALID_TAG_ALLOWANCE).build();

    public static final Allowance ALLOWANCE_FROM_DAD = new AllowanceBuilder().withName("From Mom")
            .withCost("30.00")
            .withDate(VALID_DATE_2019)
            .withTags(VALID_TAG_ALLOWANCE).build();

    public static final Allowance FIRST_EXTRA = new AllowanceBuilder().withName(VALID_NAME_FIRSTEXTRA)
            .withCost(VALID_AMOUNT_FIRSTEXTRA).withDate(VALID_DATE_FIRSTEXTRA)
            .withTags(VALID_TAG_FIRSTEXTRA, VALID_TAG_ALLOWANCE).build();

    public static final Allowance SECOND_EXTRA = new AllowanceBuilder().withName(VALID_NAME_SECONDEXTRA)
            .withCost(VALID_AMOUNT_SECONDEXTRA).withDate(VALID_DATE_SECONDEXTRA)
            .withTags(VALID_TAG_SECONDEXTRA, VALID_TAG_ALLOWANCE).build();


    private TypicalAllowances() { }

    public static EPiggy getTypicalEPiggy() {
        EPiggy ep = new EPiggy();
        for (Allowance allowance : getTypicalAllowances()) {
            ep.addAllowance(allowance);
        }
        return ep;
    }

    public static List<Allowance> getTypicalAllowances() {
        return new ArrayList<>(Arrays.asList(FIRST_EXTRA, SECOND_EXTRA));
    }
}
