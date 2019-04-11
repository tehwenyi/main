package seedu.address.testutil.epiggy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.epiggy.item.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building Allowance objects.
 */
public class AllowanceBuilder {

    public static final String DEFAULT_NAME = "Allowance from Mom";
    public static final String DEFAULT_COST = "20.0";
    public static final String DEFAULT_DATE = "03/01/2019";

    private Name name;
    private Cost cost;
    private Date date;
    private Set<Tag> tags;

    public AllowanceBuilder() {
        name = new Name(DEFAULT_NAME);
        cost = new Cost(DEFAULT_COST);
        date = new Date(DEFAULT_DATE);
        tags = new HashSet<>();
    }

    public AllowanceBuilder(Allowance allowanceToCopy) {
        name = allowanceToCopy.getItem().getName();
        cost = allowanceToCopy.getItem().getCost();
        date = allowanceToCopy.getDate();
        tags = new HashSet<>(allowanceToCopy.getItem().getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Allowance} that we are building.
     */
    public AllowanceBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Expense} that we are building.
     */
    public AllowanceBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code cost} of the {@code Expense} that we are building.
     */
    public AllowanceBuilder withCost(String cost) {
        this.cost = new Cost(cost);
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Expense} that we are building.
     */
    public AllowanceBuilder withDate(String date) {
        System.out.println(name);
        System.out.println(date);
        if (date.equals("")) {
            this.date = new Date();
        }
        try {
            this.date = ParserUtil.parseDate(date);
        } catch (ParseException e) {
            this.date = new Date();
        }
        return this;
    }

    /**
     * Builds an {@code Expense} object with name, date, cost and tags
     * @return Expense object
     */
    public Allowance build() {
        Item item = new Item(name, cost, tags);
        return new Allowance(item, date);
    }
}
