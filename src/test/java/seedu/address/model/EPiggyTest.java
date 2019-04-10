package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.epiggy.TypicalExpenses.BOWLING;
import static seedu.address.testutil.epiggy.TypicalExpenses.getTypicalEPiggy;

import java.util.Collection;
import java.util.Collections;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expense.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Goal;
import seedu.address.model.expense.item.Cost;
import seedu.address.model.expense.item.Item;

public class EPiggyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EPiggy ePiggy = new EPiggy();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), ePiggy.getExpenseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ePiggy.resetData(null);
    }

    @Ignore
    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        EPiggy newData = getTypicalEPiggy();
        ePiggy.resetData(newData);
        assertEquals(newData, ePiggy);
    }

    //    @Test
    //    public void resetData_withDuplicateExpenses_throwsDuplicateExpensesException() {
    //        // Two persons with the same identity fields
    //        expense editedAlice = new ExpensesBuilder(BOWLING)
    //        .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
    //                .build();
    //        List<expense> newExpensess = Arrays.asList(BOWLING, editedAlice);
    //        AddressBookStub newData = new AddressBookStub(newExpensess);
    //
    //        thrown.expect(DuplicateExpensesException.class);
    //        ePiggy.resetData(newData);
    //    }

    //    @Test
    //    public void hasExpenses_nullExpenses_throwsNullPointerException() {
    //        thrown.expect(NullPointerException.class);
    //        ePiggy.hasExpenses(null);
    //    }
    //
    //    @Test
    //    public void hasExpenses_personNotInAddressBook_returnsFalse() {
    //        assertFalse(ePiggy.hasExpenses(BOWLING));
    //    }
    //
    //    @Test
    //    public void hasExpenses_personInAddressBook_returnsTrue() {
    //        ePiggy.addExpense(BOWLING);
    //        assertTrue(ePiggy.hasExpense(BOWLING));
    //    }
    //
    //    @Test
    //    public void hasExpenses_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
    //        ePiggy.addExpenses(BOWLING);
    //        Expenses editedAlice = new ExpensesBuilder(BOWLING)
    //        .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
    //                .build();
    //        assertTrue(ePiggy.hasExpenses(editedAlice));
    //    }

    @Test
    public void getExpensesList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        ePiggy.getExpenseList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        ePiggy.addListener(listener);
        ePiggy.addExpense(BOWLING);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        ePiggy.addListener(listener);
        ePiggy.removeListener(listener);
        ePiggy.addExpense(BOWLING);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyEPiggy whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyEPiggy {
        private final ObservableList<Expense> persons = FXCollections.observableArrayList();
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
        private final ObservableList<Item> items = FXCollections.observableArrayList();
        private ObservableList<Budget> budgets; //TODO
        private SimpleObjectProperty<Goal> goal;


        AddressBookStub(Collection<Expense> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Expense> getExpenseList() {
            return FXCollections.unmodifiableObservableList(expenses);
        }

        @Override
        public ObservableList<Item> getItemList() {
            return FXCollections.unmodifiableObservableList(items);
        }

        //        @Override
        //        public ObservableValue<Budget> getBudget() {
        //            return budget;
        // }

        @Override
        public ObservableList<Budget> getBudgetList() {
            return budgets;
        }

        @Override
        public SimpleObjectProperty<Cost> getSavings() {
            return null;
        }

        @Override
        public SimpleObjectProperty<Goal> getGoal() {
            return null;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
