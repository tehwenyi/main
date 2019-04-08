package seedu.address.model.epiggy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import seedu.address.model.expense.Period;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code EPiggy} with sample data.
 */
public class SampleEPiggyDataUtil {
    public static Expense[] getSampleExpenses() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new Expense[] {
            new seedu.address.model.epiggy.Allowance(new Item(new Name("Allowance"), new Cost(20), getTagSet("Allowance")), new Date()),
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

    public static seedu.address.model.epiggy.Goal getSampleGoal() {
        return new seedu.address.model.epiggy.Goal(new Name("Nintendo Switch"), new Cost(499));
    }

    public static seedu.address.model.epiggy.Budget[] getSampleBudget() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new seedu.address.model.epiggy.Budget[] {
            new seedu.address.model.epiggy.Budget(new Cost(200), new Period(30), sdf.parse("01/04/2019")),
            new seedu.address.model.epiggy.Budget(new Cost(200), new Period(31), sdf.parse("01/03/2019"))
        };
    }

    public static ReadOnlyEPiggy getSampleEPiggy() {
        EPiggy sampleEp = new EPiggy();
        try {
            for (Expense sampleExpense : getSampleExpenses()) {
                if (sampleExpense instanceof seedu.address.model.epiggy.Allowance) {
                    sampleEp.addAllowance((seedu.address.model.epiggy.Allowance) sampleExpense);
                } else {
                    sampleEp.addExpense(sampleExpense);
                }
            }
            seedu.address.model.epiggy.Budget[] budgets = getSampleBudget();
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
