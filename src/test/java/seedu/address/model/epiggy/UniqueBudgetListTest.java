package seedu.address.model.epiggy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_TWO;
import static seedu.address.testutil.TypicalBudgets.ONE;
import static seedu.address.testutil.TypicalBudgets.TWO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.testutil.epiggy.BudgetBuilder;

public class UniqueBudgetListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueBudgetList uniqueBudgetList = new UniqueBudgetList();

    @Test
    public void addAtIndex_nullBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBudgetList.addAtIndex(0, null);
    }

    @Test
    public void replaceAtIndex_nullBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBudgetList.replaceAtIndex(0, null);
    }

    @Test
    public void replaceAtIndex_indexTooBig_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        uniqueBudgetList.replaceAtIndex(UniqueBudgetList.MAXIMUM_SIZE, ONE);
    }

    @Test
    public void replaceAtIndex_negativeIndex_throwsIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
        uniqueBudgetList.replaceAtIndex(-1, ONE);
    }

    @Test
    public void replaceAtIndex_editedBudgetIsSameBudget_success() {
        uniqueBudgetList.addAtIndex(0, ONE);
        uniqueBudgetList.replaceAtIndex(0, ONE);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.addAtIndex(0, ONE);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void contains_nullBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBudgetList.contains(null);
    }

    @Test
    public void contains_budgetNotInList_returnsFalse() {
        assertFalse(uniqueBudgetList.contains(ONE));
    }

    @Test
    public void contains_budgetInList_returnsTrue() {
        uniqueBudgetList.add(ONE);
        assertTrue(uniqueBudgetList.contains(ONE));
    }

    @Test
    public void contains_budgetWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBudgetList.add(ONE);
        Budget editedAlice = new BudgetBuilder(ONE).withAddress(VALID_ADDRESS_TWO).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueBudgetList.contains(editedAlice));
    }

    @Test
    public void add_nullBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBudgetList.add(null);
    }

    @Test
    public void setBudget_nullTargetBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBudgetList.setBudget(null, ONE);
    }

    @Test
    public void setBudget_nullEditedBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBudgetList.setBudget(ONE, null);
    }

    @Test
    public void setBudget_targetBudgetNotInList_throwsBudgetNotFoundException() {
        thrown.expect(BudgetNotFoundException.class);
        uniqueBudgetList.setBudget(ONE, ONE);
    }

    @Test
    public void setBudget_editedBudgetIsSameBudget_success() {
        uniqueBudgetList.add(ONE);
        uniqueBudgetList.setBudget(ONE, ONE);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(ONE);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasSameIdentity_success() {
        uniqueBudgetList.add(ONE);
        Budget editedAlice = new BudgetBuilder(ONE).withAddress(VALID_ADDRESS_TWO).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueBudgetList.setBudget(ONE, editedAlice);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(editedAlice);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasDifferentIdentity_success() {
        uniqueBudgetList.add(ONE);
        uniqueBudgetList.setBudget(ONE, TWO);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(TWO);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasNonUniqueIdentity_throwsDuplicateBudgetException() {
        uniqueBudgetList.add(ONE);
        uniqueBudgetList.add(TWO);
        thrown.expect(DuplicateBudgetException.class);
        uniqueBudgetList.setBudget(ONE, TWO);
    }

    @Test
    public void remove_nullBudget_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBudgetList.remove(null);
    }

    @Test
    public void remove_budgetDoesNotExist_throwsBudgetNotFoundException() {
        thrown.expect(BudgetNotFoundException.class);
        uniqueBudgetList.remove(ONE);
    }

    @Test
    public void remove_existingBudget_removesBudget() {
        uniqueBudgetList.add(ONE);
        uniqueBudgetList.remove(ONE);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_nullUniqueBudgetList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBudgetList.setBudgets((UniqueBudgetList) null);
    }

    @Test
    public void setBudgets_uniqueBudgetList_replacesOwnListWithProvidedUniqueBudgetList() {
        uniqueBudgetList.add(ONE);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(TWO);
        uniqueBudgetList.setBudgets(expectedUniqueBudgetList);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBudgetList.setBudgets((List<Budget>) null);
    }

    @Test
    public void setBudgets_list_replacesOwnListWithProvidedList() {
        uniqueBudgetList.add(ONE);
        List<Budget> budgetList = Collections.singletonList(TWO);
        uniqueBudgetList.setBudgets(budgetList);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(TWO);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_listWithDuplicateBudgets_throwsDuplicateBudgetException() {
        List<Budget> listWithDuplicateBudgets = Arrays.asList(ONE, ONE);
        thrown.expect(DuplicateBudgetException.class);
        uniqueBudgetList.setBudgets(listWithDuplicateBudgets);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueBudgetList.asUnmodifiableObservableList().remove(0);
    }
}
