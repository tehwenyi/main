package seedu.address.model.epiggy.item;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Item in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {
    private final Name name;
    private final Cost cost;
    private final Set<Tag> tags;

    public Item(Name name, Cost cost, Set<Tag> tags) {
        this.name = name;
        this.cost = cost;
        this.tags = tags;
    }

    public Name getName() {
        return name;
    }

    public Cost getCost() {
        return cost;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(name)
                .append(": $")
                .append(cost)
                .append("\nTags: ")
                .append(tags);
        return builder.toString();
    }
}
