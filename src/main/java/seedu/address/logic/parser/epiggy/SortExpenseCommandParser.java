package seedu.address.logic.parser.epiggy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserUtil.validateKeywordsForSort;

import java.util.Comparator;
import java.util.stream.Stream;

import seedu.address.logic.commands.epiggy.SortExpenseCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.epiggy.Expense;
import seedu.address.model.epiggy.comparators.CompareExpenseByCost;
import seedu.address.model.epiggy.comparators.CompareExpenseByDate;
import seedu.address.model.epiggy.comparators.CompareExpenseByName;

//@@author rahulb99
/**
 * Parses input arguments and creates a new SortExpenseCommand object.
 */
public class SortExpenseCommandParser implements Parser<SortExpenseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortExpenseCommand
     * and returns an SortExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpenseCommand.MESSAGE_USAGE));
        }

        //Check whether the user follow the pattern
        if (!trimmedArgs.contains("/")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpenseCommand.MESSAGE_USAGE));
        }

        String[] splitTrimmedArgs = trimmedArgs.split("/");
        if (splitTrimmedArgs[0].equals("")) {
            //Ensure args contains at least one prefix
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpenseCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap keywordsMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COST, PREFIX_DATE);
        validateKeywordsForSort(keywordsMap);
        Comparator<Expense> comparator = null;

        if (arePrefixesPresent(keywordsMap, PREFIX_NAME)) {
            comparator = new CompareExpenseByName();
        } else if (arePrefixesPresent(keywordsMap, PREFIX_DATE)) {
            comparator = new CompareExpenseByDate();
        } else if (arePrefixesPresent(keywordsMap, PREFIX_COST)) {
            comparator = new CompareExpenseByCost();
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortExpenseCommand.MESSAGE_USAGE));
        }

        return new SortExpenseCommand(comparator);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
