package seedu.address.model.epiggy;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.EPiggy;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Item;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EPiggy} with sample data.
 */
public class SampleEPiggyDataUtil {
    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Allowance(new Item(new Name("Allowance"), new Cost(20), getTagSet("Allowance")), new Date()),
            new Expense(new Item(new Name("Dumpling Soup"), new Cost("5.00"),
                    getTagSet("food", "lunch")), new Date("20/03/2019")),
            new Expense(new Item(new Name("Stationary"), new Cost("3.00"),
                    getTagSet("school", "misc")), new Date("06/03/2019")),
            new Expense(new Item(new Name("Avengers : Endgame movie"), new Cost("8.50"),
                    getTagSet("movie", "entertainment", "friends")), new Date("26/04/2019")),
            new Expense(new Item(new Name("Karaoke: KTV"), new Cost("10.00"),
                    getTagSet("friends")), new Date("07/10/2018")),
            new Expense(new Item(new Name("Clothes shopping"), new Cost("21.80"),
                    getTagSet("shopping")), new Date()),
            new Expense(new Item(new Name("KFC"), new Cost("13.00"),
                    getTagSet("food", "dinner")), new Date("26/04/2019")),
            new Expense(new Item(new Name("bowling at West Bowl centre"), new Cost("4.80"),
                    getTagSet("friends")), new Date("07/10/2018")),
            new Expense(new Item(new Name("IPhone XR from challenger"), new Cost("1799.00"),
                    getTagSet()), new Date("20/03/2019"))
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
