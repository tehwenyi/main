package seedu.address.logic.commands.epiggy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.epiggy.TypicalExpenses.BOWLING;
import static seedu.address.testutil.epiggy.TypicalExpenses.CLOTHES;
import static seedu.address.testutil.epiggy.TypicalExpenses.DUMPLING_SOUP;
import static seedu.address.testutil.epiggy.TypicalExpenses.IPHONE;
import static seedu.address.testutil.epiggy.TypicalExpenses.KARAOKE;
import static seedu.address.testutil.epiggy.TypicalExpenses.KFC;
import static seedu.address.testutil.epiggy.TypicalExpenses.MOVIE_AVENGERS;
import static seedu.address.testutil.epiggy.TypicalExpenses.STATIONARY;
import static seedu.address.testutil.epiggy.TypicalExpenses.getTypicalEpiggy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.ExpenseContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindExpenseCommand}.
 */
public class FindExpenseCommandTest {
    private Model model = new ModelManager(getTypicalEpiggy(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEpiggy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        ArgumentMultimap firstMap = ArgumentTokenizer.tokenize(" n/first", PREFIX_NAME);
        ArgumentMultimap secondMap = ArgumentTokenizer.tokenize(" n/second", PREFIX_NAME);
        ExpenseContainsKeywordsPredicate firstPredicate =
                new ExpenseContainsKeywordsPredicate(firstMap);
        ExpenseContainsKeywordsPredicate secondPredicate =
                new ExpenseContainsKeywordsPredicate(secondMap);

        FindExpenseCommand findFirstCommand = new FindExpenseCommand(firstPredicate);
        FindExpenseCommand findSecondCommand = new FindExpenseCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindExpenseCommand findFirstCommandCopy = new FindExpenseCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different expense -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noExpenseFound() {
        findExpenseCommandTester(" n/ ", 0,
                Collections.emptyList(), PREFIX_NAME);
    }

    @Test
    public void execute_multipleNameKeywords_multipleExpensesFound() {
        findExpenseCommandTester(" n/dumpling avengers clothes", 3,
                Arrays.asList(DUMPLING_SOUP, MOVIE_AVENGERS, CLOTHES), PREFIX_NAME);
    }

    @Test
    public void execute_zeroTagKeywords_noExpenseFound() {
        findExpenseCommandTester(" t/ ", 1,
                Collections.singletonList(IPHONE), PREFIX_NAME);
    }

    @Test
    public void execute_tagKeywords_noExpensesFound() {
        findExpenseCommandTester(" t/drinks", 5,
                Collections.emptyList(), PREFIX_TAG);
    }

    @Test
    public void execute_tagKeywords_multipleExpensesFound() {
        findExpenseCommandTester(" t/friends food", 5,
                Arrays.asList(DUMPLING_SOUP, KARAOKE, BOWLING, MOVIE_AVENGERS, KFC), PREFIX_TAG);
    }

    @Test
    public void execute_oneDateKeyword_noExpenseFound() {
        findExpenseCommandTester(" d/24/03/2019", 0,
                Collections.singletonList(MOVIE_AVENGERS), PREFIX_DATE);
    }

    @Test
    public void execute_oneDateKeyword_multipleExpensesFound() {
        findExpenseCommandTester(" d/26/04/2019", 2,
                Arrays.asList(MOVIE_AVENGERS, KFC), PREFIX_DATE);
    }

    @Test
    public void execute_oneDateKeyword_oneExpenseFound() {
        findExpenseCommandTester(" d/07/10/2018", 1,
                Collections.singletonList(KARAOKE), PREFIX_DATE);
    }

    @Test
    public void execute_multipleDateKeyword_multipleExpensesFound() {
        findExpenseCommandTester(" d/01/03/2019:30/04/2019", 6,
                Arrays.asList(DUMPLING_SOUP, STATIONARY, MOVIE_AVENGERS, CLOTHES, KFC, IPHONE), PREFIX_DATE);
    }
    // Clothes will be counted as long as it entered within the given dates.

    @Test
    public void execute_oneCostKeyword_noExpenseFound() {
        findExpenseCommandTester(" $/10.00", 0,
                Collections.emptyList(), PREFIX_COST);
    }

    @Test
    public void execute_oneCostKeyword_oneExpensesFound() {
        findExpenseCommandTester(" $/1799.00", 1,
                Collections.singletonList(IPHONE), PREFIX_COST);
    }

    @Test
    public void execute_multipleCostKeyword_multipleExpensesFound() {
        findExpenseCommandTester(" $/1.00:10.00", 4,
                Arrays.asList(DUMPLING_SOUP, STATIONARY, MOVIE_AVENGERS, KARAOKE), PREFIX_COST);
    }

    @Test
    public void execute_multipleKeyword_oneExpensesFound() {
        findExpenseCommandTester(" t/Friends $/10", 1,
                Collections.singletonList(KARAOKE), PREFIX_TAG, PREFIX_COST);
    }

    @Test
    public void execute_multipleKeyword_multipleExpensesFound() {
        findExpenseCommandTester(" t/food d/01/01/2019:30/04/2019", 2,
                Arrays.asList(DUMPLING_SOUP, KFC), PREFIX_TAG, PREFIX_DATE);
    }

    /**
     * Parses {@code userInput} into a {@code ExpenseContainsKeywordsPredicate}.
     */
    private void findExpenseCommandTester(String userInput, int expectedItems,
                                          List<Expense> expenses, Prefix... prefixes) {
        String expectedMessage = String.format(MESSAGE_EXPENSES_LISTED_OVERVIEW, expectedItems);
        ArgumentMultimap keywordsMap =
                ArgumentTokenizer.tokenize(userInput, prefixes);
        ExpenseContainsKeywordsPredicate predicate = new ExpenseContainsKeywordsPredicate(keywordsMap);
        FindExpenseCommand command = new FindExpenseCommand(predicate);
        expectedModel.updateFilteredExpensesList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expenses, model.getFilteredExpenseList());

    }
}
