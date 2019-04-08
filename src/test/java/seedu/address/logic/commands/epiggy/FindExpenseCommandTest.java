package seedu.address.logic.commands.epiggy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseContainsKeywordsPredicate;
import seedu.address.testutil.epiggy.TypicalExpenses;

/**
 * Contains integration tests (interaction with the Model) for {@code FindExpenseCommand}.
 */
public class FindExpenseCommandTest {
    private Model model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());

    @Test
    public void equals() {
        ArgumentMultimap firstMap = ArgumentTokenizer.tokenize(" n/first", CliSyntax.PREFIX_NAME);
        ArgumentMultimap secondMap = ArgumentTokenizer.tokenize(" n/second", CliSyntax.PREFIX_NAME);
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
        // assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different expense -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noExpenseFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" n/ ", 0,
                Collections.emptyList(), CliSyntax.PREFIX_NAME);
    }

    @Test
    public void execute_multipleNameKeywords_multipleExpensesFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" n/dumpling avengers clothes ", 3,
                Arrays.asList(TypicalExpenses.DUMPLING_SOUP, TypicalExpenses.MOVIE_AVENGERS, TypicalExpenses.CLOTHES), CliSyntax.PREFIX_NAME);
    }

    @Test
    public void execute_zeroTagKeywords_noExpenseFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/ ", 0,
                Collections.emptyList(), CliSyntax.PREFIX_NAME);
    }

    @Test
    public void execute_tagKeywords_noExpensesFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/drinks ", 5,
                Collections.emptyList(), CliSyntax.PREFIX_TAG);
    }

    @Test
    public void execute_tagKeywords_multipleExpensesFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/friends t/food ", 5,
                Arrays.asList(TypicalExpenses.DUMPLING_SOUP, TypicalExpenses.MOVIE_AVENGERS, TypicalExpenses.KARAOKE, TypicalExpenses.KFC, TypicalExpenses.BOWLING), CliSyntax.PREFIX_TAG);
    }

    @Test
    public void execute_oneDateKeyword_noExpenseFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" d/24/03/2019 ", 0,
                Collections.emptyList(), CliSyntax.PREFIX_DATE);
    }

    @Test
    public void execute_oneDateKeyword_multipleExpensesFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" d/04/26/2019 ", 2,
                Arrays.asList(TypicalExpenses.MOVIE_AVENGERS, TypicalExpenses.KFC), CliSyntax.PREFIX_DATE);
    }

    @Test
    public void execute_multipleDateKeyword_multipleExpensesFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" d/03/01/2019:04/30/2019 ", 6,
                Arrays.asList(TypicalExpenses.DUMPLING_SOUP, TypicalExpenses.STATIONARY, TypicalExpenses.MOVIE_AVENGERS, TypicalExpenses.CLOTHES, TypicalExpenses.KFC, TypicalExpenses.IPHONE), CliSyntax.PREFIX_DATE);
    }
    // Clothes will be counted as long as it entered within the given dates.

    @Test
    public void execute_oneCostKeyword_noExpenseFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" $/12.00 ", 0,
                Collections.emptyList(), CliSyntax.PREFIX_COST);
    }

    @Test
    public void execute_oneCostKeyword_oneExpensesFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" $/1799.00 ", 1,
                Collections.singletonList(TypicalExpenses.IPHONE), CliSyntax.PREFIX_COST);
    }

    @Test
    public void execute_multipleCostKeyword_multipleExpensesFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" $/1.00:10.00 ", 4,
                Arrays.asList(TypicalExpenses.DUMPLING_SOUP, TypicalExpenses.STATIONARY, TypicalExpenses.MOVIE_AVENGERS, TypicalExpenses.KARAOKE, TypicalExpenses.BOWLING), CliSyntax.PREFIX_COST);
    }

    @Test
    public void execute_multipleKeyword_oneExpensesFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/Friends $/10 ", 1,
                Collections.singletonList(TypicalExpenses.KARAOKE), CliSyntax.PREFIX_TAG, CliSyntax.PREFIX_COST);
    }

    @Test
    public void execute_multipleKeyword_multipleExpensesFound() {
        model = new ModelManager(TypicalExpenses.getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/food d/01/01/2019:04/30/2019 ", 2,
                Arrays.asList(TypicalExpenses.DUMPLING_SOUP, TypicalExpenses.KFC), CliSyntax.PREFIX_TAG, CliSyntax.PREFIX_DATE);
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
        command.execute(model, null);
        expectedModel.updateFilteredExpensesList(predicate);
        // assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expenses, model.getFilteredExpenseList());

    }
}
