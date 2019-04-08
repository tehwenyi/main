package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.epiggy.TypicalExpenses.getTypicalExpenses;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysExpense;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import guitests.guihandles.ExpenseCardHandle;
import guitests.guihandles.ExpenseListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Item;
import seedu.address.model.expense.Name;

@Ignore
public class ExpenseListPanelTest extends GuiUnitTest {
    private static final ObservableList<Expense> TYPICAL_EXPENSES =
            FXCollections.observableList(getTypicalExpenses());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Expense> selectedExpense = new SimpleObjectProperty<>();
    private ExpenseListPanelHandle expenseListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EXPENSES);

        for (int i = 0; i < TYPICAL_EXPENSES.size(); i++) {
            expenseListPanelHandle.navigateToCard(TYPICAL_EXPENSES.get(i));
            Expense expectedExpense = TYPICAL_EXPENSES.get(i);
            ExpenseCardHandle actualCard = expenseListPanelHandle.getExpenseCardHandle(i);

            assertCardDisplaysExpense(expectedExpense, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedExpenseChanged_selectionChanges() {
        initUi(TYPICAL_EXPENSES);
        Expense secondExpense = TYPICAL_EXPENSES.get(INDEX_SECOND_PERSON.getZeroBased());
        guiRobot.interact(() -> selectedExpense.set(secondExpense));
        guiRobot.pauseForHuman();

        ExpenseCardHandle expectedExpense = expenseListPanelHandle
                .getExpenseCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        ExpenseCardHandle selectedExpense = expenseListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedExpense, selectedExpense);
    }

    /**
     * Verifies that creating and deleting large number of expenses in {@code ExpenseListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Expense> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of expense cards exceeded time limit");
    }

    /**
     * Returns a list of expenses containing {@code expenseCount} expenses that is used to populate the
     * {@code ExpenseListPanel}.
     */
    private ObservableList<Expense> createBackingList(int expenseCount) {
        ObservableList<Expense> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < expenseCount; i++) {
            Name name = new Name(i + "a");
            Cost cost = new Cost("5.0");
            Date date = new Date("05/04/2019");
            Item item = new Item(name, cost, Collections.emptySet());
            Expense expense = new Expense(item, date);
            backingList.add(expense);
        }
        return backingList;
    }

    /**
     * Initializes {@code expenseListPanelHandle} with a {@code ExpenseListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ExpenseListPanel}.
     */
    private void initUi(ObservableList<Expense> backingList) {
        ExpenseListPanel expenseListPanel =
                new ExpenseListPanel(backingList, selectedExpense, selectedExpense::set);
        uiPartRule.setUiPart(expenseListPanel);

        expenseListPanelHandle = new ExpenseListPanelHandle(getChildNode(expenseListPanel.getRoot(),
                ExpenseListPanelHandle.PERSON_LIST_VIEW_ID));
    }
}
