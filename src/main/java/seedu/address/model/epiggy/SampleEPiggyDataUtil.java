package seedu.address.model.epiggy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EPiggy;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.epiggy.item.Name;
import seedu.address.model.epiggy.item.Period;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EPiggy} with sample data.
 */
public class SampleEPiggyDataUtil {
    public static Expense[] getSampleExpenses() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new Expense[] {
            new Allowance(new Item(
                    new Name("Allowance"),
                    new Cost(20), getTagSet("Allowance")),
                    sdf.parse("31/01/2019")
            ),
            new Expense(new Item(
                    new Name("Fishball Noodles"),
                    new Cost(4),
                    getTagSet("Lunch")),
                    sdf.parse("02/02/2019")
            )
        };
    }

    public static Goal getSampleGoal() {
        return new Goal(new Name("Nintendo Switch"), new Cost(499));
    }

    public static Budget[] getSampleBudget() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new Budget[] {
            new Budget(new Cost(200), new Period(30), sdf.parse("01/04/2019")),
            new Budget(new Cost(200), new Period(31), sdf.parse("01/03/2019"))
        };
    }

    public static ReadOnlyEPiggy getSampleEPiggy() {
        EPiggy sampleEp = new EPiggy();
        try {
            for (Expense sampleExpense : getSampleExpenses()) {
                if (sampleExpense instanceof Allowance) {
                    sampleEp.addAllowance((Allowance) sampleExpense);
                } else {
                    sampleEp.addExpense(sampleExpense);
                }
            }
            Budget[] budgets = getSampleBudget();
            for (int i = 0; i < budgets.length; i++) {
                sampleEp.addBudget(i, budgets[i]);
            }
            sampleEp.setGoal(getSampleGoal());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sampleEp;
    }

    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
