package seedu.address.logic.commands.epiggy;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Predicate;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.epiggy.EditBudgetCommand.EditBudgetDetails;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EPiggy;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyEPiggy;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.Savings;
import seedu.address.model.person.Person;
import seedu.address.testutil.epiggy.BudgetBuilder;
import seedu.address.testutil.epiggy.EditBudgetDetailsBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.epiggy.EditBudgetCommand.createEditedBudget;
import static seedu.address.testutil.TypicalBudgets.getTypicalEPiggy;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditBudgetCommand.
 */
@Ignore
public class EditBudgetCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Date todaysDate = new Date();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        ModelStubWithOneCurrentBudget modelStub = new ModelStubWithOneCurrentBudget();

        EditBudgetDetails details = DESC_ONE;
        Budget budgetToEdit = new BudgetBuilder().build();
        Budget editedBudget = createEditedBudget(budgetToEdit, details);
        CommandResult commandResult = new EditBudgetCommand(details).execute(modelStub, commandHistory);

        assertEquals(String.format(EditBudgetCommand.MESSAGE_EDIT_BUDGET_SUCCESS, editedBudget),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(editedBudget), modelStub.budgets);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

//    @Test
//    public void execute_someFieldsSpecifiedUnfilteredList_success() {
//        Index indexLastBudget = Index.fromOneBased(model.getFilteredBudgetList().size());
//        Budget lastBudget = model.getFilteredBudgetList().get(indexLastBudget.getZeroBased());
//
//        BudgetBuilder personInList = new BudgetBuilder(lastBudget);
//        Budget editedBudget = personInList.withAmount(VALID_AMOUNT_ONE).withPeriod(VALID_PERIOD_ONE)
//                .withDate(VALID_DATE_HUSBAND).build();
//
//        EditBudgetDetails details = new EditBudgetDetailsBuilder().withAmount(VALID_AMOUNT_ONE)
//                .withPeriod(VALID_PERIOD_ONE).withDate(VALID_DATE_HUSBAND).build();
//        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(indexLastBudget, details);
//
//        String expectedMessage = String.format(EditBudgetCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedBudget);
//
//        Model expectedModel = new ModelManager(new EPiggy(model.getEPiggy()), new UserPrefs());
//        expectedModel.setBudget(lastBudget, editedBudget);
//        expectedModel.commitEPiggy();
//
//        assertCommandSuccess(editBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_noFieldSpecifiedUnfilteredList_success() {
//        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(INDEX_FIRST_BUDGET, new EditBudgetDetails());
//        Budget editedBudget = model.getFilteredBudgetList().get(INDEX_FIRST_BUDGET.getZeroBased());
//
//        String expectedMessage = String.format(EditBudgetCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedBudget);
//
//        Model expectedModel = new ModelManager(new EPiggy(model.getEPiggy()), new UserPrefs());
//        expectedModel.commitEPiggy();
//
//        assertCommandSuccess(editBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_filteredList_success() {
//        showBudgetAtIndex(model, INDEX_FIRST_BUDGET);
//
//        Budget personInFilteredList = model.getFilteredBudgetList().get(INDEX_FIRST_BUDGET.getZeroBased());
//        Budget editedBudget = new BudgetBuilder(personInFilteredList).withAmount(VALID_AMOUNT_ONE).build();
//        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(INDEX_FIRST_BUDGET,
//                new EditBudgetDetailsBuilder().withAmount(VALID_AMOUNT_ONE).build());
//
//        String expectedMessage = String.format(EditBudgetCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedBudget);
//
//        Model expectedModel = new ModelManager(new EPiggy(model.getEPiggy()), new UserPrefs());
//        expectedModel.setBudget(model.getFilteredBudgetList().get(0), editedBudget);
//        expectedModel.commitEPiggy();
//
//        assertCommandSuccess(editBudgetCommand, model, commandHistory, expectedMessage, expectedModel);
//    }
//
//    @Ignore
//    @Test
//    public void execute_duplicateBudgetUnfilteredList_failure() {
//        Budget firstBudget = model.getFilteredBudgetList().get(INDEX_FIRST_BUDGET.getZeroBased());
//        EditBudgetDetails details = new EditBudgetDetailsBuilder(firstBudget).build();
//        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(INDEX_SECOND_BUDGET, details);
//
//        assertCommandFailure(editBudgetCommand, model, commandHistory, EditBudgetCommand.MESSAGE_DUPLICATE_PERSON);
//    }
//
//    @Ignore
//    @Test
//    public void execute_duplicateBudgetFilteredList_failure() {
//        showBudgetAtIndex(model, INDEX_FIRST_BUDGET);
//
//        // edit person in filtered list into a duplicate in address book
//        Budget personInList = model.getEPiggy().getBudgetList().get(INDEX_SECOND_BUDGET.getZeroBased());
//        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(INDEX_FIRST_BUDGET,
//                new EditBudgetDetailsBuilder(personInList).build());
//
//        assertCommandFailure(editBudgetCommand, model, commandHistory, EditBudgetCommand.MESSAGE_DUPLICATE_PERSON);
//    }

    @Test
    public void execute_noCurrentBudget_failure() throws Exception {
        EditBudgetDetails details = new EditBudgetDetailsBuilder().withAmount(VALID_AMOUNT_ONE).build();

        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(details);
        ModelStubWithNoCurrentBudget modelStub = new ModelStubWithNoCurrentBudget();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EditBudgetCommand.MESSAGE_EDIT_BUDGET_FAIL);
        editBudgetCommand.execute(modelStub, commandHistory);
    }

