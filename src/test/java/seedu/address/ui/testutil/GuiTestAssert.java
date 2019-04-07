package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ExpenseCardHandle;
import guitests.guihandles.ExpenseListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.Expense.Expense;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(ExpenseCardHandle expectedCard, ExpenseCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getCost(), actualCard.getCost());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedExpense}.
     */
    public static void assertCardDisplaysExpense(Expense expectedExpense, ExpenseCardHandle actualCard) {
        assertEquals(expectedExpense.getItem().getName().name, actualCard.getName());
        assertEquals(expectedExpense.getItem().getCost().toString(), actualCard.getCost());
        assertEquals(expectedExpense.getDate().toString(), actualCard.getDate());
        assertEquals(expectedExpense.getItem().getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code expenseListPanelHandle} displays the details of {@code expenses} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ExpenseListPanelHandle expenseListPanelHandle, Expense... expenses) {
        for (int i = 0; i < expenses.length; i++) {
            expenseListPanelHandle.navigateToCard(i);
            assertCardDisplaysExpense(expenses[i], expenseListPanelHandle.getExpenseCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code expenseListPanelHandle} displays the details of {@code expenses} correctly and
     * in the correct order.
     */
    public static void assertListMatching(ExpenseListPanelHandle expenseListPanelHandle, List<Expense> expenses) {
        assertListMatching(expenseListPanelHandle, expenses.toArray(new Expense[0]));
    }

    /**
     * Asserts the size of the list in {@code expenseListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(ExpenseListPanelHandle expenseListPanelHandle, int size) {
        int numberOfPeople = expenseListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
