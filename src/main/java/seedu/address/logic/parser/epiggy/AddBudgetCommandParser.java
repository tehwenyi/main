package seedu.address.logic.parser.epiggy;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Date;
import java.util.stream.Stream;

import seedu.address.logic.commands.epiggy.AddBudgetCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.epiggy.Budget;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Period;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddBudgetCommandParser implements Parser<AddBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBudgetCommand
     * and returns an AddBudgetCommand object for execution.
     */
    public AddBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_COST, CliSyntax.PREFIX_PERIOD, CliSyntax.PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_COST, CliSyntax.PREFIX_PERIOD, CliSyntax.PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));
        }

        Cost budgetAmount = ParserUtil.parseCost(argMultimap.getValue(CliSyntax.PREFIX_COST).get());
        Period period = ParserUtil.parsePeriod(argMultimap.getValue(CliSyntax.PREFIX_PERIOD).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(CliSyntax.PREFIX_DATE).get());

        Budget budget = new Budget(budgetAmount, period, date);

        return new AddBudgetCommand(budget);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
