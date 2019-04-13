package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.item.Cost;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.epiggy.item.Name;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing allowance.
 */
public class EditAllowanceCommand extends Command {

    public static final String COMMAND_WORD = "editAllowance";
    public static final String COMMAND_ALIAS = "ea";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the allowance identified "
            + "by the index in the displayed list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_COST + "COST] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_COST + "5 ";

    public static final String MESSAGE_EDIT_ALLOWANCE_SUCCESS = "Allowance edited.\nEdited allowance's details:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "Allowance not edited as there are no changes.\n"
            + MESSAGE_USAGE;

    private static final String MESSAGE_ITEM_NOT_ALLOWANCE = "The item selected is not an allowance. "
            + "Please use " + EditExpenseCommand.COMMAND_WORD + " to edit expenses and "
            + COMMAND_WORD + " to edit allowances.";

    final Index index;
    final EditAllowanceDescriptor editAllowanceDescriptor;

    /**
     * @param index                 of the allowance in the filtered expenses list to edit
     * @param editAllowanceDescriptor details to edit the allowance with
     */
    public EditAllowanceCommand(Index index, EditAllowanceDescriptor editAllowanceDescriptor) {
        requireNonNull(index);
        requireNonNull(editAllowanceDescriptor);

        this.index = index;
        this.editAllowanceDescriptor = new EditAllowanceDescriptor(editAllowanceDescriptor);

    }

    @Override
    public String toString() {
        return editAllowanceDescriptor.toString();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenseList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense toEdit = lastShownList.get(index.getZeroBased());
        if (!(toEdit instanceof Allowance)) {
            throw new CommandException(MESSAGE_ITEM_NOT_ALLOWANCE);
        }
        Allowance editedAllowance = createEditedAllowance((Allowance) toEdit, editAllowanceDescriptor);

        model.setExpense(toEdit, editedAllowance);
        model.updateFilteredExpensesList(PREDICATE_SHOW_ALL_EXPENSES);
        model.commitEPiggy();
        return new CommandResult(String.format(MESSAGE_EDIT_ALLOWANCE_SUCCESS, editedAllowance));
    }

    /**
     * Creates and returns a {@code Allowance} with the details of {@code allowanceToEdit}
     * edited with {@code editAllowanceDescriptor}.
     */
    static Allowance createEditedAllowance(Allowance allowanceToEdit, EditAllowanceDescriptor editAllowanceDescriptor) {
        assert allowanceToEdit != null;

        Name updatedName = editAllowanceDescriptor.getName().orElse(allowanceToEdit.getItem().getName());
        Cost updatedCost = editAllowanceDescriptor.getCost().orElse(allowanceToEdit.getItem().getCost());
        Date updatedDate = editAllowanceDescriptor.getDate().orElse(allowanceToEdit.getDate());
        Set<Tag> updatedTags = editAllowanceDescriptor.getTags().orElse(allowanceToEdit.getItem().getTags());
        return new Allowance(new Item(updatedName, updatedCost, updatedTags), updatedDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAllowanceCommand)) {
            return false;
        }

        // state check
        EditAllowanceCommand e = (EditAllowanceCommand) other;
        return index.equals(e.index)
                && editAllowanceDescriptor.equals(e.editAllowanceDescriptor);
    }
    /**
     * Stores the details to edit the allowance with. Each non-empty field value will replace the
     * corresponding field value of the allowance.
     */
    public static class EditAllowanceDescriptor {
        private Name name;
        private Cost cost;
        private Date date;
        private Set<Tag> tags;

        public EditAllowanceDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAllowanceDescriptor(EditAllowanceDescriptor toCopy) {
            setName(toCopy.name);
            setCost(toCopy.cost);
            setDate(toCopy.date);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, cost, date, tags);
        }

        public void setName(seedu.address.model.epiggy.item.Name name) {
            this.name = name;
        }

        public void setCost(Cost cost) {
            this.cost = cost;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Cost> getCost() {
            return Optional.ofNullable(cost);
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            if (tags != null) {
                tags.add(new Tag("Allowance"));
                return Optional.of(Collections.unmodifiableSet(tags));
            } else {
                return Optional.empty();
            }
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAllowanceDescriptor)) {
                return false;
            }

            // state check
            EditAllowanceDescriptor e = (EditAllowanceDescriptor) other;

            return getName().equals(e.getName())
                    && getCost().equals(e.getCost())
                    && getDate().equals(e.getDate())
                    && getTags().equals(e.getTags());
        }
    }


}