//    /**
//     * Edit filtered list where index is larger than size of filtered list,
//     * but smaller than size of address book
//     */
//    @Ignore
//    @Test
//    public void execute_invalidBudgetIndexFilteredList_failure() {
//        showBudgetAtIndex(model, INDEX_FIRST_BUDGET);
//        Index outOfBoundIndex = INDEX_SECOND_BUDGET;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getEPiggy().getBudgetList().size());
//
//        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(outOfBoundIndex,
//                new EditBudgetDetailsBuilder().withAmount(VALID_AMOUNT_ONE).build());
//
//        assertCommandFailure(editBudgetCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    @Ignore
//    @Test
//    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
//        Budget editedBudget = new BudgetBuilder().build();
//        Budget personToEdit = model.getFilteredBudgetList().get(INDEX_FIRST_BUDGET.getZeroBased());
//        EditBudgetDetails details = new EditBudgetDetailsBuilder(editedBudget).build();
//        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(INDEX_FIRST_BUDGET, details);
//        Model expectedModel = new ModelManager(new EPiggy(model.getEPiggy()), new UserPrefs());
//        expectedModel.setBudget(personToEdit, editedBudget);
//        expectedModel.commitEPiggy();
//
//        // edit -> first person edited
//        editBudgetCommand.execute(model, commandHistory);
//
//        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
//        expectedModel.undoEPiggy();
//        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
//
//        // redo -> same first person edited again
//        expectedModel.redoEPiggy();
//        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBudgetList().size() + 1);
//        EditBudgetDetails details = new EditBudgetDetailsBuilder().withAmount(VALID_AMOUNT_ONE).build();
//        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(outOfBoundIndex, details);
//
//        // execution failed -> address book state not added into model
//        assertCommandFailure(editBudgetCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//
//        // single address book state in model -> undoCommand and redoCommand fail
//        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
//        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
//    }
//
//    /**
//     * 1. Edits a {@code Budget} from a filtered list.
//     * 2. Undo the edit.
//     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
//     * unfiltered list is different from the index at the filtered list.
//     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
//     */
//    @Test
//    public void executeUndoRedo_validIndexFilteredList_sameBudgetEdited() throws Exception {
//        Budget editedBudget = new BudgetBuilder().build();
//        EditBudgetDetails details = new EditBudgetDetailsBuilder(editedBudget).build();
//        EditBudgetCommand editBudgetCommand = new EditBudgetCommand(INDEX_FIRST_BUDGET, details);
//        Model expectedModel = new ModelManager(new EPiggy(model.getEPiggy()), new UserPrefs());
//
//        showBudgetAtIndex(model, INDEX_SECOND_BUDGET);
//        Budget personToEdit = model.getFilteredBudgetList().get(INDEX_FIRST_BUDGET.getZeroBased());
//        expectedModel.setBudget(personToEdit, editedBudget);
//        expectedModel.commitEPiggy();
//
//        // edit -> edits second person in unfiltered person list / first person in filtered person list
//        editBudgetCommand.execute(model, commandHistory);
//
//        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
//        expectedModel.undoEPiggy();
//        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
//
//        assertNotEquals(model.getFilteredBudgetList().get(INDEX_FIRST_BUDGET.getZeroBased()), personToEdit);
//        // redo -> edits same second person in unfiltered person list
//        expectedModel.redoEPiggy();
//        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
//    }

    @Test
    public void equals() {
        final EditBudgetCommand standardCommand = new EditBudgetCommand(DESC_ONE);

        // same values -> returns true
        EditBudgetDetails copyDetails = new EditBudgetDetails(DESC_ONE);
        EditBudgetCommand commandWithSameValues = new EditBudgetCommand(copyDetails);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different details -> returns false
        assertFalse(standardCommand.equals(new EditBudgetCommand(DESC_TWO)));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public ObservableList<Budget> getBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortExpenses(String keyword) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getEPiggyFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEPiggyFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAllowance(Allowance allowance) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Expense> selectedExpenseProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBudget(int index, Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean budgetsOverlap(Date startDate, Date endDate, Budget earlierBudget) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deleteBudgetAtIndex(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExpense(Expense toDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getCurrentBudgetIndex() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SimpleObjectProperty<Savings> getSavings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SimpleObjectProperty<Goal> getGoal() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGoal(Goal goal) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEPiggy(ReadOnlyEPiggy newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEPiggy getEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentBudget(Budget editedBudget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBudgetList(Predicate<Budget> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExpensesList(Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitEPiggy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Person> selectedPersonProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpense(Expense target, Expense editedExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Expense getSelectedExpense() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithOneCurrentBudget extends ModelStub {
        final ArrayList<Budget> budgets = new ArrayList<>();
        private Date todaysDate = new Date();

        ModelStubWithOneCurrentBudget() {
            Budget currentBudget = new BudgetBuilder().withDate(todaysDate).build();
            budgets.add(currentBudget);
        }

        @Override
        public int getCurrentBudgetIndex() {
            return 0;
        }

        @Override
        public void setCurrentBudget(Budget editedBudget) {
            budgets.set(0, editedBudget);
        }

        @Override
        public void updateFilteredBudgetList(Predicate<Budget> predicate) {
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            return FXCollections.observableArrayList(budgets);
        }

        @Override
        public void commitEPiggy() {
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithNoCurrentBudget extends ModelStub {
        final ArrayList<Budget> budgets = new ArrayList<>();

        @Override
        public int getCurrentBudgetIndex() {
            return -1;
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            return FXCollections.observableArrayList(budgets);
        }
    }
}
