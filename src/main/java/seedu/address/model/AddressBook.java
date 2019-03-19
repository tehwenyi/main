package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.epiggy.Allowance;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.Goal;
import seedu.address.model.epiggy.Savings;
import seedu.address.model.epiggy.item.Item;
import seedu.address.model.epiggy.item.Period;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final ObservableList<Expense> expenses;
    private final ObservableList<Item> items;
    private final ObservableList<Budget> budgetList;
    private SimpleObjectProperty<Budget> budget;
    private SimpleObjectProperty<Goal> goal;
    private SimpleObjectProperty<Savings> savings;
    private final UniquePersonList persons;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        expenses = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList();
        budgetList = FXCollections.observableArrayList();
        persons = new UniquePersonList();
        budget = new SimpleObjectProperty<>(new Budget());
        goal = new SimpleObjectProperty<>();
        savings = new SimpleObjectProperty<>(new Savings());

    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
        indicateModified();
    }

    /**
     * Adds an expense to the expense book.
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
        Savings s = savings.get();
        s.deductSavings(expense.getItem().getPrice().getAmount());
        savings.set(s);

        if (!budgetList.isEmpty()) {
            // have to update last budget
            if (budgetIsNotUpdated()) {
                createNewBudgetTillUpdated(expense);
            } else {
                int latestIndex = budgetList.size() - 1;
                Budget latestBudget = budgetList.get(latestIndex);
                latestBudget.deductRemainingAmount(expense.getItem().getPrice());
                budgetList.set(latestIndex, latestBudget);
            }
        }
        indicateModified();
    }

    /**
     * Creates a new Budget everytime it's needed.
     */
    private void createNewBudgetTillUpdated(Expense expense) {
        // Creates new budgets if the expense date is over the endDate of the current budgets
        int k = budgetList.size() - 1;
        Budget previousBudget = budgetList.get(k);
        // Continue looping as long as the expense's date is later than the latest budget's end date
        do {
            // Sets the remaining days of the previous budget to 0 when creating a new budget
            previousBudget.setRemainingDays(new Period(0));
            budgetList.set(k, previousBudget);
            // Create new budget based on previous budget
            Calendar cal = Calendar.getInstance();
            cal.setTime(previousBudget.getEndDate());
            cal.add(Calendar.DATE, 1);
            Date previousBudgetEndDatePlusOne = cal.getTime();
            Budget b = new Budget(previousBudget.getPrice(), previousBudget.getPeriod(), previousBudgetEndDatePlusOne);
            // Update the budget based on current expenses
            budgetList.add(updateToBeAddedBudgetBasedOnExpenses(b));
            previousBudget = budgetList.get(k + 1);
            k++;
        } while (budgetIsNotUpdated());
    }

    /**
     * Adds an allowance to the expense book.
     * @param allowance to be added.
     */
    public void addAllowance(Allowance allowance) {
        expenses.add(allowance);
        Savings s = savings.get();
        s.addSavings(allowance.getItem().getPrice().getAmount());
        savings.set(s);
        indicateModified();
    }

    public SimpleObjectProperty<Savings> getSavings() {
        return savings;
    }

    /**
     * Sets a budget for ePiggy.
     */
    public void setBudget(Budget budget) {
        if (budget.getPrice().getAmount() == 0) {
            this.budget.setValue(budget);
        } else {
            // cannot create budget
        }
        indicateModified();
    }

    /**
     * Adds a budget to the budgetList.
     * Only called when there's no budget currently.
     */
    public void addBudget(Budget budget) {
        budget = updateToBeAddedBudgetBasedOnExpenses(budget);
        budgetList.add(budget);
        if (budgetIsNotUpdated()) {
            // Create a new budget based on the latest expense
            createNewBudgetTillUpdated(sortExpensesByDate().get(expenses.size() - 1));
        }
        indicateModified();
    }

    /**
     * Updated the budget to be added based on the current list of expenses before it is added.
     */
    private Budget updateToBeAddedBudgetBasedOnExpenses(Budget budget) {
        SortedList<Expense> sortedExpensesByDate = sortExpensesByDate();
        ListIterator<Expense> iterator = sortedExpensesByDate.listIterator();
        while (iterator.hasNext()) {
            Expense expense = iterator.next();
            if (expense.getDate().after(budget.getStartDate())) {
                if (!budget.getEndDate().before(expense.getDate())) {
                    budget.deductRemainingAmount(expense.getItem().getPrice());
                    long diffInMillies = Math.abs(budget.getEndDate().getTime() - expense.getDate().getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    budget.setRemainingDays(new Period((int) diff));
                    System.out.println("CHECK: the long diff and int diff: " + diff + (int) diff);
                } else {
                    // Should continue adding budget if expenses > budgeted end date
                    // TODO Expenses might not be sorted by date though
                    // There might not be any expenses + there might not be any budget before this
                    return budget;
                }
            }
        }
        return budget;
    }

    /**
     * Sorts Expenses according to Date.
     * @return SortedList of Expenses
     */
    private SortedList<Expense> sortExpensesByDate() {
        return expenses.sorted(new Comparator<Expense>() {
                public int compare(Expense e1, Expense e2) {
                    if (e1.getDate() == null || e2.getDate() == null) {
                        return 0;
                    }
                    return e1.getDate().compareTo(e2.getDate());
                }
            });
    }

    /**
     * Checks if the budget is updated according to the Expense List.
     * Only invoked if there is at least one budget.
     * @return True if budget is not updated.
     */
    private boolean budgetIsNotUpdated() {
        SortedList<Expense> sortedExpensesByDate = sortExpensesByDate();
        Expense latestExpense = sortedExpensesByDate.get(sortedExpensesByDate.size() - 1);
        Date lastBudgetedDate = budgetList.get(budgetList.size() - 1).getEndDate();
        Date lastExpenseDate = latestExpense.getDate();
        return (lastExpenseDate.after(lastBudgetedDate));
    }

    /**
     * Gets the current budget for ePiggy.
     */
    public SimpleObjectProperty<Budget> getBudget() {
        return this.budget;
    }

    /**
     * Gets the current budget for ePiggy.
     */
    public ObservableList<Budget> getBudgetList() {
        return this.budgetList;
    }

    /**
     * Checks if there is already a budget in AddressBook.
     */
    public boolean hasBudget() {
        if (budgetList.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Sets the saving goal for ePiggy.
     */
    public void setGoal(Goal goal) {
        this.goal.setValue(goal);
        indicateModified();
    }

    /**
     * Get the saving goal for ePiggy.
     */
    public SimpleObjectProperty<Goal> getGoal() {
        return goal;
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return FXCollections.unmodifiableObservableList(expenses);
    }

    @Override
    public ObservableList<Item> getItemList() {
        return FXCollections.unmodifiableObservableList(items);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
