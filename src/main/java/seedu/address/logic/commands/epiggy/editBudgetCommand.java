package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits the current budget in ePiggy.
 */
public class editBudgetCommand extends Command {

    public static final String COMMAND_WORD = "editBudget";
    public static final String COMMAND_ALIAS = "eb";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the current budget. ";

    public static final String MESSAGE_EDIT_BUDGET_SUCCESS = "Current budget updated";

    private final EditBudgetDetails editBudgetDetails;

    public editBudgetCommand(EditBudgetDetails editBudgetDetails) {
        requireNonNull(editBudgetDetails);
        this.editBudgetDetails = new EditBudgetDetails(editBudgetDetails);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
//        List<Person> lastShownList = model.getFilteredPersonList();
//
//        if (index.getZeroBased() >= lastShownList.size()) {
//            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//        }
//
//        Person personToEdit = lastShownList.get(index.getZeroBased());
//        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
//
//        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
//            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//        }
//
//        model.setPerson(personToEdit, editedPerson);
//        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
//        model.commitAddressBook();
        // Need to add the edited budget details
        return new CommandResult(String.format(MESSAGE_EDIT_BUDGET_SUCCESS));
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditBudgetDetails {
//        private Name name;
//        private Phone phone;
//        private Email email;
//        private Address address;
//        private Set<Tag> tags;

        public EditBudgetDetails() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBudgetDetails(EditBudgetDetails toCopy) {
//            setName(toCopy.name);
//            setPhone(toCopy.phone);
//            setEmail(toCopy.email);
//            setAddress(toCopy.address);
//            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
//        public boolean isAnyFieldEdited() {
//            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
//        }
//
//        public void setName(Name name) {
//            this.name = name;
//        }
//
//        public Optional<Name> getName() {
//            return Optional.ofNullable(name);
//        }
//
//        public void setPhone(Phone phone) {
//            this.phone = phone;
//        }
//
//        public Optional<Phone> getPhone() {
//            return Optional.ofNullable(phone);
//        }
//
//        public void setEmail(Email email) {
//            this.email = email;
//        }
//
//        public Optional<Email> getEmail() {
//            return Optional.ofNullable(email);
//        }
//
//        public void setAddress(Address address) {
//            this.address = address;
//        }
//
//        public Optional<Address> getAddress() {
//            return Optional.ofNullable(address);
//        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
//        public void setTags(Set<Tag> tags) {
//            this.tags = (tags != null) ? new HashSet<>(tags) : null;
//        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
//        public Optional<Set<Tag>> getTags() {
//            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
//        }

//        @Override
//        public boolean equals(Object other) {
//            // short circuit if same object
//            if (other == this) {
//                return true;
//            }
//
//            // instanceof handles nulls
//            if (!(other instanceof EditPersonDescriptor)) {
//                return false;
//            }
//
//            // state check
//            EditPersonDescriptor e = (EditPersonDescriptor) other;
//
//            return getName().equals(e.getName())
//                    && getPhone().equals(e.getPhone())
//                    && getEmail().equals(e.getEmail())
//                    && getAddress().equals(e.getAddress())
//                    && getTags().equals(e.getTags());
//        }
    }
}
