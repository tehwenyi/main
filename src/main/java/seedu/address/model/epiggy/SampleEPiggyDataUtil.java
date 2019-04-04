package seedu.address.model.epiggy;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EPiggy;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.epiggy.item.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EPiggy} with sample data.
 */
public class SampleEPiggyDataUtil {
    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Allowance(new Item(new Name("Allowance"), new Cost(20), getTagSet("Allowance")), new Date()),
            new Expense(new Item(new Name("Fishball Noodles"), new Cost(4), getTagSet("Lunch")), new Date())
        };
    }

    public static Goal getSampleGoal() {
        return new Goal(new Name("Nintendo Switch"), new Cost(499));
    }

    public static ReadOnlyEPiggy getSampleEPiggy() {
        EPiggy sampleEp = new EPiggy();
        for (Expense sampleExpense : getSampleExpenses()) {
            if (sampleExpense instanceof Allowance) {
                sampleEp.addAllowance((Allowance) sampleExpense);
            } else {
                sampleEp.addExpense(sampleExpense);
            }
        }
        sampleEp.setGoal(getSampleGoal());
        return sampleEp;
    }

    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
