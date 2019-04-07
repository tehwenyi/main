package seedu.address.testutil.epiggy;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.epiggy.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.model.Expense.Expense;
import seedu.address.model.Expense.Cost;
import seedu.address.model.Expense.Name;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditExpenseDescriptor objects.
 */
public class EditExpenseDescriptorBuilder {

    private EditExpenseDescriptor descriptor;

    public EditExpenseDescriptorBuilder() {
        descriptor = new EditExpenseDescriptor();
    }

    public EditExpenseDescriptorBuilder(EditExpenseDescriptor descriptor) {
        this.descriptor = new EditExpenseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpenseDescriptor} with fields containing {@code expense}'s details
     */
    public EditExpenseDescriptorBuilder(Expense expense) {
        descriptor = new EditExpenseDescriptor();
        descriptor.setName(expense.getItem().getName());
        descriptor.setCost(expense.getItem().getCost());
        descriptor.setDate(expense.getDate());
        descriptor.setTags(expense.getItem().getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withCost(String cost) {
        descriptor.setCost(new Cost(cost));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditExpenseDescriptor}
     * that we are building.
     */
    public EditExpenseDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditExpenseDescriptor build() {
        return descriptor;
    }
}
