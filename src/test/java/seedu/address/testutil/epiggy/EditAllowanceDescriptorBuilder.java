package seedu.address.testutil.epiggy;

import seedu.address.logic.commands.epiggy.EditAllowanceCommand.EditAllowanceDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Name;
import seedu.address.model.tag.Tag;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EditAllowanceDescriptorBuilder {

    private EditAllowanceDescriptor descriptor;

    public EditAllowanceDescriptorBuilder() {
        descriptor = new EditAllowanceDescriptor();
    }

    public EditAllowanceDescriptorBuilder(EditAllowanceDescriptor descriptor) {
        this.descriptor = new EditAllowanceDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpenseDescriptor} with fields containing {@code expense}'s details
     */
    public EditAllowanceDescriptorBuilder(Allowance allowance) {
        descriptor = new EditAllowanceDescriptor();
        descriptor.setName(allowance.getItem().getName());
        descriptor.setCost(allowance.getItem().getCost());
        descriptor.setDate(allowance.getDate());
        descriptor.setTags(allowance.getItem().getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditAllowanceDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditAllowanceDescriptorBuilder withCost(String cost) {
        descriptor.setCost(new Cost(cost));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditAllowanceDescriptorBuilder withDate(String date) {
        try {
            descriptor.setDate(ParserUtil.parseDate(date));
        } catch (ParseException e) {
            descriptor.setDate(new Date());
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditExpenseDescriptor}
     * that we are building.
     */
    public EditAllowanceDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditAllowanceDescriptor build() {
        return descriptor;
    }
}
