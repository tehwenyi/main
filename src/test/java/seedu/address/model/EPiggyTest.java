package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.Goal;

import seedu.address.model.epiggy.Savings;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

@Ignore
public class EPiggyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EPiggy EPiggy = new EPiggy();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), EPiggy.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        EPiggy.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        EPiggy newData = getTypicalAddressBook();
        EPiggy.resetData(newData);
        assertEquals(newData, EPiggy);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        thrown.expect(DuplicatePersonException.class);
        EPiggy.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        EPiggy.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(EPiggy.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        EPiggy.addPerson(ALICE);
        assertTrue(EPiggy.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        EPiggy.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(EPiggy.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        EPiggy.getPersonList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        EPiggy.addListener(listener);
        EPiggy.addPerson(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        EPiggy.addListener(listener);
        EPiggy.removeListener(listener);
        EPiggy.addPerson(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyEPiggy whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyEPiggy {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Expense> expenses = FXCollections.observableArrayList();
        private final ObservableList<Item> items = FXCollections.observableArrayList();
        private ObservableList<Budget> budgets; //TODO
        private SimpleObjectProperty<Goal> goal;
        private SimpleObjectProperty<Savings> savings;


        AddressBookStub(Collection<Person> persons) {
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
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Budget> getBudgetList() {
            return budgets;
        }

        @Override
        public SimpleObjectProperty<Savings> getSavings() {
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
