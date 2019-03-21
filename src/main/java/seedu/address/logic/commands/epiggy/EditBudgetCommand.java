package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUDGETS;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.item.Period;
import seedu.address.model.epiggy.item.Price;


/**
 * Edits the current budget in ePiggy.
 */
public class EditBudgetCommand extends Command {

    public static final String COMMAND_WORD = "editBudget";
    public static final String COMMAND_ALIAS = "eb";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the current budget. ";

    public static final String MESSAGE_EDIT_BUDGET_SUCCESS = "Current budget updated";

    public static final String MESSAGE_NOT_EDITED = "Budget not edited as there are no changes.";

    private final EditBudgetDetails editBudgetDetails;

    public EditBudgetCommand(EditBudgetDetails editBudgetDetails) {
        requireNonNull(editBudgetDetails);
        this.editBudgetDetails = new EditBudgetDetails(editBudgetDetails);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Budget> lastShownBudgetList = model.getFilteredBudgetList();

        Budget budgetToEdit = lastShownBudgetList.get(lastShownBudgetList.size() - 1);
        Budget editedBudget = createEditedBudget(budgetToEdit, editBudgetDetails);

        model.setCurrentBudget(editedBudget);
        model.updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
        model.commitAddressBook();
        // Need to add the edited budget details in the String below
        return new CommandResult(String.format(MESSAGE_EDIT_BUDGET_SUCCESS));
    }

    /**
     * Creates and returns a {@code Budget} with the details of {@code budgetToEdit}
     * edited with {@code editBudgetDetails}.
     */
    private static Budget createEditedBudget(Budget budgetToEdit, EditBudgetDetails editBudgetDetails) {
        assert budgetToEdit != null;

        Price updatedAmount = editBudgetDetails.getAmount().orElse(budgetToEdit.getPrice());
        Date updatedStartDate = editBudgetDetails.getStartDate().orElse((budgetToEdit.getStartDate()));
        Period updatedPeriod = editBudgetDetails.getPeriod().orElse(budgetToEdit.getPeriod());

        return new Budget(updatedAmount, updatedPeriod, updatedStartDate);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditBudgetDetails {
        private Price amount;
        private Date startDate;
        private Period period;

        public EditBudgetDetails() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBudgetDetails(EditBudgetDetails toCopy) {
            setAmount(toCopy.amount);
            setStartDate(toCopy.startDate);
            setPeriod(toCopy.period);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(amount, startDate, period);
        }

        public void setAmount(Price amount) {
            this.amount = amount;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public void setPeriod(Period period) {
            this.period = period;
        }

        public Optional<Price> getAmount() {
            return Optional.ofNullable(amount);
        }

        public Optional<Date> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public Optional<Period> getPeriod() {
            return Optional.ofNullable(period);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBudgetDetails)) {
                return false;
            }

            // state check
            EditBudgetDetails e = (EditBudgetDetails) other;

            return getAmount().equals(e.getAmount())
                    && getStartDate().equals(e.getStartDate())
                    && getPeriod().equals(e.getPeriod());
        }
    }
}
