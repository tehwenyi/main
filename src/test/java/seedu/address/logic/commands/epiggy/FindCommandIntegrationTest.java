package seedu.address.logic.commands.epiggy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXPENSES_LISTED_OVERVIEW;
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
import static seedu.address.testutil.epiggy.TypicalExpenses.getTypicalEPiggy;

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
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.ExpenseContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandIntegrationTest {
    private Model model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEPiggy(), new UserPrefs());

    @Test
    public void equals() {
        ArgumentMultimap firstMap = ArgumentTokenizer.tokenize(" n/first", PREFIX_NAME);
        ArgumentMultimap secondMap = ArgumentTokenizer.tokenize(" n/second", PREFIX_NAME);
        ExpenseContainsKeywordsPredicate firstPredicate =
                new ExpenseContainsKeywordsPredicate(firstMap);
        ExpenseContainsKeywordsPredicate secondPredicate =
                new ExpenseContainsKeywordsPredicate(secondMap);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
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
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" n/ ", 8,
                Arrays.asList(DUMPLING_SOUP, STATIONARY, MOVIE_AVENGERS, KARAOKE, CLOTHES, KFC,
                        BOWLING, IPHONE), CliSyntax.PREFIX_NAME);
    }

    @Test
    public void execute_multipleNameKeywords_multipleExpensesFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" n/dumpling n/avengers n/clothes ", 0,
                Collections.emptyList(), CliSyntax.PREFIX_NAME);
    }

    @Test
    public void execute_zeroTagKeywords_noExpenseFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/ ", 0,
                Collections.emptyList(), PREFIX_NAME);
    }

    @Test
    public void execute_tagKeywords_noExpensesFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/drinks ", 0,
                Collections.emptyList(), CliSyntax.PREFIX_TAG);
    }

    @Test
    public void execute_tagKeywords_multipleExpensesFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/friends t/food ", 0,
                Collections.emptyList(), CliSyntax.PREFIX_TAG);
    }

    @Test
    public void execute_oneDateKeyword_noExpenseFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" d/24/03/2019 ", 0,
                Collections.emptyList(), PREFIX_DATE);
    }

    @Test
    public void execute_oneDateKeyword_multipleExpensesFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" d/26/04/2019 ", 2,
                Arrays.asList(MOVIE_AVENGERS, KFC), PREFIX_DATE);
    }

    @Test
    public void execute_multipleDateKeyword_multipleExpensesFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" d/01/03/2019:30/04/2019 ", 6,
                Arrays.asList(DUMPLING_SOUP, STATIONARY, MOVIE_AVENGERS, CLOTHES, KFC, IPHONE), PREFIX_DATE);
    }
    // Clothes will be counted as long as it entered within the given dates.

    @Test
    public void execute_oneCostKeyword_noExpenseFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" $/12.00 ", 0,
                Collections.emptyList(), PREFIX_COST);
    }

    @Test
    public void execute_oneCostKeyword_oneExpensesFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" $/1799.00 ", 1,
                Collections.singletonList(IPHONE), PREFIX_COST);
    }

    @Test
    public void execute_multipleCostKeyword_multipleExpensesFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" $/1.00:10.00 ", 4,
                Arrays.asList(DUMPLING_SOUP, STATIONARY, MOVIE_AVENGERS, KARAOKE, BOWLING), PREFIX_COST);
    }

    @Test
    public void execute_multipleKeyword_oneExpensesFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/Friends $/10 ", 1,
                Collections.singletonList(KARAOKE), PREFIX_TAG, PREFIX_COST);
    }

    @Test
    public void execute_multipleKeyword_multipleExpensesFound() {
        model = new ModelManager(getTypicalEPiggy(), new UserPrefs());
        findExpenseCommandTester(" t/food d/01/01/2019:30/04/2019 ", 2,
                Arrays.asList(DUMPLING_SOUP, KFC), CliSyntax.PREFIX_TAG, CliSyntax.PREFIX_DATE);
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
        FindCommand command = new FindCommand(predicate);
        command.execute(model, null);
        expectedModel.updateFilteredExpensesList(predicate);
        // assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expenses, model.getFilteredExpenseList());

    }
}
